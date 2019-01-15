package org.appli.bastien.isi_park;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ParkingDetailActivity extends AppCompatActivity {
    @BindView(R.id.activity_detail_parking_title)
    TextView name;

    @BindView(R.id.activity_detail_parking_total_places)
    TextView totalPlaces;

    @BindView(R.id.activity_detail_parking_available_places)
    TextView availablePlaces;

    @BindView(R.id.activity_detail_parking_category)
    TextView category;

    @BindView(R.id.activity_detail_parking_address)
    TextView address;

    @BindView(R.id.activity_detail_parking_phone)
    TextView phone;

    @BindView(R.id.activity_detail_parking_website)
    TextView website;

    @BindView(R.id.activity_detail_parking_zip_code)
    TextView zipCode;

    @BindView(R.id.activity_detail_parking_description)
    TextView description;

    @BindView(R.id.activity_detail_parking_handicap_access)
    TextView handicapAccess;

    @BindView(R.id.activity_detail_parking_handicap_places)
    TextView handicapPlaces;

    @BindView(R.id.activity_detail_parking_electric_places)
    TextView electricPlaces;

    @BindView(R.id.activity_detail_parking_motorbike_places)
    TextView motorbikePlaces;

    @BindView(R.id.activity_detail_parking_bike_places)
    TextView bikePlaces;

    @BindView(R.id.activity_detail_parking_public_transport)
    TextView publicTransport;

    @BindView(R.id.activity_detail_parking_means_of_payment)
    TextView meansOfPayment;

    @BindView(R.id.activity_detail_parking_schedule)
    TextView schedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_detail);

        ButterKnife.bind(this);
        name.setText("Nom du parking");
        totalPlaces.setText("Nombre de place total : " + "10");
        availablePlaces.setText("Nombre de place disponibles : " + "10");
        address.setText("Adresse du parking : " + "10");
        phone.setText("Téléphone : " + "10");
        website.setText("Site web : " + "10");
        zipCode.setText("Code postal : " + "10");
        description.setText("Description du parking");
        handicapAccess.setText("Accès handicapé : " + "10");
        handicapPlaces.setText("Nombre de place handicapés : " + "10");
        electricPlaces.setText("Nombre de place véhicule électrique : " + "10");
        motorbikePlaces.setText("Nombre de place motos : " + "10");
        bikePlaces.setText("Places de vélo : " + "10");
        publicTransport.setText("Transports proches : " + "10");
        bikePlaces.setText("Places de vélo : " + "10");
        meansOfPayment.setText("Moyens de paiement : " + "10");
        schedule.setText("Horaires : " + "10");
        category.setText("Catégorie : " + "10");
    }

    @OnClick(R.id.activity_detail_parking_title)
    public void clickedOnParking() {
        finish();
    }
}
