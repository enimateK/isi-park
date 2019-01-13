package org.appli.bastien.isi_park;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ParkingDetailActivity extends AppCompatActivity {
    @BindView(R.id.activity_detail_parking)
    TextView parkingText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_detail);

        ButterKnife.bind(this);
        parkingText.setText("Détails du lieu");
    }

    @OnClick(R.id.activity_detail_parking)
    public void clickedOnParking() {
        finish();
    }
}
