package com.ruslan.pocketdoc.searching.doctors;

import android.support.annotation.NonNull;

import com.ruslan.pocketdoc.api.DocDocApi;
import com.ruslan.pocketdoc.api.DocDocClient;
import com.ruslan.pocketdoc.data.DoctorList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class DoctorsInteractor implements DoctorsContract.Interactor {

    private static final int MOSCOW_ID = 1;

    @Override
    public void loadDoctors(String specialityId, String stationId,
                            OnLoadFinishedListener listener) {
        final DocDocApi api = DocDocClient.getClient();
        final Call<DoctorList> doctorListCall = api.getDoctors(
                0, 500, MOSCOW_ID, specialityId, stationId, "strict",
                "rating", 0, 0, 1, 14
        );
        doctorListCall.enqueue(new Callback<DoctorList>() {
            @Override
            public void onResponse(@NonNull Call<DoctorList> call,
                                   @NonNull Response<DoctorList> response) {
                if (response.body() != null) {
                    listener.onSuccess(response.body().getDoctorList());
                }
            }

            @Override
            public void onFailure(@NonNull Call<DoctorList> call, @NonNull Throwable t) {
                listener.onFailure(t);
            }
        });
    }
}
