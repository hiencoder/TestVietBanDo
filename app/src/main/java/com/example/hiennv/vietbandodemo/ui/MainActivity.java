package com.example.hiennv.vietbandodemo.ui;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hiennv.vietbandodemo.R;
import com.vietbando.services.commons.geojson.Feature;
import com.vietbando.services.commons.geojson.Point;
import com.vietbando.vietbandosdk.constants.Style;
import com.vietbando.vietbandosdk.maps.MapView;
import com.vietbando.vietbandosdk.maps.VietbandoMap;
import com.vietbando.vietbandosdk.style.layers.PropertyFactory;
import com.vietbando.vietbandosdk.style.layers.SymbolLayer;
import com.vietbando.vietbandosdk.style.sources.GeoJsonSource;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {
    private MapView mapView;
    private VietbandoMap vietMap;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapView = findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync((vietbandoMap) -> {
            vietMap = vietbandoMap;

            vietMap.setStyle(Style.VIETBANDO_VT_LIGHT, new VietbandoMap.OnStyleLoadedListener() {
                @Override
                public void onStyleLoaded(String s) {
                    Timber.i("Style: %s",s);
                }
            });


            vietMap.addImage("my-marker",
                    BitmapFactory.decodeResource(MainActivity.this.getResources(), R.drawable.vietbando_mylocation_icon_bearing));

            //Add source
            GeoJsonSource geoJsonSource = new GeoJsonSource("source-id",
                    Feature.fromGeometry(Point.fromCoordinates(new double[]{106.701278, 10.7773973})));
            vietMap.addSource(geoJsonSource);

            //Add layer
            SymbolLayer symbolLayer = new SymbolLayer("layer-id","source-id");
            symbolLayer.withProperties(PropertyFactory.iconImage("marker-icon-id"));
            vietMap.addLayer(symbolLayer);


        });

        //Add mapView dynamic
        /*VietbandoMapOptions options = new VietbandoMapOptions()
                .camera(new CameraPosition.Builder()
                        .target(new LatLng(10.7773973, 106.701278))
                        .zoom(12)
                        .build());
        mapView = new MapView(this, options);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull VietbandoMap vietbandoMap) {
                vietbandoMap.setStyle(Style.VIETBANDO_VT_LIGHT, new VietbandoMap.OnStyleLoadedListener() {
                    @Override
                    public void onStyleLoaded(@NonNull String style) {

                    }
                });
            }
        });

        //Add mapview
        setContentView(mapView);*/
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
