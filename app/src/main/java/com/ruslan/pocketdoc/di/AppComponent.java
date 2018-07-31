package com.ruslan.pocketdoc.di;

import com.ruslan.pocketdoc.clinics.ClinicsPresenter;
import com.ruslan.pocketdoc.clinics.ClinicsService;
import com.ruslan.pocketdoc.data.LocalDataSource;
import com.ruslan.pocketdoc.data.RemoteDataSource;
import com.ruslan.pocketdoc.data.Repository;
import com.ruslan.pocketdoc.doctor.DoctorFragment;
import com.ruslan.pocketdoc.doctor.DoctorPresenter;
import com.ruslan.pocketdoc.doctors.DoctorsPresenter;
import com.ruslan.pocketdoc.specialities.SpecialitiesPresenter;
import com.ruslan.pocketdoc.stations.StationsPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules =
        {ContextModule.class, DocDocServiceModule.class, DataSourceModule.class, UtilsModule.class})
public interface AppComponent {

    void inject(RemoteDataSource remoteDataSource);

    void inject(LocalDataSource localDataSource);

    void inject(Repository repository);

    void inject(SpecialitiesPresenter presenter);

    void inject(StationsPresenter presenter);

    void inject(DoctorsPresenter presenter);

    void inject(ClinicsPresenter presenter);

    void inject(DoctorFragment fragment);

    void inject(DoctorPresenter presenter);

    void inject(ClinicsService clinicsService);
}
