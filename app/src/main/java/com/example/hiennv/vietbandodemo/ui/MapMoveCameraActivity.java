package com.example.hiennv.vietbandodemo.ui;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hiennv.vietbandodemo.R;
import com.example.hiennv.vietbandodemo.base.BaseActivity;
import com.vietbando.vietbandosdk.camera.CameraPosition;
import com.vietbando.vietbandosdk.camera.CameraUpdateFactory;
import com.vietbando.vietbandosdk.geometry.LatLng;
import com.vietbando.vietbandosdk.maps.MapView;
import com.vietbando.vietbandosdk.maps.OnMapReadyCallback;
import com.vietbando.vietbandosdk.maps.VietbandoMap;
import com.vietbando.vietbandosdk.maps.VietbandoMapOptions;

public class MapMoveCameraActivity extends BaseActivity implements OnMapReadyCallback {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_map_move_camera;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapView = findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(VietbandoMap vietbandoMap) {
        mMap = vietbandoMap;
        // When user clicks the map, animate to new camera location
        mMap.setOnMapClickListener(point -> {
            CameraPosition position = new CameraPosition.Builder()
                    .target(new LatLng(10.781013712543356, 106.69203042984009))
                    // Sets the new camera position
                    .zoom(17) // Sets the zoom
                    .bearing(180) // Rotate the camera
                    .tilt(30) // Set the camera tilt
                    .build(); // Creates a CameraPosition from the builder

            mMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(position), 7000);
        });
    }
}
