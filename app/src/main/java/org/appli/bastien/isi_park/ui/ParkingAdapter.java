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
import android.widget.ImageView;
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

    @BindView(R.id.adresse_park)
    TextView t_adresse;

    @BindView(R.id.fav_park)
    ImageView t_fav;

    @BindView(R.id.no_fav_park)
    ImageView t_no_fav;

    @BindView(R.id.cb_park)
    ImageView t_cb;

    @BindView(R.id.espece_park)
    ImageView t_espece;

    @BindView(R.id.total_gr_park)
    ImageView t_total_gr;

    @BindView(R.id.place_dispo)
    TextView t_place;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View actualView = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            actualView = inflater.inflate(R.layout.parking_adapter, parent, false);
        }
        ButterKnife.bind(this, actualView);
        t_nom.setText(getItem(position).name);
        t_adresse.setText(getItem(position).adresse);
        t_place.setText(getItem(position).dispoVoitures + "/" + getItem(position).placesVoitures);
        if(getItem(position).favorite) {
            t_fav.setVisibility(View.VISIBLE);
            t_no_fav.setVisibility(View.GONE);
        } else {
            t_fav.setVisibility(View.GONE);
            t_no_fav.setVisibility(View.VISIBLE);
        }
        if(getItem(position).cb) {
            t_cb.setVisibility(View.VISIBLE);
        } else {
            t_cb.setVisibility(View.GONE);
        }
        if(getItem(position).espece) {
            t_espece.setVisibility(View.VISIBLE);
        } else {
            t_espece.setVisibility(View.GONE);
        }
        if(getItem(position).totalGr) {
            t_total_gr.setVisibility(View.VISIBLE);
        } else {
            t_total_gr.setVisibility(View.GONE);
        }
        return actualView;
    }
}
