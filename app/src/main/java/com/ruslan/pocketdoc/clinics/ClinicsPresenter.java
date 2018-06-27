package com.ruslan.pocketdoc.clinics;

import com.ruslan.pocketdoc.App;
import com.ruslan.pocketdoc.data.DataSource;
import com.ruslan.pocketdoc.data.Repository;
import com.ruslan.pocketdoc.data.clinics.Clinic;

import java.util.List;

import javax.inject.Inject;

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
        mRepository.getClinics(false, new DataSource.OnLoadFinishedListener<Clinic>() {
            @Override
            public void onSuccess(List<Clinic> clinics) {
                if (mView != null) {
                    mView.showClinics(clinics);
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                if (mView != null) {
                    mView.showErrorMessage(throwable);
                }
            }
        });
    }

    @Override
    public void updateClinics() {
        mRepository.getClinics(true, new DataSource.OnLoadFinishedListener<Clinic>() {
            @Override
            public void onSuccess(List<Clinic> clinics) {
                if (mView != null) {
                    mView.showClinics(clinics);
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                if (mView != null) {
                    mView.showErrorMessage(throwable);
                }
            }
        });
    }
}
