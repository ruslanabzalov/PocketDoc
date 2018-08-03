package com.ruslan.pocketdoc;

import android.app.Application;

import com.ruslan.pocketdoc.di.AppComponent;
import com.ruslan.pocketdoc.di.ContextModule;
import com.ruslan.pocketdoc.di.DaggerAppComponent;
import com.ruslan.pocketdoc.di.DataSourceModule;
import com.ruslan.pocketdoc.di.DocDocServiceModule;
import com.ruslan.pocketdoc.di.UtilsModule;

/**
 * Базовый класс приложения.
 */
public class App extends Application {

    private static AppComponent sComponent;

    /**
     * Метод доступа к компоненту Dagger 2.
     * @return Компонент Dagger 2.
     */
    public static AppComponent getComponent() {
        return sComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sComponent = DaggerAppComponent.builder()
                .contextModule(new ContextModule(getApplicationContext()))
                .docDocServiceModule(new DocDocServiceModule())
                .dataSourceModule(new DataSourceModule())
                .utilsModule(new UtilsModule())
                .build();
    }
}
