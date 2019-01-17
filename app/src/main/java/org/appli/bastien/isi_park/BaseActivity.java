package org.appli.bastien.isi_park;

import android.support.v7.app.AppCompatActivity;

import org.appli.bastien.isi_park.event.EventBusManager;

import butterknife.ButterKnife;

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
}
