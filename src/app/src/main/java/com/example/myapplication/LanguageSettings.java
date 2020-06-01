package com.example.myapplication;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.util.DisplayMetrics;

import com.google.gson.Gson;

import java.util.Locale;

public class LanguageSettings {
    /**
     * Chinese
     */
    public static final Locale LOCALE_CHINESE = Locale.CHINESE;
    /**
     * English
     */
    public static final Locale LOCALE_ENGLISH = Locale.ENGLISH;
    /**
     * Russian
     */
    public static final Locale LOCALE_RUSSIAN = new Locale("ru");
    /**
     * Save the file name of SharedPreferences
     */
    private static final String LOCALE_FILE = "LOCALE_FILE";
    /**
     * Save Locale key
     */
    private static final String LOCALE_KEY = "LOCALE_KEY";

    /**
     * Get the Locale set by the user
     * @param pContext Context
     * @return Locale
     */
    public static Locale getUserLocale(Context pContext) {
        SharedPreferences _SpLocale = pContext.getSharedPreferences(LOCALE_FILE, Context.MODE_PRIVATE);
        String _LocaleJson = _SpLocale.getString(LOCALE_KEY, "");
        return jsonToLocale(_LocaleJson);
    }
    /**
     * Get the current Locale
     * @param pContext Context
     * @return Locale
     */
    public static Locale getCurrentLocale(Context pContext) {
        Locale _Locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { //7.0 has multiple language settings to get the top language
            _Locale = pContext.getResources().getConfiguration().getLocales().get(0);
        } else {
            _Locale = pContext.getResources().getConfiguration().locale;
        }
        return _Locale;
    }
    /**
     * Locale to save user settings
     * @param pContext Context
     * @param pUserLocale Locale
     */
    public static void saveUserLocale(Context pContext, Locale pUserLocale) {
        SharedPreferences _SpLocal=pContext.getSharedPreferences(LOCALE_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor _Edit=_SpLocal.edit();
        String _LocaleJson = localeToJson(pUserLocale);
        //String _LocaleJson = "{}";
        _Edit.putString(LOCALE_KEY, _LocaleJson);
        _Edit.apply();
    }
    /**
     * Convert Locale to json
     * @param pUserLocale UserLocale
     * @return json String
     */
    private static String localeToJson(Locale pUserLocale) {
        Gson _Gson = new Gson();
        return _Gson.toJson(pUserLocale);
    }

    /**
     * Convert json to Locale
     * @param pLocaleJson LocaleJson
     * @return Locale
     */
    private static Locale jsonToLocale(String pLocaleJson) {
        Gson _Gson = new Gson();
        return _Gson.fromJson(pLocaleJson, Locale.class);
    }

    /**
     * update Locale
     * @param pContext Context
     * @param pNewUserLocale New User Locale
     */
    public static void updateLocale(Context pContext, Locale pNewUserLocale) {
        if (needUpdateLocale(pContext, pNewUserLocale)) {
            Configuration _Configuration = pContext.getResources().getConfiguration();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                _Configuration.setLocale(pNewUserLocale);
            } else {
                _Configuration.locale =pNewUserLocale;
            }
            DisplayMetrics _DisplayMetrics = pContext.getResources().getDisplayMetrics();
            pContext.getResources().updateConfiguration(_Configuration, _DisplayMetrics);
            saveUserLocale(pContext, pNewUserLocale);
        }
    }
    /**
     * Determine if it needs to be updated
     * @param pContext Context
     * @param pNewUserLocale New User Locale
     * @return true / false
     */
    public static boolean needUpdateLocale(Context pContext, Locale pNewUserLocale) {
        return pNewUserLocale != null && !getCurrentLocale(pContext).equals(pNewUserLocale);
    }
}
