package com.example.hiennv.vietbandodemo;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.vietbando.vietbandosdk.annotations.PolygonOptions;
import com.vietbando.vietbandosdk.camera.CameraPosition;
import com.vietbando.vietbandosdk.constants.Style;
import com.vietbando.vietbandosdk.geometry.LatLng;
import com.vietbando.vietbandosdk.maps.MapView;
import com.vietbando.vietbandosdk.maps.OnMapReadyCallback;
import com.vietbando.vietbandosdk.maps.VietbandoMap;
import com.vietbando.vietbandosdk.maps.VietbandoMapOptions;

import java.util.ArrayList;
import java.util.List;

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
                        .target(new LatLng(10.7773973, 106.701278))
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
        drawPolygon(mMap);
    }

    /**
     * Draw polygon
     *
     * @param map
     */
    private void drawPolygon(VietbandoMap map) {
        List<LatLng> polygon = new ArrayList<>();
        polygon.add(new LatLng(10.7773973, 106.701278));
        polygon.add(new LatLng(10.7753973, 106.700178));
        polygon.add(new LatLng(10.7753973, 106.702378));
        map.addPolygon(new PolygonOptions()
                .addAll(polygon)
                .fillColor(Color.parseColor("#3bb2d0")));
    }


}
