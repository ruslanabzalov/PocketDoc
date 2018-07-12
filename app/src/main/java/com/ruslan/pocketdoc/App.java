package com.ruslan.pocketdoc;

import android.app.Application;

import com.ruslan.pocketdoc.di.AppComponent;
import com.ruslan.pocketdoc.di.ContextModule;
import com.ruslan.pocketdoc.di.DaggerAppComponent;
import com.ruslan.pocketdoc.di.DocDocServiceModule;
import com.ruslan.pocketdoc.di.DataSourceModule;

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
                .contextModule(new ContextModule(getApplicationContext()))
                .docDocServiceModule(new DocDocServiceModule())
                .dataSourceModule(new DataSourceModule())
                .build();
    }
}
