package com.pocketdoc.pocketdoc.doc.specs;

import android.support.annotation.NonNull;

import com.pocketdoc.pocketdoc.api.DocDocApi;
import com.pocketdoc.pocketdoc.api.DocDocClient;
import com.pocketdoc.pocketdoc.data.SpecsList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fetcher implements SpecsContract.Fetcher {

    @Override
    public void getSpecs(OnFetchListener onFetchListener) {
        int moscowId = 1;
        DocDocApi api = DocDocClient.getClient();
        Call<SpecsList> specsListCall = api.getSpecs(DocDocClient.AUTHORIZATION, moscowId);
        specsListCall.enqueue(new Callback<SpecsList>() {
            @Override
            public void onResponse(@NonNull Call<SpecsList> call,
                                   @NonNull Response<SpecsList> response) {
                SpecsList specsList = response.body();
                if (specsList != null) {
                    onFetchListener.onSuccess(specsList.getSpecialities());
                }
            }

            @Override
            public void onFailure(@NonNull Call<SpecsList> call, @NonNull Throwable t) {
                onFetchListener.onFailure(t);
            }
        });
    }
}
