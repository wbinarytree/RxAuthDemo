package io.phoenix.demo.rxauthdemo;

import android.app.Application;

import io.phoenix.demo.rxauthdemo.translator.AuthTranslator;

/**
 * Created by yaoda on 24/04/17.
 */

public class MainApplication extends Application {

    private AuthTranslator translator;

    @Override
    public void onCreate() {
        super.onCreate();
        translator = new AuthTranslator(new AuthManager());
    }

    public AuthTranslator getTranslator() {
        return translator;
    }
}
