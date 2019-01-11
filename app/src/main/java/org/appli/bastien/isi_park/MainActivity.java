package org.appli.bastien.isi_park;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import org.appli.bastien.isi_park.model.Parking;
import org.appli.bastien.isi_park.ui.ParkingAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.ListParking)
    ListView m_ListParking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        ParkingAdapter m_ParkingAdapter;

        m_ParkingAdapter = new ParkingAdapter(this, new ArrayList<Parking>());

        m_ListParking.setAdapter(m_ParkingAdapter);


        
    }
}
