package com.ruslan.pocketdoc.data;

import com.ruslan.pocketdoc.App;
import com.ruslan.pocketdoc.data.doctors.Doctor;
import com.ruslan.pocketdoc.data.specialities.Speciality;
import com.ruslan.pocketdoc.data.stations.Station;

import java.util.List;

import javax.inject.Inject;

public class Repository {

    @Inject
    RemoteDataSourceImpl mRemoteDataSource;

    @Inject
    LocalDataSourceImpl mLocalDataSource;

    public Repository() {
        App.getComponent().inject(this);
    }

    public void getSpecialities(boolean forceUpdate, DataSource.OnLoadFinishedListener<Speciality> listener) {
        if (forceUpdate) {
            mRemoteDataSource.getSpecialities(new DataSource.OnLoadFinishedListener<Speciality>() {
                @Override
                public void onSuccess(List<Speciality> specialities) {
                    if (specialities.size() != 0) {
                        mLocalDataSource.saveSpecialities(specialities);
                        listener.onSuccess(specialities);
                    }
                }

                @Override
                public void onFailure(Throwable throwable) {
                    listener.onFailure(throwable);
                }
            });
        } else {
            mLocalDataSource.getSpecialities(new DataSource.OnLoadFinishedListener<Speciality>() {
                @Override
                public void onSuccess(List<Speciality> specialities) {
                    listener.onSuccess(specialities);
                }

                @Override
                public void onFailure(Throwable throwable) {
                    mRemoteDataSource.getSpecialities(new DataSource.OnLoadFinishedListener<Speciality>() {
                        @Override
                        public void onSuccess(List<Speciality> specialities) {
                            if (specialities.size() != 0) {
                                mLocalDataSource.saveSpecialities(specialities);
                                listener.onSuccess(specialities);
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
    }

    public void getStations(boolean forceUpdate, DataSource.OnLoadFinishedListener<Station> listener) {
        if (forceUpdate) {
            mRemoteDataSource.getStations(new DataSource.OnLoadFinishedListener<Station>() {
                @Override
                public void onSuccess(List<Station> stations) {
                    if (stations.size() != 0) {
                        mLocalDataSource.saveStations(stations);
                        listener.onSuccess(stations);
                    }
                }

                @Override
                public void onFailure(Throwable throwable) {
                    listener.onFailure(throwable);
                }
            });
        } else {
            mLocalDataSource.getStations(new DataSource.OnLoadFinishedListener<Station>() {
                @Override
                public void onSuccess(List<Station> stations) {
                    listener.onSuccess(stations);
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
    }

    public void getDoctors(String specialityId, String stationId,
                           DataSource.OnLoadFinishedListener<Doctor> listener) {
        mRemoteDataSource.getDoctors(specialityId, stationId, new DataSource.OnLoadFinishedListener<Doctor>() {
            @Override
            public void onSuccess(List<Doctor> doctors) {
                if (doctors.size() != 0) {
                    listener.onSuccess(doctors);
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                listener.onFailure(throwable);
            }
        });
    }
}
