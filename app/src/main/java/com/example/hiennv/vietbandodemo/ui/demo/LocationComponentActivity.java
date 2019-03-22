package com.example.hiennv.vietbandodemo.ui.demo;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.hiennv.vietbandodemo.R;
import com.example.hiennv.vietbandodemo.base.BaseActivity;

import com.example.hiennv.vietbandodemo.ui.MainActivity;
import com.vietbando.services.android.telemetry.permissions.PermissionsListener;
import com.vietbando.services.android.telemetry.permissions.PermissionsManager;
import com.vietbando.vietbandosdk.camera.CameraPosition;
import com.vietbando.vietbandosdk.camera.CameraUpdateFactory;
import com.vietbando.vietbandosdk.constants.Style;
import com.vietbando.vietbandosdk.geometry.LatLng;
import com.vietbando.vietbandosdk.maps.OnMapReadyCallback;
import com.vietbando.vietbandosdk.maps.VietbandoMap;
import com.vietbando.vietbandosdk.style.layers.SymbolLayer;

import java.util.List;

import timber.log.Timber;

public class LocationComponentActivity extends BaseActivity implements PermissionsListener, OnMapReadyCallback, LocationListener, View.OnClickListener {
    private LocationManager locationManager;
    private Location myLocation;
    private PermissionsManager permissionsManager;
    private FloatingActionButton btnMove;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_location_component;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapView = findViewById(R.id.map_view);
        btnMove = findViewById(R.id.btn_move);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        btnMove.setOnClickListener(this);
    }

    void getLocation() {
        try {
            if (PermissionsManager.areLocationPermissionsGranted(this)) {
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);
                myLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            } else {
                permissionsManager = new PermissionsManager(this);
                permissionsManager.requestLocationPermissions(this);
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {

    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            getLocation();
        } else {
            finish();
        }
    }

    @Override
    public void onMapReady(VietbandoMap vietbandoMap) {
        mMap = vietbandoMap;
        mMap.setStyle(Style.VIETBANDO_RA_ADMIN);
        getLocation();
    }

    @Override
    public void onLocationChanged(Location location) {
        myLocation = location;
        Timber.i("Lat: %f", myLocation.getLatitude());
        CameraPosition position = new CameraPosition.Builder()
                .target(new LatLng(myLocation.getLatitude(), myLocation.getLongitude()))
                // Sets the new camera position
                .zoom(17) // Sets the zoom
                .bearing(0) // Rotate the camera
                .tilt(30) // Set the camera tilt
                .build(); // Creates a CameraPosition from the builder

        mMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(position), 7000);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    //https://medium.com/@ssaurel/getting-gps-location-on-android-with-fused-location-provider-api-1001eb549089

    //https://gist.github.com/ccabanero/6996756
    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClick(View v) {
        getLocation();
    }
}
