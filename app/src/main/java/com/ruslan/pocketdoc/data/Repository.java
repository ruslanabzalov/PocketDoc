package com.ruslan.pocketdoc.data;

import com.ruslan.pocketdoc.App;
import com.ruslan.pocketdoc.data.clinics.Clinic;
import com.ruslan.pocketdoc.data.doctors.Doctor;
import com.ruslan.pocketdoc.data.specialities.Speciality;
import com.ruslan.pocketdoc.data.stations.Station;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class Repository {

    @SuppressWarnings("WeakerAccess")
    @Inject
    RemoteDataSource mRemoteDataSource;

    @SuppressWarnings("WeakerAccess")
    @Inject
    LocalDataSource mLocalDataSource;

    public Repository() {
        App.getComponent().inject(this);
    }

    public Flowable<List<Speciality>> getSpecialities(boolean forceUpdate) {
        if (forceUpdate) {
            return mRemoteDataSource.getSpecialities();
        } else {
            return mLocalDataSource.getSpecialities()
                    .flatMap(specialities -> {
                        if (specialities.size() == 0) {
                            return mRemoteDataSource.getSpecialities().doOnNext(this::saveSpecialities);
                        } else {
                            return Flowable.just(specialities);
                        }
                    });
        }
    }

    private void saveSpecialities(List<Speciality> specialities) {
        mLocalDataSource.saveSpecialities(specialities)
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public Flowable<List<Station>> getStations(boolean forceUpdate) {
        if (forceUpdate) {
            return mRemoteDataSource.getStations()
                    .doOnNext(this::saveStations);

        } else {
            return mLocalDataSource.getStations()
                    .flatMap(stations -> (stations.size() == 0)
                            ? mRemoteDataSource.getStations().doOnNext(this::saveStations)
                            : Flowable.just(stations));
        }
    }

    private void saveStations(List<Station> stations) {
        mLocalDataSource.saveStations(stations)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
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

    public Flowable<List<Clinic>> getClinics(boolean forceUpdate) {
        if (forceUpdate) {
            return getClinicsZipped()
                    .doOnNext(this::saveClinics);
        } else {
            return mLocalDataSource.getClinics()
                    .flatMap(clinics -> {
                        if (clinics.size() == 0) {
                            return getClinicsZipped()
                                    .doOnNext(this::saveClinics);
                        } else {
                            return Flowable.fromIterable(clinics).toList().toFlowable();
                        }
                    });
        }
    }

    public Flowable<List<Clinic>> getOnlyClinics() {
        return mLocalDataSource.getOnlyClinics();
    }

    public Flowable<List<Clinic>> getOnlyDiagnostics() {
        return mLocalDataSource.getOnlyDiagnostics();
    }

    private Flowable<List<Clinic>> getClinicsZipped() {
        return Flowable.zip(mRemoteDataSource.getClinics(0, 500),
                mRemoteDataSource.getClinics(500, 500), (firstClinics, secondClinics) -> {
            List<Clinic> finalClinicList = new ArrayList<>(firstClinics);
            finalClinicList.addAll(secondClinics);
            return finalClinicList;
        });
    }

    private void saveClinics(List<Clinic> clinics) {
        mLocalDataSource.saveClinics(clinics)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}
