package org.appli.bastien.isi_park;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import com.squareup.otto.Subscribe;

import org.appli.bastien.isi_park.event.SearchResultEvent;
import org.appli.bastien.isi_park.model.Parking;
import org.appli.bastien.isi_park.service.ParkingSearchService;
import org.appli.bastien.isi_park.ui.ParkingAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnItemClick;

public class ListeActivity extends BaseActivity {

    @BindView(R.id.edit_text_recherche)
    EditText recherche;

    @BindView(R.id.list_view)
    ListView listView;
    ParkingAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste);
        adapter = new ParkingAdapter(this, new ArrayList<Parking>());
        listView.setAdapter(adapter);
        recherche.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ParkingSearchService.INSTANCE.searchParkingFromDB();
            }
        });
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
                adapter.addAll(event.getParkings(recherche.getText().toString()));
                // Step 2: hide loader
                //mProgressBar.setVisibility(View.GONE);
            }
        });
    }

    @OnItemClick(R.id.list_view)
    public void onItemSelected(int position) {
        Intent intent = new Intent(ListeActivity.this, ParkingDetailActivity.class);
        Parking parking = adapter.getItem(position);
        intent.putExtra("parking", parking.idobj);
        startActivity(intent);
    }
}
