package com.ruslan.pocketdoc.stations;

import android.util.Log;

import com.ruslan.pocketdoc.App;
import com.ruslan.pocketdoc.data.Repository;
import com.ruslan.pocketdoc.data.stations.Station;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class StationsPresenter implements StationsContract.Presenter {

    private static final String TAG = "StationsPresenter";

    private StationsContract.View mView;

    @Inject
    Repository mRepository;

    private Disposable mDisposable;

    StationsPresenter() {
        App.getComponent().inject(this);
    }

    @Override
    public void attachView(StationsContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }

    @Override
    public void loadStations() {
        dispose(mDisposable);
        mView.showProgressBar();
        mDisposable = mRepository.getStations(false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(subscription ->
                        Log.i(TAG, "getStations(false): onSubscribe()"))
                .doOnNext(stations -> {
                    Log.i(TAG, "getStations(false): onNext()");
                    Log.i(TAG, "Stations loaded: " + stations.size());
                    showList(stations);
                })
                .doOnError(throwable -> {
                    Log.i(TAG, "getStations(false): onError()");
                    Log.i(TAG, "Error message: " + throwable.getMessage());
                    showError(throwable);
                })
                .doOnComplete(() -> Log.i(TAG, "getStations(false): onComplete"))
                .subscribe();
    }

    @Override
    public void updateStations(boolean isMenuRefreshing) {
        dispose(mDisposable);
        if (isMenuRefreshing) {
            mView.showProgressBar();
            forceUpdateStations(true);
        } else {
            forceUpdateStations(false);
        }
    }

    @Override
    public void chooseStation(Station station) {
        mView.showCalendarUi(station.getId());
    }

    private void dispose(Disposable disposable) {
        if (disposable != null) {
            disposable.dispose();
        }
    }

    private void forceUpdateStations(boolean isMenuRefreshing) {
        mDisposable = mRepository.getStations(true)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(subscription ->
                        Log.i(TAG, "getStations(true): onSubscribe()"))
                .doOnNext(stations -> {
                    Log.i(TAG, "getStations(true): onNext()");
                    Log.i(TAG, "Stations loaded: " + stations.size());
                    showUpdatedList(stations, isMenuRefreshing);
                })
                .doOnError(throwable -> {
                    Log.i(TAG, "getStations(true): onError()");
                    Log.i(TAG, "Error message: " + throwable.getMessage());
                    showRefreshingError(throwable, isMenuRefreshing);
                })
                .doOnComplete(() -> Log.i(TAG, "getStations(true): onComplete"))
                .subscribe();
    }

    private void showList(List<Station> stations) {
        if (mView != null) {
            mView.showStations(stations);
            mView.hideProgressBar();
        }
    }

    private void showError(Throwable throwable) {
        if (mView != null) {
            mView.showErrorDialog(throwable);
            mView.hideProgressBar();
        }
    }

    private void showUpdatedList(List<Station> stations, boolean isMenuRefreshing) {
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
            mView.showErrorDialog(throwable);
            if (isMenuRefreshing) {
                mView.hideProgressBar();
            } else {
                mView.hideRefreshing();
            }
        }
    }
}
