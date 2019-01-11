package org.appli.bastien.isi_park.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import org.appli.bastien.isi_park.R;
import org.appli.bastien.isi_park.model.Parking;

import java.util.List;

public class ParkingAdapter extends ArrayAdapter<Parking> {


    public ParkingAdapter (Context context, List<Parking> park){
        super(context, -1, park);
    }

    @NonNull
    @Override

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View actualView = convertView;

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            actualView = inflater.inflate(R.layout.activity_parking_adapter, parent, false);

        }
        return actualView;
    }
}
