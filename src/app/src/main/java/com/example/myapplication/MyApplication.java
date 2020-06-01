package com.example.myapplication;

import android.app.Application;
import android.content.res.Configuration;
import android.os.Build;

import java.lang.annotation.Annotation;
import java.util.Locale;

public class MyApplication extends Application {//implements Thread.UncaughtExceptionHandler

    private static MyApplication _instance;
    public static Locale _UserLocale = null;

    public static MyApplication getInstance()
    {
        return _instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        _instance = this;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        _UserLocale=LanguageSettings.getUserLocale(this);
        //System language changed the language that the app keeps before setting
        if (_UserLocale != null) {
            Locale.setDefault(_UserLocale);
            Configuration _Configuration = new Configuration(newConfig);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                _Configuration.setLocale(_UserLocale);
            } else {
                _Configuration.locale =_UserLocale;
            }
            getResources().updateConfiguration(_Configuration, getResources().getDisplayMetrics());
        }
    }



}
