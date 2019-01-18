package org.appli.bastien.isi_park;

import android.content.Intent;
import android.os.Bundle;

import org.appli.bastien.isi_park.service.ParkingSearchService;

import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ParkingSearchService.INSTANCE.searchParkingFromDataNantes();
    }

    @OnClick(R.id.button_liste)
    public void onButtonListeClick() {
        startActivity(new Intent(this, ListeActivity.class));
    }

    @OnClick(R.id.button_map)
    public void onButtonMapClick() {
        startActivity(new Intent(this, MapActivity.class));
    }

    @OnClick(R.id.button_favoris)
    public void onButtonFavorisClick() {
        startActivity(new Intent(this, FavorisActivity.class));
    }

    @OnClick(R.id.button_recherche)
    public void onButtonRechercheClick() {
        startActivity(new Intent(this, RechercheActivity.class));
    }

    @OnClick(R.id.button_param)
    public void onButtonParamClick() {
        startActivity(new Intent(this, ParamActivity.class));
    }
}
