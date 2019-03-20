package com.example.hiennv.vietbandodemo.app;

import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.example.hiennv.vietbandodemo.R;
import com.vietbando.vietbandosdk.Vietbando;

public class MyApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        Vietbando.getInstance(getApplicationContext(), getString(R.string.access_token));
    }
}
