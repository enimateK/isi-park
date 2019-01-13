package org.appli.bastien.isi_park;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.squareup.otto.Subscribe;

import org.appli.bastien.isi_park.event.EventBusManager;
import org.appli.bastien.isi_park.event.SearchResultEvent;
import org.appli.bastien.isi_park.model.Parking;
import org.appli.bastien.isi_park.ui.ParkingAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.ListParking)
    ListView m_ListParking;

    ParkingAdapter m_ParkingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        ParkingAdapter m_ParkingAdapter;

        List<Parking> ListTest = new ArrayList<Parking>();
        for (int i = 0; i<50; ++i){
            ListTest.add(new Parking("Park"+i, "blablabla", 12.0, 15.0, 230, 12, 7, "label37", true));
        }
        //m_ParkingAdapter = new ParkingAdapter(this, new ArrayList<Parking>());
        m_ParkingAdapter = new ParkingAdapter(this, ListTest);
        m_ListParking.setAdapter(m_ParkingAdapter);

    }

    @Override
    protected void onResume() {
        // Do NOT forget to call super.onResume()
        super.onResume();
        EventBusManager.BUS.register(this);
        //RetrofitService.INSTANCE.searchParking("truc");
    }

    @Override
    protected void onPause(){
        super.onPause();
        EventBusManager.BUS.unregister(this);
    }

    @Subscribe
    public void searchResult(final SearchResultEvent event){
        m_ParkingAdapter.clear();
        m_ParkingAdapter.addAll(event.getParkings());
    }
}
