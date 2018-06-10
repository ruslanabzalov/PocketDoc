package com.ruslan.pocketdoc.searching.stations;

import com.ruslan.pocketdoc.data.stations.Station;

import java.util.List;

class StationsPresenter implements StationsContract.Presenter {

    private StationsContract.View mView;
    private StationsContract.Interactor mInteractor;

    StationsPresenter(StationsContract.View view,
                      StationsContract.Interactor interactor) {
        mView = view;
        mInteractor = interactor;
    }

    @Override
    public void start() {
        mView.showProgressBar();
        if (mView != null) {
            mInteractor.loadStations(new StationsContract.Interactor.OnLoadFinishedListener() {
                @Override
                public void onSuccess(List<Station> stationList) {
                    mView.showStationList(stationList);
                    mView.hideProgressBar();
                }

                @Override
                public void onFailure(Throwable t) {
                    mView.showLoadErrorMessage(t);
                    mView.hideProgressBar();
                }
            });
        }
    }

    @Override
    public void stop() {
        mView = null;
    }

    @Override
    public void onStationClick(Station station) {
        mView.navigateToDoctorsActivity(station.getId());
    }
}
