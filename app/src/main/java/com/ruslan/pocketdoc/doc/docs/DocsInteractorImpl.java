package com.ruslan.pocketdoc.doc.docs;

import android.support.annotation.NonNull;

import com.ruslan.pocketdoc.api.DocDocApi;
import com.ruslan.pocketdoc.api.DocDocClient;
import com.ruslan.pocketdoc.data.DocList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DocsInteractorImpl implements DocsInteractor {

    @Override
    public void loadDocs(OnLoadFinishedListener onLoadFinishedListener,
                         String specId, String stationId) {
        int moscowId = 1;
        DocDocApi api = DocDocClient.getClient();
        Call<DocList> docListCall = api.getDocs(
                DocDocClient.AUTHORIZATION, 0, 500, moscowId, specId, stationId,
                "strict", "rating", 0, 0, 1, 14
        );
        docListCall.enqueue(new Callback<DocList>() {
            @Override
            public void onResponse(@NonNull Call<DocList> call,
                                   @NonNull Response<DocList> response) {
                DocList docList = response.body();
                if (docList != null) {
                    onLoadFinishedListener.onSuccess(docList.getDocList());
                    // TODO: Add data to database.
                }
            }

            @Override
            public void onFailure(@NonNull Call<DocList> call, @NonNull Throwable t) {
                onLoadFinishedListener.onFailure(t);
            }
        });
    }
}
