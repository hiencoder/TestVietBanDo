package com.example.hiennv.vietbandodemo.app;

import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.example.hiennv.vietbandodemo.BuildConfig;
import com.example.hiennv.vietbandodemo.R;


import com.example.hiennv.vietbandodemo.base.ReleaseTree;
import com.vietbando.vietbandosdk.Vietbando;

import timber.log.Timber;

public class MyApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        Vietbando.getInstance(getApplicationContext(), getString(R.string.access_token));
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new ReleaseTree());
        }
    }
}
