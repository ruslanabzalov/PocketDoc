package com.ruslan.pocketdoc.clinics;

import com.ruslan.pocketdoc.App;
import com.ruslan.pocketdoc.data.Repository;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ClinicsPresenter implements ClinicsContract.Presenter {

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
                .doOnSuccess(integer -> {
                    if (mView != null) {
                        if (integer == 0) {
                            mView.scheduleClinicsJobService();
                        }
                    }
                })
                .doOnError(throwable -> {
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
                .doOnNext(clinics -> {
                    if (mView != null) {
                        if (clinics.size() != 0) {
                            mView.addMarkers(clinics);
                        }
                    }
                })
                .doOnError(throwable -> {
                    if (mView != null) {
                        mView.showErrorDialog(throwable);
                    }
                })
                .subscribe();
    }

    @Override
    public void getOnlyClinicsFromDb() {
        mDisposable = mRepository.getOnlyClinicsFromDb("no")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(clinics -> {
                    if (mView != null) {
                        mView.addMarkers(clinics);
                    }
                })
                .doOnError(throwable -> {
                    if (mView != null) {
                        mView.showErrorDialog(throwable);
                    }
                })
                .subscribe();
    }

    @Override
    public void getOnlyDiagnosticsFromDb() {
        mDisposable = mRepository.getOnlyDiagnosticsFromDb("yes")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(clinics -> {
                    if (mView != null) {
                        mView.addMarkers(clinics);
                    }
                })
                .doOnError(throwable -> {
                    if (mView != null) {
                        mView.showErrorDialog(throwable);
                    }
                })
                .subscribe();
    }
}
