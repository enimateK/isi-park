package org.appli.bastien.isi_park;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.otto.Subscribe;

import org.appli.bastien.isi_park.event.SearchResultEvent;
import org.appli.bastien.isi_park.model.Parking;
import org.appli.bastien.isi_park.service.ParkingSearchService;

import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.OnClick;

public class MapActivity extends BaseActivity implements OnMapReadyCallback {

    private GoogleMap mActiveGoogleMap;
    private Map<String, Parking> mMarkersToPlaces = new LinkedHashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ParkingSearchService.INSTANCE.searchAvailabilityFromOpenDataNantes();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mActiveGoogleMap = googleMap;
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Parking parking = mMarkersToPlaces.get(marker.getId());
                Intent intent = new Intent(MapActivity.this, ParkingDetailActivity.class);
                intent.putExtra("placeStreet", parking.idobj);
                startActivity(intent);
            }
        });
    }

    @Subscribe
    public void searchResult(final SearchResultEvent event) {
        // Here someone has posted a SearchResultEvent
        // Run on UI Thread as we want to update ui
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Check that map is ready
                if (mActiveGoogleMap != null) {
                    // Update map's markers
                    mActiveGoogleMap.clear();
                    mMarkersToPlaces.clear();

                    LatLngBounds.Builder cameraBounds = LatLngBounds.builder();
                    for (Parking parking : event.getParkings()) {
                        // Step 1: create marker icon (and resize drawable so that marker is not too big)
                        int markerIconResource;
                        /*if (parking.getProperties().isStreet()) {
                            markerIconResource = R.drawable.street_icon;
                        } else {
                            markerIconResource = R.drawable.home_icon;
                        }
                        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(), markerIconResource);
                        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, 50, 50, false);*/

                        // Step 2: define marker options
                        MarkerOptions markerOptions = new MarkerOptions()
                                .position(new LatLng(parking.latitude, parking.longitude))
                                .title(parking.name + " (" + parking.dispoVoitures + " places)")
                                .snippet(parking.adresse);
                                /*.icon(BitmapDescriptorFactory.fromBitmap(resizedBitmap))*/;

                        // Step 3: include marker in camera bounds
                        cameraBounds.include(markerOptions.getPosition());

                        // Step 4: add marker
                        Marker marker = mActiveGoogleMap.addMarker(markerOptions);
                        mMarkersToPlaces.put(marker.getId(), parking);
                    }

                    // Finaly, move camera to reveal all markers
                    if (event.getParkings().size() > 0) {
                        int width = getResources().getDisplayMetrics().widthPixels;
                        int height = getResources().getDisplayMetrics().heightPixels;
                        int padding = (int) (width * 0.10); // offset from edges of the map 10% of screen

                        mActiveGoogleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(cameraBounds.build(), width, height, padding));
                    }
                }

                // Hide loader
                //mProgressBar.setVisibility(View.GONE);
            }
        });
    }
}
