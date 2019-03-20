package com.example.hiennv.vietbandodemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.vietbando.vietbandosdk.camera.CameraPosition;
import com.vietbando.vietbandosdk.constants.Style;
import com.vietbando.vietbandosdk.geometry.LatLng;
import com.vietbando.vietbandosdk.maps.MapView;
import com.vietbando.vietbandosdk.maps.OnMapReadyCallback;
import com.vietbando.vietbandosdk.maps.VietbandoMap;
import com.vietbando.vietbandosdk.maps.VietbandoMapOptions;

public class PolygonActivity extends BaseActivity implements OnMapReadyCallback {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_polygon;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        VietbandoMapOptions options = new VietbandoMapOptions()
                .camera(new CameraPosition.Builder()
                        .target(new LatLng(21.969158830062938, 106.71006523224813))
                        .zoom(15)
                        .build());

        mapView = new MapView(this, options);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        setContentView(mapView);
    }


    @Override
    public void onMapReady(VietbandoMap vietbandoMap) {
        mMap = vietbandoMap;
        mMap.setStyle(Style.VIETBANDO_VT_DEFAULT);
    }


}
