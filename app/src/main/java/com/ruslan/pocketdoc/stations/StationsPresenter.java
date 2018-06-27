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
    public void detachView() {
        mView = null;
    }

    @Override
    public void loadStations() {
        mView.showProgressBar();
        mRepository.getStations(false, new DataSource.OnLoadFinishedListener<Station>() {
            @Override
            public void onSuccess(List<Station> stations) {
                if (mView != null) {
                    mView.showStations(stations);
                    mView.hideProgressBar();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                if (mView != null) {
                    mView.showErrorMessage(throwable);
                    mView.hideProgressBar();
                }
            }
        });
    }

    @Override
    public void updateStations(boolean isMenuRefreshing) {
        if (isMenuRefreshing) {
            mView.showProgressBar();
            mRepository.getStations(true, new DataSource.OnLoadFinishedListener<Station>() {
                @Override
                public void onSuccess(List<Station> stations) {
                    if (mView != null) {
                        mView.showStations(stations);
                        mView.hideProgressBar();
                    }
                }

                @Override
                public void onFailure(Throwable throwable) {
                    if (mView != null) {
                        mView.showErrorMessage(throwable);
                        mView.hideProgressBar();
                    }
                }
            });
        } else {
            mRepository.getStations(true, new DataSource.OnLoadFinishedListener<Station>() {
                @Override
                public void onSuccess(List<Station> stations) {
                    if (mView != null) {
                        mView.showStations(stations);
                        mView.hideRefreshing();
                    }
                }

                @Override
                public void onFailure(Throwable throwable) {
                    if (mView != null) {
                        mView.showErrorMessage(throwable);
                        mView.hideRefreshing();
                    }
                }
            });
        }
    }

    @Override
    public void chooseStation(Station station) {
        String stationId = station.getId();
        mView.showDoctorsListUi(stationId);
    }
}
