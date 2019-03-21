package com.example.hiennv.vietbandodemo.ui;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.vietbando.vietbandosdk.Vietbando;
import com.vietbando.vietbandosdk.annotations.PolylineOptions;
import com.vietbando.vietbandosdk.camera.CameraPosition;
import com.vietbando.vietbandosdk.constants.Style;
import com.vietbando.vietbandosdk.geometry.LatLng;
import com.vietbando.vietbandosdk.maps.MapView;
import com.vietbando.vietbandosdk.maps.OnMapReadyCallback;
import com.vietbando.vietbandosdk.maps.VietbandoMap;
import com.vietbando.vietbandosdk.maps.VietbandoMapOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class GeoJsonActivity extends AppCompatActivity implements OnMapReadyCallback {
    private MapView mapView;
    private VietbandoMap mVietbandoMap;
    private Vietbando vietbando;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        VietbandoMapOptions options = new VietbandoMapOptions()
                .camera(new CameraPosition.Builder()
                        .target(new LatLng(21.969158830062938, 106.71006523224813))
                        .zoom(15)
                        .build());
        //mapView = findViewById(R.id.map_view);
        mapView = new MapView(this, options);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        setContentView(mapView);
    }


    @Override
    public void onMapReady(VietbandoMap vietbandoMap) {
        mVietbandoMap = vietbandoMap;

        mVietbandoMap.setStyle(Style.VIETBANDO_VT_DEFAULT);

        new DrawGeoJson().execute();
    }

    private class DrawGeoJson extends AsyncTask<Void, Void, List<LatLng>> {

        @Override
        protected List<LatLng> doInBackground(Void... voids) {
            List<LatLng> points = new ArrayList<>();
            Log.i("HIEN", "doInBackground");
            try {
                //Load GeoJson file
                InputStream inputStream = getAssets().open("map_demo.geojson");
                BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
                StringBuilder sb = new StringBuilder();
                int cp;
                while ((cp = bf.read()) != -1) {
                    sb.append((char) cp);
                }
                inputStream.close();

                //Parse json
                JSONObject jsonObject = new JSONObject(sb.toString());
                JSONArray features = jsonObject.getJSONArray("features");
                JSONObject feature = features.getJSONObject(0);
                JSONObject geometry = feature.getJSONObject("geometry");

                if (geometry != null) {
                    String type = geometry.getString("type");
                    if (!TextUtils.isEmpty(type) && type.equalsIgnoreCase("LineString")) {
                        //Get the coordinates
                        JSONArray coordinates = geometry.getJSONArray("coordinates");
                        for (int i = 0; i < coordinates.length(); i++) {
                            JSONArray coord = coordinates.getJSONArray(i);
                            LatLng latLng = new LatLng(coord.getDouble(0), coord.getDouble(1));
                            points.add(latLng);
                        }
                    }
                }
            } catch (Exception e) {
                Timber.e("Error: %s", e.getMessage());
            }
            return points;
        }

        @Override
        protected void onPostExecute(List<LatLng> latLngs) {
            super.onPostExecute(latLngs);
            Log.i("HIEN", "onPostExcute" + latLngs.size());
            if (latLngs != null && latLngs.size() > 0) {
                LatLng[] pointArray = latLngs.toArray(new LatLng[latLngs.size()]);

                //Draw line
                mVietbandoMap.addPolyline(new PolylineOptions()
                        .add(pointArray)
                        .color(Color.parseColor("#ff0000"))
                        .width(2f));
            }
        }
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
