package com.ruslan.pocketdoc.searching.stations;

import com.ruslan.pocketdoc.data.Station;
import com.ruslan.pocketdoc.BaseContract;

import java.util.List;

class StationsPresenter implements BaseContract.BasePresenter {

    private StationsContract.View mStationsView;
    private StationsContract.Interactor mStationsInteractor;

    StationsPresenter(StationsContract.View stationsView,
                      StationsContract.Interactor stationsInteractor) {
        mStationsView = stationsView;
        mStationsInteractor = stationsInteractor;
    }

    @Override
    public void start() {
        mStationsView.showProgressBar();
        if (mStationsView != null) {
            mStationsInteractor.loadStations(new StationsContract.Interactor.OnLoadFinishedListener() {
                @Override
                public void onSuccess(List<Station> stationList) {
                    mStationsView.showStationList(stationList);
                    mStationsView.hideProgressBar();
                }

                @Override
                public void onFailure(Throwable t) {
                    mStationsView.showLoadErrorMessage(t);
                    mStationsView.hideProgressBar();
                }
            });
        }
    }

    @Override
    public void stop() {
        mStationsView = null;
    }
}
