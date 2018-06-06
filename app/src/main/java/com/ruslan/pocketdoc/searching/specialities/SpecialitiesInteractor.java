package com.ruslan.pocketdoc.searching.specialities;

import android.support.annotation.NonNull;

import com.ruslan.pocketdoc.api.DocDocApi;
import com.ruslan.pocketdoc.api.DocDocClient;
import com.ruslan.pocketdoc.data.SpecialityList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpecialitiesInteractor implements SpecialitiesContract.Interactor {

    private static final int MOSCOW_ID = 1;

    @Override
    public void loadSpecialities(OnLoadFinishedListener listener) {
        DocDocApi api = DocDocClient.getClient();
        Call<SpecialityList> specialityListCall = api.getSpecialities(MOSCOW_ID);
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
}
