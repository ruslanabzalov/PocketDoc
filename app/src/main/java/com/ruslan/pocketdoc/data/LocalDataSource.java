package com.ruslan.pocketdoc.data;

import com.ruslan.pocketdoc.App;
import com.ruslan.pocketdoc.data.clinics.Clinic;
import com.ruslan.pocketdoc.data.clinics.ClinicList;
import com.ruslan.pocketdoc.data.specialities.Speciality;
import com.ruslan.pocketdoc.data.specialities.SpecialityList;
import com.ruslan.pocketdoc.data.stations.Station;
import com.ruslan.pocketdoc.data.stations.StationList;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;

public class LocalDataSource implements LocalDataSourceContract {

    @Inject
    AppDatabase mDatabase;

    public LocalDataSource() {
        App.getComponent().inject(this);
    }

    @Override
    public Flowable<List<Speciality>> getSpecialities() {
        return mDatabase.specialityDao().getAllSpecialities();
    }

    @Override
    public Completable saveSpecialities(List<Speciality> specialities) {
        return Completable.fromAction(() -> mDatabase.specialityDao().insertSpecialities(specialities));
    }

    @Override
    public Observable<StationList> getStations() {
        return null;
    }

    @Override
    public void saveStations(List<Station> stations) {}

    @Override
    public Observable<ClinicList> getClinics() {
        return null;
    }

    @Override
    public void saveClinics(List<Clinic> clinics) {}
}
