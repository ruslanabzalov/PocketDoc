package com.pocketdoc.pocketdoc.doc.stations;

import android.support.annotation.NonNull;

import com.pocketdoc.pocketdoc.api.DocDocApi;
import com.pocketdoc.pocketdoc.api.DocDocClient;
import com.pocketdoc.pocketdoc.data.StationsList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class Fetcher implements StationsContract.Fetcher {

    @Override
    public void getStationsData(OnStationsFetchListener onStationsFetchListener) {
        int moscowId = 1;
        DocDocApi api = DocDocClient.getClient();
        Call<StationsList> stationsListCall = api.getStations(DocDocClient.AUTHORIZATION, moscowId);
        stationsListCall.enqueue(new Callback<StationsList>() {
            @Override
            public void onResponse(@NonNull Call<StationsList> call,
                                   @NonNull Response<StationsList> response) {
                StationsList stationsList = response.body();
                if (stationsList != null) {
                    onStationsFetchListener.onSuccess(stationsList.getStations());
                }
            }

            @Override
            public void onFailure(@NonNull Call<StationsList> call, @NonNull Throwable t) {
                onStationsFetchListener.onFailure(t);
            }
        });
    }
}
