package abzalov.ruslan.pocketdoc.di;

import abzalov.ruslan.pocketdoc.clinic.ClinicFragment;
import abzalov.ruslan.pocketdoc.clinic.ClinicPresenter;
import abzalov.ruslan.pocketdoc.clinics.ClinicsJobService;
import abzalov.ruslan.pocketdoc.clinics.ClinicsPresenter;
import abzalov.ruslan.pocketdoc.data.LocalDataSource;
import abzalov.ruslan.pocketdoc.data.RemoteDataSource;
import abzalov.ruslan.pocketdoc.data.Repository;
import abzalov.ruslan.pocketdoc.dialogs.CreateRecordDialogFragment;
import abzalov.ruslan.pocketdoc.doctor.DoctorFragment;
import abzalov.ruslan.pocketdoc.doctor.DoctorPresenter;
import abzalov.ruslan.pocketdoc.doctors.DoctorsAdapter;
import abzalov.ruslan.pocketdoc.doctors.DoctorsPresenter;
import abzalov.ruslan.pocketdoc.specialities.SpecialitiesPresenter;
import abzalov.ruslan.pocketdoc.stations.StationsPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        ContextModule.class, DocDocServiceModule.class,
        DataSourceModule.class, UtilsModule.class
})
public interface AppComponent {

    void inject(RemoteDataSource remoteDataSource);

    void inject(LocalDataSource localDataSource);

    void inject(Repository repository);

    void inject(SpecialitiesPresenter presenter);

    void inject(ClinicsPresenter presenter);

    void inject(ClinicsJobService clinicsJobService);

    void inject(ClinicFragment clinicFragment);

    void inject(ClinicPresenter clinicPresenter);

    void inject(StationsPresenter presenter);

    void inject(DoctorsPresenter presenter);

    void inject(DoctorsAdapter.DoctorViewHolder viewHolder);

    void inject(DoctorFragment fragment);

    void inject(DoctorPresenter presenter);

    void inject(CreateRecordDialogFragment createRecordDialogFragment);
}
