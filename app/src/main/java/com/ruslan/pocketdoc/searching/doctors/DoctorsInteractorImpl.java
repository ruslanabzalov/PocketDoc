package com.ruslan.pocketdoc.searching.doctors;

import android.support.annotation.NonNull;

import com.ruslan.pocketdoc.api.DocDocApi;
import com.ruslan.pocketdoc.api.DocDocClient;
import com.ruslan.pocketdoc.data.DoctorsList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorsInteractorImpl implements DoctorsInteractor {

    @Override
    public void loadDoctors(OnLoadFinishedListener onLoadFinishedListener,
                            String specId, String stationId) {
        int moscowId = 1;
        DocDocApi api = DocDocClient.getClient();
        Call<DoctorsList> doctorsListCall = api.getDoctors(
                0, 500, moscowId, specId, stationId, "strict",
                "rating", 0, 0, 1, 14
        );
        doctorsListCall.enqueue(new Callback<DoctorsList>() {
            @Override
            public void onResponse(@NonNull Call<DoctorsList> call,
                                   @NonNull Response<DoctorsList> response) {
                DoctorsList doctorsList = response.body();
                if (doctorsList != null) {
                    onLoadFinishedListener.onSuccess(doctorsList.getDoctorsList());
                    // TODO: Add data to database.
                }
            }

            @Override
            public void onFailure(@NonNull Call<DoctorsList> call, @NonNull Throwable t) {
                onLoadFinishedListener.onFailure(t);
            }
        });
    }
}
