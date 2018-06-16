package com.ruslan.pocketdoc.data;

import android.content.Context;

import com.ruslan.pocketdoc.data.doctors.Doctor;
import com.ruslan.pocketdoc.data.specialities.Speciality;
import com.ruslan.pocketdoc.data.stations.Station;

import java.util.List;

public class Repository {

    private static Repository sRepository = null;

    private RemoteDataSourceImpl mRemoteDataSource;
    private LocalDataSourceImpl mLocalDataSource;

    private Repository(Context context) {
        mRemoteDataSource = RemoteDataSourceImpl.getInstance();
        mLocalDataSource = LocalDataSourceImpl.getInstance(context);
    }

    public static Repository getInstance(Context context) {
        if (sRepository == null) {
            sRepository = new Repository(context);
        }
        return sRepository;
    }

    public void getSpecialities(DataSource.OnLoadFinishedListener<Speciality> listener) {
        mLocalDataSource.getSpecialities(new DataSource.OnLoadFinishedListener<Speciality>() {

            @Override
            public void onSuccess(List<Speciality> items) {
                listener.onSuccess(items);
            }

            @Override
            public void onFailure(Throwable throwable) {
                mRemoteDataSource.getSpecialities(new DataSource.OnLoadFinishedListener<Speciality>() {

                    @Override
                    public void onSuccess(List<Speciality> items) {
                        if (items.size() != 0) {
                            mLocalDataSource.saveSpecialities(items);
                            listener.onSuccess(items);
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        listener.onFailure(throwable);
                    }
                });
            }
        });
    }

    public void getStations(DataSource.OnLoadFinishedListener<Station> listener) {
        mLocalDataSource.getStations(new DataSource.OnLoadFinishedListener<Station>() {
            @Override
            public void onSuccess(List<Station> items) {
                listener.onSuccess(items);
            }

            @Override
            public void onFailure(Throwable throwable) {
                mRemoteDataSource.getStations(new DataSource.OnLoadFinishedListener<Station>() {
                    @Override
                    public void onSuccess(List<Station> items) {
                        if (items.size() != 0) {
                            mLocalDataSource.saveStations(items);
                            listener.onSuccess(items);
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        listener.onFailure(throwable);
                    }
                });
            }
        });
    }

    public void getDoctors(String specialityId, String stationId,
                           DataSource.OnLoadFinishedListener<Doctor> listener) {
        mRemoteDataSource.getDoctors(specialityId, stationId, new DataSource.OnLoadFinishedListener<Doctor>() {
            @Override
            public void onSuccess(List<Doctor> items) {
                if (items.size() != 0) {
                    listener.onSuccess(items);
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                listener.onFailure(throwable);
            }
        });
    }
}
