package com.rishabh;

import android.app.Application;
import android.content.Context;

import com.rishabh.moviestage.data.DataManager;

public class MovieStageApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        DataManager.init(getApplicationContext());

    }
}
