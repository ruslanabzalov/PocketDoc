package com.ruslan.pocketdoc.data;

import com.ruslan.pocketdoc.App;
import com.ruslan.pocketdoc.api.CreateRecordRequestSchedule;
import com.ruslan.pocketdoc.api.CreateRecordResponse;
import com.ruslan.pocketdoc.data.clinics.Clinic;
import com.ruslan.pocketdoc.data.doctors.Doctor;
import com.ruslan.pocketdoc.data.specialities.Speciality;
import com.ruslan.pocketdoc.data.stations.Station;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Single;

public class Repository {

    @Inject
    RemoteDataSource mRemoteDataSource;

    @Inject
    LocalDataSource mLocalDataSource;

    public Repository() {
        App.getComponent().inject(this);
    }

    public Flowable<List<Speciality>> getSpecialities(boolean forceUpdate) {
        if (forceUpdate) {
            return getAndSaveRemoteSpecialities();
        } else {
            return mLocalDataSource.getSpecialities()
                    // TODO: Работает некорректно. Переделать!
                    .switchMap(specialities -> (specialities.size() == 0)
                            ? getAndSaveRemoteSpecialities()
                            : Flowable.just(specialities));
        }
    }

    private Flowable<List<Speciality>> getAndSaveRemoteSpecialities() {
        return mRemoteDataSource.getSpecialities()
                .doOnNext(mLocalDataSource::saveSpecialities);
    }

    public Flowable<List<Station>> getStations(boolean forceUpdate) {
        if (forceUpdate) {
            return getAndSaveRemoteStations();
        } else {
            return mLocalDataSource.getStations()
                    // TODO: Работает некорректно. Переделать!
                    .switchMap(stations -> (stations.size() == 0)
                            ? getAndSaveRemoteStations()
                            : Flowable.just(stations));
        }
    }

    private Flowable<List<Station>> getAndSaveRemoteStations() {
        return mRemoteDataSource.getStations()
                .doOnNext(mLocalDataSource::saveStations);
    }

    public Flowable<List<Doctor>> getDoctors(String specialityId, String stationId) {
        return mRemoteDataSource.getDoctors(specialityId, stationId);
    }

    public Single<Doctor> getDoctorInfo(int doctorId) {
        return mRemoteDataSource.getDoctor(doctorId);
    }

    public Single<Integer> getClinicsCount() {
        return mLocalDataSource.countClinics();
    }

    public Flowable<List<Clinic>> getClinicsFromApi() {
        return Flowable.zip(mRemoteDataSource.getClinics(0, 500),
                mRemoteDataSource.getClinics(500, 500), (firstClinics, secondClinics) -> {
            List<Clinic> allClinics = new ArrayList<>(firstClinics);
            allClinics.addAll(secondClinics);
            return allClinics;
        })
                .doOnNext(mLocalDataSource::saveClinics);
    }

    public Flowable<List<Clinic>> getAllClinicsFromDb() {
        return mLocalDataSource.getAllClinics();
    }

    public Flowable<List<Clinic>> getOnlyClinicsFromDb(String isDiagnostic) {
        return mLocalDataSource.getOnlyClinics(isDiagnostic);
    }

    public Flowable<List<Clinic>> getOnlyDiagnosticsFromDb(String isDiagnostic) {
        return mLocalDataSource.getOnlyDiagnostics(isDiagnostic);
    }

    public Single<Clinic> getClinicByIdFromDb(int id) {
        return mLocalDataSource.getClinicById(id);
    }

    public Single<CreateRecordResponse> createRecord(CreateRecordRequestSchedule recordRequest) {
        return mRemoteDataSource.createRecord(recordRequest);
    }
}
