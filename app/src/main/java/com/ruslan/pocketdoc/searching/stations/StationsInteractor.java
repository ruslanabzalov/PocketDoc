package com.ruslan.pocketdoc.searching.stations;

import android.support.annotation.NonNull;

import com.ruslan.pocketdoc.api.DocDocApi;
import com.ruslan.pocketdoc.api.DocDocClient;
import com.ruslan.pocketdoc.data.StationList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class StationsInteractor implements StationsContract.Interactor {

    private static final int MOSCOW_ID = 1;

    @Override
    public void loadStations(OnLoadFinishedListener listener) {
        DocDocApi api = DocDocClient.getClient();
        Call<StationList> stationsListCall = api.getStations(MOSCOW_ID);
        stationsListCall.enqueue(new Callback<StationList>() {
            @Override
            public void onResponse(@NonNull Call<StationList> call,
                                   @NonNull Response<StationList> response) {
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
}
