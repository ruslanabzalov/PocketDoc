package com.ruslan.pocketdoc.data;

import com.ruslan.pocketdoc.App;
import com.ruslan.pocketdoc.data.clinics.Clinic;
import com.ruslan.pocketdoc.data.clinics.ClinicList;
import com.ruslan.pocketdoc.data.doctors.Doctor;
import com.ruslan.pocketdoc.data.doctors.DoctorList;
import com.ruslan.pocketdoc.data.specialities.Speciality;
import com.ruslan.pocketdoc.data.specialities.SpecialityList;
import com.ruslan.pocketdoc.data.stations.Station;
import com.ruslan.pocketdoc.data.stations.StationList;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
            return mRemoteDataSource.getSpecialities()
                    .map(SpecialityList::getSpecialities)
                    .doOnNext(this::saveSpecialities);
        } else {
            return mLocalDataSource.getSpecialities()
                    .flatMap(specialities -> {
                        if (specialities.size() == 0) {
                            return mRemoteDataSource.getSpecialities()
                                    .map(SpecialityList::getSpecialities)
                                    .doOnNext(this::saveSpecialities);
                        } else {
                            return Flowable.fromIterable(specialities).toList().toFlowable();
                        }
                    });
        }
    }

    private void saveSpecialities(List<Speciality> specialities) {
        mLocalDataSource.saveSpecialities(specialities)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public Flowable<List<Station>> getStations(boolean forceUpdate) {
        if (forceUpdate) {
            return mRemoteDataSource.getStations()
                    .map(StationList::getStations)
                    .doOnNext(this::saveStations);

        } else {
            return mLocalDataSource.getStations()
                    .flatMap(stations -> {
                       if (stations.size() == 0) {
                           return mRemoteDataSource.getStations()
                                   .map(StationList::getStations)
                                   .doOnNext(this::saveStations);
                       } else {
                           return Flowable.fromIterable(stations).toList().toFlowable();
                       }
                    });
        }
    }

    private void saveStations(List<Station> stations) {
        mLocalDataSource.saveStations(stations)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public Flowable<List<Doctor>> getDoctors(String specialityId, String stationId) {
        return mRemoteDataSource.getDoctors(specialityId, stationId)
                .map(DoctorList::getDoctors);
    }

    public Flowable<Doctor> getDoctorInfo(int doctorId) {
        return mRemoteDataSource.getDoctor(doctorId)
                .map(doctorInfo -> doctorInfo.getDoctor().get(0));
    }

    public Flowable<List<Clinic>> getClinics(boolean forceUpdate) {
        if (forceUpdate) {
            return mRemoteDataSource.getClinics()
                    .map(ClinicList::getClinics)
                    .doOnNext(this::saveClinics);
        } else {
            return mLocalDataSource.getClinics()
                    .flatMap(clinics -> {
                        if (clinics.size() == 0) {
                            return mRemoteDataSource.getClinics()
                                    .map(ClinicList::getClinics)
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

    private void saveClinics(List<Clinic> clinics) {
        mLocalDataSource.saveClinics(clinics)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}
