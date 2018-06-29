package com.ruslan.pocketdoc.clinics;

import com.ruslan.pocketdoc.App;
import com.ruslan.pocketdoc.data.Repository;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ClinicsPresenter implements ClinicsContract.Presenter {

    @Inject
    Repository mRepository;

    private ClinicsContract.View mView;

    public ClinicsPresenter() {
        App.getComponent().inject(this);
    }

    @Override
    public void attachView(ClinicsContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void loadClinics() {
        mRepository.getClinics()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(clinicList -> {
                    if (mView != null) {
                        mView.showClinics(clinicList.getClinics());
                    }
                });
    }

    @Override
    public void updateClinics() {
        mRepository.getClinics()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(clinicList -> mView.showClinics(clinicList.getClinics()));
    }
}
