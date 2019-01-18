package org.appli.bastien.isi_park.service;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Debug;
import android.util.Log;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import org.appli.bastien.isi_park.event.EventBusManager;
import org.appli.bastien.isi_park.event.SearchResultEvent;
import org.appli.bastien.isi_park.model.DispoSearchResult;
import org.appli.bastien.isi_park.model.Parking;
import org.appli.bastien.isi_park.model.ParkingSearchResult;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class ParkingSearchService {

    private static final long REFRESH_DELAY = 650;
    public static ParkingSearchService INSTANCE = new ParkingSearchService();
    private final ParkingSearchRESTService mRESTService;
    private ScheduledExecutorService mScheduler = Executors.newScheduledThreadPool(1);
    private ScheduledFuture mLastScheduleTask;

    public ParkingSearchService() {
        // Create GSON Converter that will be used to convert from JSON to Java
        Gson gsonConverter = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                .serializeNulls()
                .excludeFieldsWithoutExposeAnnotation().create();

        // Create Retrofit client
        Retrofit retrofit = new Retrofit.Builder()
                // Using OkHttp as HTTP Client
                .client(new OkHttpClient())
                // Having the following as server URL
                .baseUrl("https://data.nantesmetropole.fr/")
                // Using GSON to convert from Json to Java
                .addConverterFactory(GsonConverterFactory.create(gsonConverter))
                .build();

        // Use retrofit to generate our REST service code
        mRESTService = retrofit.create(ParkingSearchRESTService.class);
    }

    public void searchParkingFromDataNantes() {
        new Runnable() {
            public void run() {
                searchParkingFromDB();

                mRESTService.searchForParkings().enqueue(new Callback<ParkingSearchResult>() {
                    @Override
                    public void onResponse(Call<ParkingSearchResult> call, Response<ParkingSearchResult> response) {
                        // Post an event so that listening activities can update their UI
                        if (response.body() != null && response.body().records != null) {
                            // Save all results in Database
                            ActiveAndroid.beginTransaction();
                            for (ParkingSearchResult.ParkingRecord record : response.body().records) {
                                Parking parking = new Select().from(Parking.class).where("idobj = '" + record.fields.idobj + "'").executeSingle();
                                if(parking == null) {
                                    parking = new Parking(record.fields.idobj);
                                }
                                parking.name = record.fields.nom_complet;
                                parking.description = record.fields.presentation;
                                parking.adresse = record.fields.adresse;
                                parking.codePostal = record.fields.code_postal;
                                parking.ville = record.fields.commune;
                                parking.longitude = record.fields.location.get(1);
                                parking.latitude = record.fields.location.get(0);
                                parking.placesVoitures = record.fields.capacite_voiture;
                                parking.placesMoto = record.fields.capacite_moto;
                                parking.placesVelo = record.fields.capacite_velo;
                                parking.placesVoituresElectriques = record.fields.capacite_vehicule_electrique;
                                parking.placesPmr = record.fields.capacite_pmr;
                                parking.cb = record.fields.moyen_paiement.contains("CB");
                                parking.espece = record.fields.moyen_paiement.contains("Espèces");
                                parking.totalGr = record.fields.moyen_paiement.contains("Total GR");
                                parking.save();
                            }
                            ActiveAndroid.setTransactionSuccessful();
                            ActiveAndroid.endTransaction();

                            // Send a new event with results from network
                            searchAvailabilityFromOpenDataNantes();
                        } else {
                            // Null result
                            // We may want to display a warning to user (e.g. Toast)
                            Log.e("[ParkingSearcher] [REST]", "Response error : null body");
                        }
                    }

                    @Override
                    public void onFailure(Call<ParkingSearchResult> call, Throwable t) {
                        // Request has failed or is not at expected format
                        // We may want to display a warning to user (e.g. Toast)
                        Log.e("[ParkingSearcher] [REST]", "Response error : " + t.getMessage());
                    }
                });
            }
        }.run();
    }

    public void searchAvailabilityFromOpenDataNantes() {
        // Cancel last scheduled network call (if any)
        if (mLastScheduleTask != null && !mLastScheduleTask.isDone()) {
            mLastScheduleTask.cancel(true);
        }

        // Schedule a network call in REFRESH_DELAY ms
        mLastScheduleTask = mScheduler.schedule(new Runnable() {
            public void run() {
                // Step 1 : first run a local search from DB and post result
                searchParkingFromDB();

                // Step 2 : Call to the REST service
                mRESTService.searchForAvailability().enqueue(new Callback<DispoSearchResult>() {
                    @Override
                    public void onResponse(Call<DispoSearchResult> call, Response<DispoSearchResult> response) {
                        // Post an event so that listening activities can update their UI
                        if (response.body() != null && response.body().records != null) {
                            // Save all results in Database
                            ActiveAndroid.beginTransaction();
                            for (DispoSearchResult.AvailabilityRecord record : response.body().records) {
                                Parking parking = new Select().from(Parking.class).where("idobj = '" + record.fields.idobj + "'").executeSingle();
                                if(parking != null) {
                                    parking.dispoVoitures = record.fields.grp_disponible;
                                    parking.save();
                                }
                            }
                            ActiveAndroid.setTransactionSuccessful();
                            ActiveAndroid.endTransaction();

                            // Send a new event with results from network
                            searchParkingFromDB();
                        } else {
                            // Null result
                            // We may want to display a warning to user (e.g. Toast)
                            Log.e("[ParkingSearcher] [REST]", "Response error : null body");
                        }
                    }

                    @Override
                    public void onFailure(Call<DispoSearchResult> call, Throwable t) {
                        // Request has failed or is not at expected format
                        // We may want to display a warning to user (e.g. Toast)
                        Log.e("[ParkingSearcher] [REST]", "Response error : " + t.getMessage());
                    }
                });
            }
        }, REFRESH_DELAY, TimeUnit.MILLISECONDS);
    }

    public void searchParkingFromDB() {
        List<Parking> parkings = new Select().from(Parking.class).execute();
        EventBusManager.BUS.post(new SearchResultEvent(parkings));
    }

    public interface ParkingSearchRESTService {
        @GET("/api/records/1.0/search/?dataset=244400404_parkings-publics-nantes&rows=100")
        Call<ParkingSearchResult> searchForParkings();
        @GET("/api/records/1.0/search/?dataset=244400404_parkings-publics-nantes-disponibilites&rows=100")
        Call<DispoSearchResult> searchForAvailability();
    }


}
