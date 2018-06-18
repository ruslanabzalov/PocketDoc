package com.ruslan.pocketdoc.stations;

import com.ruslan.pocketdoc.App;
import com.ruslan.pocketdoc.data.DataSource;
import com.ruslan.pocketdoc.data.Repository;
import com.ruslan.pocketdoc.data.stations.Station;

import java.util.List;

import javax.inject.Inject;

public class StationsPresenter implements StationsContract.Presenter {

    private StationsContract.View mView;

    @Inject
    Repository mRepository;

    public StationsPresenter(StationsContract.View view) {
        mView = view;
        App.getComponent().inject(this);
    }

    @Override
    public void start() {
        mView.showProgressBar();
        if (mView != null) {
            mRepository.getStations(new DataSource.OnLoadFinishedListener<Station>() {
                @Override
                public void onSuccess(List<Station> items) {
                    mView.showStationList(items);
                    mView.hideProgressBar();
                }

                @Override
                public void onFailure(Throwable throwable) {
                    mView.showLoadErrorMessage(throwable);
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
        mView.navigateToDoctorsList(station.getId());
    }
}
