package com.ruslan.pocketdoc.doc.stations;

import android.support.annotation.NonNull;

import com.ruslan.pocketdoc.api.DocDocApi;
import com.ruslan.pocketdoc.api.DocDocClient;
import com.ruslan.pocketdoc.data.StationList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class StationsMvpInteractor implements StationsContract.MvpInteractor {

    @Override
    public void loadStations(OnLoadFinishedListener onLoadFinishedListener) {
        int moscowId = 1;
        DocDocApi api = DocDocClient.getClient();
        Call<StationList> stationsListCall = api.getStations(DocDocClient.AUTHORIZATION, moscowId);
        stationsListCall.enqueue(new Callback<StationList>() {
            @Override
            public void onResponse(@NonNull Call<StationList> call,
                                   @NonNull Response<StationList> response) {
                StationList stationList = response.body();
                if (stationList != null) {
                    onLoadFinishedListener.onSuccess(stationList.getStationList());
                }
            }

            @Override
            public void onFailure(@NonNull Call<StationList> call, @NonNull Throwable t) {
                onLoadFinishedListener.onFailure(t);
            }
        });
    }
}
