package com.ruslan.pocketdoc.doc.specs;

import android.support.annotation.NonNull;

import com.ruslan.pocketdoc.api.DocDocApi;
import com.ruslan.pocketdoc.api.DocDocClient;
import com.ruslan.pocketdoc.data.SpecList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StationsInteractor implements SpecsContract.MvpInteractor {

    @Override
    public void loadSpecs(OnLoadFinishedListener onLoadFinishedListener) {
        int moscowId = 1;
        DocDocApi api = DocDocClient.getClient();
        Call<SpecList> specsListCall = api.getSpecs(DocDocClient.AUTHORIZATION, moscowId);
        specsListCall.enqueue(new Callback<SpecList>() {
            @Override
            public void onResponse(@NonNull Call<SpecList> call,
                                   @NonNull Response<SpecList> response) {
                SpecList specList = response.body();
                if (specList != null) {
                    onLoadFinishedListener.onSuccess(specList.getSpecList());
                }
            }

            @Override
            public void onFailure(@NonNull Call<SpecList> call, @NonNull Throwable t) {
                onLoadFinishedListener.onFailure(t);
            }
        });
    }
}
