package com.ruslan.pocketdoc.data;

import com.ruslan.pocketdoc.App;
import com.ruslan.pocketdoc.data.clinics.Clinic;
import com.ruslan.pocketdoc.data.clinics.ClinicsDao;
import com.ruslan.pocketdoc.data.specialities.SpecialitiesDao;
import com.ruslan.pocketdoc.data.specialities.Speciality;
import com.ruslan.pocketdoc.data.stations.Station;
import com.ruslan.pocketdoc.data.stations.StationDao;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * Класс, реализующий интерфейс работы с БД Room.
 */
public class LocalDataSource implements LocalDataSourceContract {

    @Inject
    AppDatabase mDatabase;

    private SpecialitiesDao mSpecialitiesDao;
    private StationDao mStationDao;
    private ClinicsDao mClinicsDao;

    public LocalDataSource() {
        App.getComponent().inject(this);
        mSpecialitiesDao = mDatabase.specialityDao();
        mStationDao = mDatabase.stationDao();
        mClinicsDao = mDatabase.clinicsDao();
    }

    @Override
    public Flowable<List<Speciality>> getSpecialities() {
        return mSpecialitiesDao.getAllSpecialities();
    }

    @Override
    public void saveSpecialities(List<Speciality> specialities) {
        mSpecialitiesDao.insertSpecialities(specialities);
    }

    @Override
    public Flowable<List<Station>> getStations() {
        return mStationDao.getAllStations();
    }

    @Override
    public void saveStations(List<Station> stations) {
        mStationDao.insertStations(stations);
    }

    @Override
    public Single<Integer> countClinics() {
        return mClinicsDao.countAll();
    }

    @Override
    public Flowable<List<Clinic>> getAllClinics() {
        return mClinicsDao.getAllClinics();
    }

    @Override
    public Flowable<List<Clinic>> getOnlyClinics(String isDiagnostic) {
        return mClinicsDao.getOnlyClinics(isDiagnostic);
    }

    @Override
    public Flowable<List<Clinic>> getOnlyDiagnostics(String isDiagnostic) {
        return mClinicsDao.getOnlyDiagnostics(isDiagnostic);
    }

    @Override
    public void saveClinics(List<Clinic> clinics) {
        mClinicsDao.clearTable();
        mClinicsDao.insertClinics(clinics);
    }
}
