package com.ruslan.pocketdoc.searching.stations;

import com.ruslan.pocketdoc.data.Station;
import com.ruslan.pocketdoc.searching.BaseInteractor;
import com.ruslan.pocketdoc.searching.BasePresenter;
import com.ruslan.pocketdoc.searching.BaseView;

import java.util.List;

class StationsPresenter implements BasePresenter {

    private BaseView<Station> mStationView;
    private BaseInteractor<Station> mStationInteractor;

    StationsPresenter(BaseView<Station> stationView, BaseInteractor<Station> stationInteractor) {
        mStationView = stationView;
        mStationInteractor = stationInteractor;
    }

    @Override
    public void onResume() {
        mStationInteractor.loadData(new BaseInteractor.OnLoadFinishedListener<Station>() {
            @Override
            public void onSuccess(List<Station> stations) {
                if (mStationView != null) {
                    mStationView.showItems(stations);
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                if (mStationView != null) {
                    mStationView.showErrorMessage(throwable);
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        mStationView = null;
    }
}
