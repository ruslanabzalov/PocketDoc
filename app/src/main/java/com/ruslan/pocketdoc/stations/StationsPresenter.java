package com.ruslan.pocketdoc.stations;

import com.ruslan.pocketdoc.App;
import com.ruslan.pocketdoc.data.Repository;
import com.ruslan.pocketdoc.data.stations.Station;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class StationsPresenter implements StationsContract.Presenter {

    private StationsContract.View mView;

    private Disposable mDisposable;

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
        mDisposable.dispose();
    }

    @Override
    public void loadStations() {
        mView.showProgressBar();
        mDisposable = mRepository.getStations(false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::showList,
                        this::showError
                );
    }

    @Override
    public void updateStations(boolean isMenuRefreshing) {
        if (isMenuRefreshing) {
            mView.showProgressBar();
            mDisposable = mRepository.getStations(true)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            stations -> showRefreshingList(stations, true),
                            throwable -> showRefreshingError(throwable, true)
                    );
        } else {
            mDisposable = mRepository.getStations(true)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            stations -> showRefreshingList(stations, false),
                            throwable -> showRefreshingError(throwable, false)
                    );
        }
    }

    @Override
    public void chooseStation(Station station) {
        mView.showDoctorsListUi(station.getId());
    }

    private void showList(List<Station> stations) {
        if (mView != null) {

            // Почему-то вызывается метод showStations(stations) после обновления списка.
            mView.showStations(stations);
            mView.hideProgressBar();
        }
    }

    private void showError(Throwable throwable) {
        if (mView != null) {
            mView.showErrorMessage(throwable);
            mView.hideProgressBar();
        }
    }

    private void showRefreshingList(List<Station> stations, boolean isMenuRefreshing) {
        if (mView != null) {
            mView.showStations(stations);
            if (isMenuRefreshing) {
                mView.hideProgressBar();
            } else {
                mView.hideRefreshing();
            }
        }
    }

    private void showRefreshingError(Throwable throwable, boolean isMenuRefreshing) {
        if (mView != null) {
            mView.showErrorMessage(throwable);
            if (isMenuRefreshing) {
                mView.hideProgressBar();
            } else {
                mView.hideRefreshing();
            }
        }
    }
}
