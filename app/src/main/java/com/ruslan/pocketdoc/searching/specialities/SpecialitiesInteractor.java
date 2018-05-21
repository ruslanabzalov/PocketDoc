package com.ruslan.pocketdoc.searching.specialities;

import android.support.annotation.NonNull;

import com.ruslan.pocketdoc.api.DocDocApi;
import com.ruslan.pocketdoc.api.DocDocClient;
import com.ruslan.pocketdoc.data.Speciality;
import com.ruslan.pocketdoc.data.SpecialitiesList;
import com.ruslan.pocketdoc.searching.BaseInteractor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpecialitiesInteractor implements BaseInteractor<Speciality> {

    @Override
    public void loadData(OnLoadFinishedListener<Speciality> onLoadFinishedListener) {
        int moscowId = 1;
        DocDocApi api = DocDocClient.getClient();
        Call<SpecialitiesList> specsListCall = api.getSpecialities(DocDocClient.AUTHORIZATION, moscowId);
        specsListCall.enqueue(new Callback<SpecialitiesList>() {
            @Override
            public void onResponse(@NonNull Call<SpecialitiesList> call,
                                   @NonNull Response<SpecialitiesList> response) {
                SpecialitiesList specialitiesList = response.body();
                if (specialitiesList != null) {
                    onLoadFinishedListener.onSuccess(specialitiesList.getSpecialitiesList());
                }
            }

            @Override
            public void onFailure(@NonNull Call<SpecialitiesList> call, @NonNull Throwable t) {
                onLoadFinishedListener.onFailure(t);
            }
        });
    }
}
