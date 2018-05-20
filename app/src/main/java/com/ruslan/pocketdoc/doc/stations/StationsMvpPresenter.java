package com.ruslan.pocketdoc.doc.stations;

import com.ruslan.pocketdoc.data.Station;

import java.util.List;

class StationsMvpPresenter implements StationsContract.MvpPresenter {

    private StationsContract.MvpView mStationsView;
    private StationsContract.MvpInteractor mStationsMvpInteractor;

    StationsMvpPresenter(StationsContract.MvpView mvpView,
                         StationsContract.MvpInteractor stationsMvpInteractor) {
        mStationsView = mvpView;
        mStationsMvpInteractor = stationsMvpInteractor;
    }

    @Override
    public void onDestroy() {
        mStationsView = null;
    }

    @Override
    public void getData() {
        mStationsMvpInteractor
                .loadStations(new StationsContract.MvpInteractor.OnLoadFinishedListener() {
            @Override
            public void onSuccess(List<Station> stations) {
                if (mStationsView != null) {
                    mStationsView.showStations(stations);
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                mStationsView.showLoadErrorMessage(throwable);
            }
        });
    }
}
