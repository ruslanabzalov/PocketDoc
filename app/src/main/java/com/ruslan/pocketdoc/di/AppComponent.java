package com.ruslan.pocketdoc.di;

import com.ruslan.pocketdoc.clinics.ClinicsPresenter;
import com.ruslan.pocketdoc.data.LocalDataSourceImpl;
import com.ruslan.pocketdoc.data.RemoteDataSourceImpl;
import com.ruslan.pocketdoc.data.Repository;
import com.ruslan.pocketdoc.doctors.DoctorsPresenter;
import com.ruslan.pocketdoc.specialities.SpecialitiesPresenter;
import com.ruslan.pocketdoc.stations.StationsPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ContextModule.class, DocDocServiceModule.class, DataSourceModule.class})
public interface AppComponent {

    void inject(RemoteDataSourceImpl remoteDataSource);

    void inject(LocalDataSourceImpl localDataSource);

    void inject(Repository repository);

    void inject(SpecialitiesPresenter presenter);

    void inject(StationsPresenter presenter);

    void inject(DoctorsPresenter presenter);

    void inject(ClinicsPresenter presenter);
}
