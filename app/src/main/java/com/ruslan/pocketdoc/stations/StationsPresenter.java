package com.ruslan.pocketdoc.stations;

import com.ruslan.pocketdoc.App;
import com.ruslan.pocketdoc.data.Repository;
import com.ruslan.pocketdoc.data.stations.Station;
import com.ruslan.pocketdoc.data.stations.StationList;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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
        mRepository.getStations(false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<StationList>() {
                    @Override
                    public void onSubscribe(Disposable d) {}

                    @Override
                    public void onNext(StationList stationList) {
                        if (mView != null) {
                            mView.showStations(stationList.getStations());
                            mView.hideProgressBar();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mView != null) {
                            mView.showErrorMessage(e);
                            mView.hideProgressBar();
                        }
                    }

                    @Override
                    public void onComplete() {}
                });
    }

    @Override
    public void updateStations(boolean isMenuRefreshing) {
        if (isMenuRefreshing) {
            mView.showProgressBar();
            mRepository.getStations(true)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<StationList>() {
                        @Override
                        public void onSubscribe(Disposable d) {}

                        @Override
                        public void onNext(StationList stationList) {
                            if (mView != null) {
                                mView.showStations(stationList.getStations());
                                mView.hideProgressBar();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (mView != null) {
                                mView.showErrorMessage(e);
                                mView.hideProgressBar();
                            }
                        }

                        @Override
                        public void onComplete() {}
                    });
        } else {
            mRepository.getStations(true)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<StationList>() {
                        @Override
                        public void onSubscribe(Disposable d) {}

                        @Override
                        public void onNext(StationList stationList) {
                            if (mView != null) {
                                mView.showStations(stationList.getStations());
                                mView.hideRefreshing();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (mView != null) {
                                mView.showErrorMessage(e);
                                mView.hideRefreshing();
                            }
                        }

                        @Override
                        public void onComplete() {}
                    });
        }
    }

    @Override
    public void chooseStation(Station station) {
        String stationId = station.getId();
        mView.showDoctorsListUi(stationId);
    }
}
