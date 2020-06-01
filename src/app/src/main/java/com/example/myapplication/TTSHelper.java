package com.example.myapplication;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

public class TTSHelper {

    private TextToSpeech mTTS;
    private static TTSHelper _instance;

    public void init(Context context) {

        if (mTTS == null) {
            mTTS = new TextToSpeech(context, new TextToSpeech.OnInitListener() {

                @Override
                public void onInit(int status) {

                    if (status == mTTS.SUCCESS) {
                        Locale userLocale = LanguageSettings.getUserLocale(MyApplication.getInstance());
                        int result = mTTS.setLanguage(userLocale);
                        if (result != TextToSpeech.LANG_COUNTRY_AVAILABLE
                                && result != TextToSpeech.LANG_AVAILABLE) {
                            Log.e("tts:", "don't support that language");
                        }
                    }
                }
            });
        }
    }


    public static TTSHelper getInstance() {

        if (_instance == null) {
            _instance = new TTSHelper();
        }
        return _instance;
    }

    public void speechText(String text) {
        Locale userLocale = LanguageSettings.getUserLocale(MyApplication.getInstance());
        mTTS.speak(text, TextToSpeech.QUEUE_ADD, null);
    }



}
