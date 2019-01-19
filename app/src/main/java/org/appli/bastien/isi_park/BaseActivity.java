package org.appli.bastien.isi_park;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;

import org.appli.bastien.isi_park.event.EventBusManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BaseActivity extends AppCompatActivity {

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBusManager.BUS.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBusManager.BUS.unregister(this);
    }

    @OnClick(R.id.button_retour)
    protected void button_retour() {
        finish();
    }
}
