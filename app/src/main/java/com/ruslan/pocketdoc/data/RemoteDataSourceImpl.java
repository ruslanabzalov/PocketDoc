package com.ruslan.pocketdoc.data;

import android.support.annotation.NonNull;

import com.ruslan.pocketdoc.api.DocDocApi;
import com.ruslan.pocketdoc.api.DocDocClient;
import com.ruslan.pocketdoc.data.doctors.Doctor;
import com.ruslan.pocketdoc.data.doctors.DoctorList;
import com.ruslan.pocketdoc.data.specialities.Speciality;
import com.ruslan.pocketdoc.data.specialities.SpecialityList;
import com.ruslan.pocketdoc.data.stations.Station;
import com.ruslan.pocketdoc.data.stations.StationList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoteDataSourceImpl implements RemoteDataSource {

    private static RemoteDataSourceImpl sRemoteDatabase = null;

    private static final int MOSCOW_ID = 1;

    private DocDocApi mApi;

    private RemoteDataSourceImpl() {
        mApi = DocDocClient.getClient();
    }

    public static RemoteDataSourceImpl getInstance() {
        if (sRemoteDatabase == null) {
            sRemoteDatabase = new RemoteDataSourceImpl();
        }
        return sRemoteDatabase;
    }

    @Override
    public void getSpecialities(OnLoadFinishedListener<Speciality> listener) {
        Call<SpecialityList> specialityListCall = mApi.getSpecialities(MOSCOW_ID);
        specialityListCall.enqueue(new Callback<SpecialityList>() {
            @Override
            public void onResponse(@NonNull Call<SpecialityList> call,
                                   @NonNull Response<SpecialityList> response) {
                if (response.body() != null) {
                    listener.onSuccess(response.body().getSpecialities());
                }
            }

            @Override
            public void onFailure(@NonNull Call<SpecialityList> call, @NonNull Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    @Override
    public void getStations(OnLoadFinishedListener<Station> listener) {
        Call<StationList> stationListCall = mApi.getStations(MOSCOW_ID);
        stationListCall.enqueue(new Callback<StationList>() {
            @Override
            public void onResponse(@NonNull Call<StationList> call, @NonNull Response<StationList> response) {
                if (response.body() != null) {
                    listener.onSuccess(response.body().getStations());
                }
            }

            @Override
            public void onFailure(@NonNull Call<StationList> call, @NonNull Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    @Override
    public void getDoctors(String specialityId, String stationId, OnLoadFinishedListener<Doctor> listener) {
        Call<DoctorList> doctorListCall = mApi.getDoctors(
                0, 500, MOSCOW_ID, specialityId, stationId, "strict",
                "rating", 0, 0, 1, 14
        );
        doctorListCall.enqueue(new Callback<DoctorList>() {
            @Override
            public void onResponse(@NonNull Call<DoctorList> call, @NonNull Response<DoctorList> response) {
                if (response.body() != null) {
                    listener.onSuccess(response.body().getDoctors());
                }
            }

            @Override
            public void onFailure(@NonNull Call<DoctorList> call, @NonNull Throwable t) {
                listener.onFailure(t);
            }
        });
    }
}
