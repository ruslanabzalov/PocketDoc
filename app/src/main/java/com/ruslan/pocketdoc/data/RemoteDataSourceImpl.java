package com.ruslan.pocketdoc.data;

import android.support.annotation.NonNull;

import com.ruslan.pocketdoc.App;
import com.ruslan.pocketdoc.api.DocDocApi;
import com.ruslan.pocketdoc.data.clinics.Clinic;
import com.ruslan.pocketdoc.data.clinics.ClinicList;
import com.ruslan.pocketdoc.data.doctors.Doctor;
import com.ruslan.pocketdoc.data.doctors.DoctorList;
import com.ruslan.pocketdoc.data.specialities.Speciality;
import com.ruslan.pocketdoc.data.specialities.SpecialityList;
import com.ruslan.pocketdoc.data.stations.Station;
import com.ruslan.pocketdoc.data.stations.StationList;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoteDataSourceImpl implements RemoteDataSource {

    @Inject
    DocDocApi mApi;

    public RemoteDataSourceImpl() {
        App.getComponent().inject(this);
    }

    @Override
    public void getSpecialities(OnLoadFinishedListener<Speciality> listener) {
        Call<SpecialityList> specialityListCall = mApi.getSpecialities();
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
        Call<StationList> stationListCall = mApi.getStations();
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
                0, 500, specialityId, stationId, "strict",
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

    @Override
    public void getClinics(OnLoadFinishedListener<Clinic> listener) {
        Call<ClinicList> clinicListCall = mApi.getClinics();
        clinicListCall.enqueue(new Callback<ClinicList>() {
            @Override
            public void onResponse(@NonNull Call<ClinicList> call, @NonNull Response<ClinicList> response) {
                if (response.body() != null) {
                    listener.onSuccess(response.body().getClinics());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ClinicList> call, @NonNull Throwable t) {
                listener.onFailure(t);
            }
        });
    }
}
