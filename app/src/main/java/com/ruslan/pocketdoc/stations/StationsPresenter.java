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

    public StationsPresenter() {
        App.getComponent().inject(this);
    }

    @Override
    public void attachView(StationsContract.View view) {
        mView = view;
    }

    @Override
    public void detach() {
        mView = null;
    }

    @Override
    public void loadStations() {
        mView.showProgressBar();
        mRepository.getStations(new DataSource.OnLoadFinishedListener<Station>() {
            @Override
            public void onSuccess(List<Station> stations) {
                mView.showStations(stations);
                mView.hideProgressBar();
            }

            @Override
            public void onFailure(Throwable throwable) {
                mView.showErrorMessage(throwable);
                mView.hideProgressBar();
            }
        });
    }

    @Override
    public void onStationClick(Station station) {
        mView.showDoctorsListUi(station.getId());
    }
}
