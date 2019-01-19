package org.appli.bastien.isi_park;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.otto.Subscribe;

import org.appli.bastien.isi_park.event.SearchResultEvent;
import org.appli.bastien.isi_park.model.Parking;
import org.appli.bastien.isi_park.service.ParkingSearchService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ParkingDetailActivity extends BaseActivity implements OnStreetViewPanoramaReadyCallback {
    @BindView(R.id.activity_detail_parking_title)
    TextView name;

    @BindView(R.id.activity_detail_parking_total_places)
    TextView totalPlaces;

    @BindView(R.id.activity_detail_parking_available_places)
    TextView availablePlaces;

    @BindView(R.id.activity_detail_parking_address)
    TextView address;

    @BindView(R.id.activity_detail_parking_description)
    TextView description;

    @BindView(R.id.activity_detail_parking_motorbike_places)
    TextView motorbikePlaces;

    @BindView(R.id.activity_detail_parking_bike_places)
    TextView bikePlaces;

    @BindView(R.id.activity_detail_parking_pmr_places)
    TextView pmrPlaces;

    @BindView(R.id.activity_detail_parking_electric_places)
    TextView electricPlaces;

    @BindView(R.id.activity_detail_parking_means_of_payment)
    TextView meansOfPayment;

    @BindView(R.id.activity_detail_favoris)
    Button favoris;

    String idobj;
    Parking parking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_detail);

        idobj = getIntent().getStringExtra("parking");
        SupportStreetViewPanoramaFragment streetViewPanoramaFragment = (SupportStreetViewPanoramaFragment)getSupportFragmentManager().findFragmentById(R.id.activity_detail_street_view);
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ParkingSearchService.INSTANCE.searchParkingFromDB(idobj);
    }

    @Subscribe
    public void searchResult(final SearchResultEvent event) {
        // Here someone has posted a SearchResultEvent
        // Run on ui thread as we want to update UI
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                parking = event.getParking(idobj);
                name.setText(parking.name);
                totalPlaces.setText("Nombre de places total : " + parking.placesVoitures);
                availablePlaces.setText("Nombre de places disponibles : " + parking.dispoVoitures);
                address.setText(parking.adresse + " " + parking.codePostal + " " + parking.ville);
                description.setText(parking.description);
                motorbikePlaces.setText("Nombre de places motos : " + parking.placesMoto);
                bikePlaces.setText("Nombre de places vélo : " + parking.placesVelo);
                pmrPlaces.setText("Nombre de places PMR : " + parking.placesPmr);
                electricPlaces.setText("Nombre de places voitures électriques : " + parking.placesVoituresElectriques);
                String payment = (parking.cb ? ",Carte bancaire" : "") + (parking.espece ? ",Espèces" : "") + (parking.totalGr ? ",Total GR" : "");
                meansOfPayment.setText("Moyens de paiement : " + payment.substring(1, payment.length() - 1));
                favoris.setText(parking.favorite ? "Retirer des favoris" : "Ajouter aux favoris");
            }
        });
    }

    @OnClick(R.id.activity_detail_favoris)
    public void favoris() {
        parking.favorite = !parking.favorite;
        parking.save();
        favoris.setText(parking.favorite ? "Retirer des favoris" : "Ajouter aux favoris");
    }

    @OnClick(R.id.activity_detail_parking_title)
    public void clickedOnParking() {
        finish();
    }

    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {
        streetViewPanorama.setPosition(new LatLng(parking.latitude, parking.longitude));
    }
}
