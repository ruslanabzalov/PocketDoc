package com.ruslan.pocketdoc;

import com.ruslan.pocketdoc.api.DocDocServiceModule;
import com.ruslan.pocketdoc.data.RemoteDataSourceImpl;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {DocDocServiceModule.class})
public interface AppComponent {

    void inject(RemoteDataSourceImpl remoteDataSource);
}
