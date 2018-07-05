package com.ruslan.pocketdoc.data;

import com.ruslan.pocketdoc.App;
import com.ruslan.pocketdoc.data.clinics.ClinicList;
import com.ruslan.pocketdoc.data.doctors.DoctorList;
import com.ruslan.pocketdoc.data.specialities.Speciality;
import com.ruslan.pocketdoc.data.specialities.SpecialityList;
import com.ruslan.pocketdoc.data.stations.StationList;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
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

    public Flowable<List<Speciality>> getSpecsFromDb() {
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

    public Flowable<SpecialityList> getSpecialities(boolean forceUpdate) {
        return mRemoteDataSource.getSpecialities();
    }

    public void saveSpecialities(List<Speciality> specialities) {
        mLocalDataSource.saveSpecialities(specialities)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public Observable<StationList> getStations(boolean forceUpdate) {
        return mRemoteDataSource.getStations();
    }

    public Observable<DoctorList> getDoctors(String specialityId, String stationId, boolean forceUpdate) {
        return mRemoteDataSource.getDoctors(specialityId, stationId);
    }

    public Observable<ClinicList> getClinics(boolean forceUpdate) {
        return mRemoteDataSource.getClinics();
    }
}
