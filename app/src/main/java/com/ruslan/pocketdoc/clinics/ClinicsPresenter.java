package com.ruslan.pocketdoc.clinics;

import android.util.Log;

import com.ruslan.pocketdoc.App;
import com.ruslan.pocketdoc.data.Repository;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ClinicsPresenter implements ClinicsContract.Presenter {

    private static final String TAG = ClinicsPresenter.class.getSimpleName();

    private Disposable mDisposable;

    @Inject
    Repository mRepository;

    private ClinicsContract.View mView;

    ClinicsPresenter() {
        App.getComponent().inject(this);
    }

    @Override
    public void attachView(ClinicsContract.View view) {
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
    public void getClinicsCount() {
        mDisposable = mRepository.getClinicsCount()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> Log.i(TAG, "getClinicsCount(): onSubscribe()"))
                .doOnSuccess(integer -> {
                    Log.i(TAG, "getClinicsCount(): onSuccess()");
                    if (mView != null) {
                        if (integer == 0) {
                            mView.scheduleClinicsJobService();
                        }
                    }
                })
                .doOnError(throwable -> {
                    Log.i(TAG, "getClinicsCount(): onError()");
                    if (mView != null) {
                        if (throwable != null) {
                            mView.showErrorDialog(throwable);
                        }
                    }
                })
                .subscribe();
    }

    @Override
    public void getAllClinicsFromDb() {
        mDisposable = mRepository.getAllClinicsFromDb()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(subscription ->
                        Log.i(TAG, "getAllClinicsFromDb(): onSubscribe()"))
                .doOnNext(clinics -> {
                    Log.i(TAG, "getAllClinicsFromDb(): onNext()");
                    if (mView != null) {
                        if (clinics.size() != 0) {
                            mView.addMarkers(clinics);
                        }
                    }
                })
                .doOnError(throwable -> {
                    Log.i(TAG, "getAllClinicsFromDb(): onError()");
                    if (mView != null) {
                        mView.showErrorDialog(throwable);
                    }
                })
                .doOnComplete(() -> Log.i(TAG, "getAllClinicsFromDb(): onComplete()"))
                .subscribe();
    }

    @Override
    public void getOnlyClinicsFromDb() {
        mDisposable = mRepository.getOnlyClinicsFromDb("no")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(subscription ->
                        Log.i(TAG, "getOnlyClinicsFromDb(): onSubscribe()"))
                .doOnNext(clinics -> {
                    Log.i(TAG, "getOnlyClinicsFromDb(): onNext()");
                    if (mView != null) {
                        mView.addMarkers(clinics);
                    }
                })
                .doOnError(throwable -> {
                    Log.i(TAG, "getOnlyClinicsFromDb(): onError");
                    if (mView != null) {
                        mView.showErrorDialog(throwable);
                    }
                })
                .doOnComplete(() -> Log.i(TAG, "getOnlyClinicsFromDb(): onComplete()"))
                .subscribe();
    }

    @Override
    public void getOnlyDiagnosticsFromDb() {
        mDisposable = mRepository.getOnlyDiagnosticsFromDb("yes")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(subscription ->
                        Log.i(TAG, "getOnlyDiagnosticsFromDb(): onSubscribe()"))
                .doOnNext(clinics -> {
                    Log.i(TAG, "getOnlyDiagnosticsFromDb(): onNext");
                    if (mView != null) {
                        mView.addMarkers(clinics);
                    }
                })
                .doOnError(throwable -> {
                    Log.i(TAG, "getOnlyDiagnosticsFromDb(): onError()");
                    if (mView != null) {
                        mView.showErrorDialog(throwable);
                    }
                })
                .doOnComplete(() -> Log.i(TAG, "getOnlyDiagnosticsFromDb(): onComplete()"))
                .subscribe();
    }
}
