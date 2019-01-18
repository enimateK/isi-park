package org.appli.bastien.isi_park;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.squareup.otto.Subscribe;

import org.appli.bastien.isi_park.event.SearchResultEvent;
import org.appli.bastien.isi_park.model.Parking;
import org.appli.bastien.isi_park.service.ParkingSearchService;
import org.appli.bastien.isi_park.ui.ParkingAdapter;

import java.util.ArrayList;

import butterknife.BindView;

public class ListeActivity extends BaseActivity {

    @BindView(R.id.list_view)
    ListView listView;
    ParkingAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste);
        adapter = new ParkingAdapter(this, new ArrayList<Parking>());
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ParkingSearchService.INSTANCE.searchParkingFromDB();
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
                adapter.addAll(event.getParkings());
                // Step 2: hide loader
                //mProgressBar.setVisibility(View.GONE);
            }
        });
    }
}
