package com.ruslan.pocketdoc.doc.stations;

import android.support.annotation.NonNull;

import com.ruslan.pocketdoc.api.DocDocApi;
import com.ruslan.pocketdoc.api.DocDocClient;
import com.ruslan.pocketdoc.data.StationsList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class StationsMvpInteractor implements StationsContract.MvpInteractor {

    @Override
    public void loadStations(OnLoadFinishedListener onLoadFinishedListener) {
        int moscowId = 1;
        DocDocApi api = DocDocClient.getClient();
        Call<StationsList> stationsListCall = api.getStations(DocDocClient.AUTHORIZATION, moscowId);
        stationsListCall.enqueue(new Callback<StationsList>() {
            @Override
            public void onResponse(@NonNull Call<StationsList> call,
                                   @NonNull Response<StationsList> response) {
                StationsList stationsList = response.body();
                if (stationsList != null) {
                    onLoadFinishedListener.onSuccess(stationsList.getStations());
                }
            }

            @Override
            public void onFailure(@NonNull Call<StationsList> call, @NonNull Throwable t) {
                onLoadFinishedListener.onFailure(t);
            }
        });
    }
}
