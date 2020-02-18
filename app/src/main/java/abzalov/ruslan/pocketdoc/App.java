package abzalov.ruslan.pocketdoc;

import android.app.Application;

import abzalov.ruslan.pocketdoc.di.AppComponent;
import abzalov.ruslan.pocketdoc.di.ContextModule;
import abzalov.ruslan.pocketdoc.di.DaggerAppComponent;
import abzalov.ruslan.pocketdoc.di.DataSourceModule;
import abzalov.ruslan.pocketdoc.di.DocDocServiceModule;
import abzalov.ruslan.pocketdoc.di.UtilsModule;

/**
 * Основной класс приложения.
 */
public final class App extends Application {

    private static AppComponent sComponent;

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
