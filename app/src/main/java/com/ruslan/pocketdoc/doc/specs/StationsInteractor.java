package com.ruslan.pocketdoc.doc.specs;

import android.support.annotation.NonNull;

import com.ruslan.pocketdoc.api.DocDocApi;
import com.ruslan.pocketdoc.api.DocDocClient;
import com.ruslan.pocketdoc.data.SpecsList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StationsInteractor implements SpecsContract.MvpInteractor {

    @Override
    public void loadSpecs(OnLoadFinishedListener onLoadFinishedListener) {
        int moscowId = 1;
        DocDocApi api = DocDocClient.getClient();
        Call<SpecsList> specsListCall = api.getSpecs(DocDocClient.AUTHORIZATION, moscowId);
        specsListCall.enqueue(new Callback<SpecsList>() {
            @Override
            public void onResponse(@NonNull Call<SpecsList> call,
                                   @NonNull Response<SpecsList> response) {
                SpecsList specsList = response.body();
                if (specsList != null) {
                    onLoadFinishedListener.onSuccess(specsList.getSpecialities());
                }
            }

            @Override
            public void onFailure(@NonNull Call<SpecsList> call, @NonNull Throwable t) {
                onLoadFinishedListener.onFailure(t);
            }
        });
    }
}
