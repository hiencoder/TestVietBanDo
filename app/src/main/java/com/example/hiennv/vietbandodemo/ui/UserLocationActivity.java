package com.example.hiennv.vietbandodemo.ui;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hiennv.vietbandodemo.R;
import com.vietbando.services.android.telemetry.permissions.PermissionsListener;
import com.vietbando.services.android.telemetry.permissions.PermissionsManager;
import com.vietbando.vietbandosdk.annotations.Icon;
import com.vietbando.vietbandosdk.annotations.IconFactory;
import com.vietbando.vietbandosdk.annotations.MarkerOptions;
import com.vietbando.vietbandosdk.camera.CameraPosition;
import com.vietbando.vietbandosdk.constants.Style;
import com.vietbando.vietbandosdk.geometry.LatLng;
import com.vietbando.vietbandosdk.maps.MapView;
import com.vietbando.vietbandosdk.maps.OnMapReadyCallback;
import com.vietbando.vietbandosdk.maps.VietbandoMap;
import com.vietbando.vietbandosdk.annotations.InfoWindow;
import com.vietbando.vietbandosdk.maps.VietbandoMapOptions;

import java.util.List;

public class UserLocationActivity extends AppCompatActivity implements OnMapReadyCallback, PermissionsListener {
    //Show user location
    private MapView mapView;
    private PermissionsManager permissionsManager;
    private VietbandoMap vietbandoMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Add mapview dynamic
        VietbandoMapOptions options = new VietbandoMapOptions()
                .camera(new CameraPosition.Builder()
                        .target(new LatLng(10.7773973, 106.701278))
                        .zoom(12)
                        .build());
        mapView = new MapView(this, options);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        setContentView(mapView);
    }

    @Override
    public void onExplanationNeeded(List<String> list) {

    }

    @Override
    public void onPermissionResult(boolean b) {

    }

    @SuppressLint("WrongConstant")
    @Override
    public void onMapReady(VietbandoMap vietbandoMap) {
        this.vietbandoMap = vietbandoMap;
        /*Style của Vietbando
         * VIETBANDO_VT_DEFAULT: Ảnh vector mặc định
         * VIETBANDO_RA_DEFAULT: Ảnh render mặc định
         * VIETBANDO_RA_TERRAIN: Ảnh render địa hình
         * VIETBANDO_RA_ADMIN: Ảnh hành chính
         * Style riêng sử dụng setStyleUrl*/
        this.vietbandoMap.setStyle(Style.VIETBANDO_RA_ADMIN);
        //this.vietbandoMap.setStyle(Style.getVietbandoUrl(1,""));
        enableLocationComponent();
        //Add marker
        /*this.vietbandoMap.addMarker(new MarkerOptions()
                .position(new LatLng(10.7773973, 106.701278))
                .title("Hello World!")
                .snippet("Welcome to my marker."));*/

        //Add marker tùy chỉnh
        // Create an Icon object for the marker to use
        IconFactory iconFactory = IconFactory.getInstance(this);
        //Drawable iconDrawable = ContextCompat.getDrawable(this, R.drawable.agoda);
        Icon icon = iconFactory.fromResource(R.drawable.agoda);

        // Add the custom icon marker to the map
        this.vietbandoMap.addMarker(new MarkerOptions()
                .position(new LatLng(10.7773973, 106.701278))
                .title("Customer Marker")
                .snippet("Q1, HCM City")
                .icon(icon));


    }

    /**
     *
     */
    private void enableLocationComponent() {
        //Check permission
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            //
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
