package com.ruslan.pocketdoc.searching.stations;

import com.ruslan.pocketdoc.data.Station;

import java.util.List;

class StationsPresenter implements StationsContract.Presenter {

    private StationsContract.View mStationsView;
    private StationsContract.Interactor mStationsInteractor;

    StationsPresenter(StationsContract.View stationsView,
                      StationsContract.Interactor stationsInteractor) {
        mStationsView = stationsView;
        mStationsInteractor = stationsInteractor;
    }

    @Override
    public void onDestroy() {
        mStationsView = null;
    }

    @Override
    public void getStations() {
        mStationsInteractor.loadStations(new StationsContract.Interactor.OnLoadFinishedListener() {
            @Override
            public void onSuccess(List<Station> stationList) {
                if (mStationsView != null) {
                    mStationsView.showStationList(stationList);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                if (mStationsView != null) {
                    mStationsView.showLoadErrorMessage(t);
                }
            }
        });
    }
}
