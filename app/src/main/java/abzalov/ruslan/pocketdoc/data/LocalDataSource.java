package abzalov.ruslan.pocketdoc.data;

import abzalov.ruslan.pocketdoc.App;
import abzalov.ruslan.pocketdoc.data.clinics.Clinic;
import abzalov.ruslan.pocketdoc.data.clinics.ClinicsDao;
import abzalov.ruslan.pocketdoc.data.specialities.SpecialitiesDao;
import abzalov.ruslan.pocketdoc.data.specialities.Speciality;
import abzalov.ruslan.pocketdoc.data.stations.Station;
import abzalov.ruslan.pocketdoc.data.stations.StationDao;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Single;

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
    public Single<Clinic> getClinicById(int id) {
        return mClinicsDao.getClinicById(id);
    }

    @Override
    public void saveClinics(List<Clinic> clinics) {
        mClinicsDao.clearTable();
        mClinicsDao.insertClinics(clinics);
    }
}