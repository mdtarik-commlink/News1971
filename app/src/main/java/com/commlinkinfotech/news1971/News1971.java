package com.commlinkinfotech.news1971;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by CommlinkT on 12/27/2016.
 */

public class News1971 extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
