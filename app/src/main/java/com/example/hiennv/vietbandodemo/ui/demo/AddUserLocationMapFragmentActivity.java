package com.example.hiennv.vietbandodemo.ui.demo;

import android.annotation.SuppressLint;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.hiennv.vietbandodemo.R;
import com.example.hiennv.vietbandodemo.base.BaseActivity;

import com.vietbando.services.android.telemetry.permissions.PermissionsListener;
import com.vietbando.services.android.telemetry.permissions.PermissionsManager;
import com.vietbando.vietbandosdk.camera.CameraPosition;
import com.vietbando.vietbandosdk.camera.CameraUpdateFactory;
import com.vietbando.vietbandosdk.constants.Style;
import com.vietbando.vietbandosdk.geometry.LatLng;
import com.vietbando.vietbandosdk.maps.OnMapReadyCallback;
import com.vietbando.vietbandosdk.maps.SupportMapFragment;
import com.vietbando.vietbandosdk.maps.VietbandoMap;
import com.vietbando.vietbandosdk.maps.VietbandoMapOptions;


import java.util.List;

import timber.log.Timber;

public class AddUserLocationMapFragmentActivity extends BaseActivity implements PermissionsListener, OnMapReadyCallback,
        View.OnClickListener {
    private PermissionsManager permissionsManager;
    private FloatingActionButton btnMove;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_user_location_map_fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_location_map_fragment);
        btnMove = findViewById(R.id.btn_move);
        mapView = findViewById(R.id.map_box);
        //Create support fragment
        SupportMapFragment mapFragment;
        if (savedInstanceState == null) {
            //Create fragment
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            //Build VietbandoMap
            VietbandoMapOptions options = new VietbandoMapOptions();
            options.camera(new CameraPosition.Builder()
                    .target(new LatLng(10.781013712543356, 106.69203042984009))
                    .zoom(10)
                    .build());

            //Create map fragment with VietbandoMapOption
            mapFragment = SupportMapFragment.newInstance(options);
            transaction.replace(R.id.fl_container, mapFragment, "com.hiennv.demo");
            transaction.commit();
        } else {
            mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentByTag("com.hiennv.demo");
        }
        mapFragment.getMapAsync(this);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        btnMove.setOnClickListener(this);
    }

    @Override
    public void onExplanationNeeded(List<String> list) {

    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {

        } else {

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onMapReady(VietbandoMap vietbandoMap) {
        mMap = vietbandoMap;
        mMap.setStyle(Style.VIETBANDO_VT_DEFAULT);
        //enableLocationComponent();


        //Event click map
        mMap.setOnMapLongClickListener(new VietbandoMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(@NonNull LatLng latLng) {
                Timber.i("Lat: %f, Lng: %f", latLng.getLongitude(), latLng.getLongitude());
            }
        });
    }

    private Location myLocation;


    @Override
    public void onClick(View v) {
        CameraPosition position = new CameraPosition.Builder()
                .target(new LatLng(myLocation.getLatitude(), myLocation.getLongitude()))
                // Sets the new camera position
                .zoom(17) // Sets the zoom
                .bearing(180) // Rotate the camera
                .tilt(30) // Set the camera tilt
                .build(); // Creates a CameraPosition from the builder

        mMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(position), 7000);
    }


}
