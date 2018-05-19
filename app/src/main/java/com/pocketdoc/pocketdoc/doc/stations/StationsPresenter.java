package com.pocketdoc.pocketdoc.doc.stations;

import com.pocketdoc.pocketdoc.data.Station;

import java.util.List;

class StationsPresenter implements StationsContract.Presenter {

    private StationsContract.MainView mStationsView;
    private StationsContract.Fetcher mStationsFetcher;

    StationsPresenter(StationsContract.MainView mainView,
                              StationsContract.Fetcher stationsFetcher) {
        mStationsView = mainView;
        mStationsFetcher = stationsFetcher;
    }

    @Override
    public void onDestroy() {
        mStationsView = null;
    }

    @Override
    public void getData() {
        mStationsFetcher.getStationsData(new StationsContract.Fetcher.OnStationsFetchListener() {
            @Override
            public void onSuccess(List<Station> stations) {
                if (mStationsView != null) {
                    mStationsView.setDataToRecyclerView(stations);
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                mStationsView.onFailureFetchedMessage(throwable);
            }
        });
    }
}
