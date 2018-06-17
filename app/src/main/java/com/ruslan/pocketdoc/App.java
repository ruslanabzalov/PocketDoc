package com.ruslan.pocketdoc;

import android.app.Application;

import com.ruslan.pocketdoc.api.DocDocServiceModule;

public class App extends Application {

    private static AppComponent sComponent;

    public static AppComponent getComponent() {
        return sComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sComponent = buildComponent();
    }

    private AppComponent buildComponent() {
        return DaggerAppComponent.builder()
                .docDocServiceModule(new DocDocServiceModule())
                .build();
    }
}
