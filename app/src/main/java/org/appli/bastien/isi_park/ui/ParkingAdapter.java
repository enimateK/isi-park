package org.appli.bastien.isi_park.ui;

import android.content.ClipData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;

import org.appli.bastien.isi_park.R;
import org.appli.bastien.isi_park.model.Parking;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ParkingAdapter extends ArrayAdapter<Parking> {


    public ParkingAdapter (Context context, List<Parking> park){
        super(context, -1, park);
    }


    @BindView(R.id.nom_park)
    TextView t_nom;

    @BindView(R.id.distance_park)
    TextView t_distance;

    @BindView(R.id.place_dispo)
    TextView t_place;

    @NonNull
    @Override

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View actualView = convertView;

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            actualView = inflater.inflate(R.layout.activity_parking_adapter, parent, false);

        }
        ButterKnife.bind(this, actualView);

        t_nom.setText(getItem(position).getName());
        t_place.setText(getItem(position).getVoiturePlace().toString());
        Double distance = 0 - getItem(position).getLatitude() + 0 - getItem(position).getLongitude();
        t_distance.setText(distance.toString());
        return actualView;
    }
}
