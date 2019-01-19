package org.appli.bastien.isi_park;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.ListView;

import com.squareup.otto.Subscribe;

import org.appli.bastien.isi_park.event.SearchResultEvent;
import org.appli.bastien.isi_park.model.Parking;
import org.appli.bastien.isi_park.service.ParkingSearchService;
import org.appli.bastien.isi_park.ui.ParkingAdapter;

import java.util.ArrayList;

import butterknife.BindView;

public class RechercheResultatsActivity extends BaseActivity {
    @BindView(R.id.list_view)
    ListView listView;
    @BindView(R.id.edit_text_recherche)
    EditText recherche;
    ParkingAdapter adapter;
    String adresse;
    String nom;
    boolean cb;
    boolean espece;
    boolean total_gr;
    int dispo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste);
        adapter = new ParkingAdapter(this, new ArrayList<Parking>());
        listView.setAdapter(adapter);
        adresse = getIntent().getStringExtra("adresse");
        nom = getIntent().getStringExtra("nom");
        cb = getIntent().getBooleanExtra("cb", false);
        espece = getIntent().getBooleanExtra("espece", false);
        total_gr = getIntent().getBooleanExtra("total_gr", false);
        dispo = getIntent().getIntExtra("dispo", 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ParkingSearchService.INSTANCE.searchAvailabilityFromOpenDataNantes();
    }

    @Subscribe
    public void searchResult(final SearchResultEvent event) {
        // Here someone has posted a SearchResultEvent
        // Run on ui thread as we want to update UI
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Step 1: Update adapter's model
                adapter.clear();
                adapter.addAll(event.getParkings(recherche.getText().toString(), nom, adresse, dispo, cb, espece, total_gr));
                // Step 2: hide loader
                //mProgressBar.setVisibility(View.GONE);
            }
        });
    }
}
