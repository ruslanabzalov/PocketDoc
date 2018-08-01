package com.ruslan.pocketdoc.data;

import com.ruslan.pocketdoc.App;
import com.ruslan.pocketdoc.data.clinics.Clinic;
import com.ruslan.pocketdoc.data.specialities.Speciality;
import com.ruslan.pocketdoc.data.stations.Station;

import java.util.List;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Single;

public class LocalDataSource implements LocalDataSourceContract {

    @Inject
    AppDatabase mDatabase;

    @Inject
    ExecutorService mExecutorService;

    public LocalDataSource() {
        App.getComponent().inject(this);
    }

    @Override
    public Flowable<List<Speciality>> getSpecialities() {
        return mDatabase.specialityDao().getAllSpecialities();
    }

    @Override
    public void saveSpecialities(List<Speciality> specialities) {
        mDatabase.specialityDao().insertSpecialities(specialities);
    }

    @Override
    public Flowable<List<Station>> getStations() {
        return mDatabase.stationDao().getAllStations();
    }

    @Override
    public void saveStations(List<Station> stations) {
        mDatabase.stationDao().insertStations(stations);
    }

    @Override
    public Single<Integer> countClinics() {
        return mDatabase.clinicsDao().countAll();
    }

    @Override
    public Flowable<List<Clinic>> getClinics() {
        return mDatabase.clinicsDao().getAllClinics();
    }

    @Override
    public void saveClinics(List<Clinic> clinics) {
        mDatabase.clinicsDao().insertClinics(clinics);
    }
}
