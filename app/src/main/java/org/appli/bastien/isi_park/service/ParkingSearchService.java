package org.appli.bastien.isi_park.service;

import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import org.appli.bastien.isi_park.event.EventBusManager;
import org.appli.bastien.isi_park.event.SearchResultEvent;
import org.appli.bastien.isi_park.model.Parking;

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
import retrofit2.http.Query;

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
                mRESTService.searchForParkings().enqueue(new Callback<ParkingSearchResult>() {
                    @Override
                    public void onResponse(Call<ParkingSearchResult> call, Response<ParkingSearchResult> response) {
                        // Post an event so that listening activities can update their UI
                        if (response.body() != null && response.body().records != null) {
                            // Save all results in Database
                            ActiveAndroid.beginTransaction();
                            for (ParkingSearchResult.ParkingRecord record : response.body().records) {
                                // TODO convertir en Parking
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
                    public void onFailure(Call<ParkingSearchResult> call, Throwable t) {
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
        Call<AvailabilitySearchResult> searchForAvailability();
    }

    public class ParkingSearchResult {
        @Expose
        public List<ParkingRecord> records;
        public class ParkingRecord {
            @Expose
            public ParkingFields fields;
            public class ParkingFields {
                @Expose
                public String idobj;
                @Expose
                public String nom_complet;
                @Expose
                public String presentation;
                @Expose
                public String acces_transports_communs;
                @Expose
                public String adresse;
                @Expose
                public String code_postal;
                @Expose
                public String commune;
                @Expose
                public int capacite_voiture;
                @Expose
                public int capacite_vehicule_electrique;
                @Expose
                public int capacite_pmr;
                @Expose
                public int capacite_moto;
                @Expose
                public int capacite_velo;
                @Expose
                public List<Double> location;
            }
        }
    }

    public class AvailabilitySearchResult {
        @Expose
        public List<AvailabilityRecord> records;
        public class AvailabilityRecord {
            @Expose
            public AvailabilityFields fields;
            public class AvailabilityFields {
                @Expose
                public int idobj;
                @Expose
                public int grp_disponible;
                @Expose
                public int grp_statut;
            }
        }
    }
}
