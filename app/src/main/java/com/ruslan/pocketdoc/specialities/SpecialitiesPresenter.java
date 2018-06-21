package com.ruslan.pocketdoc.specialities;

import com.ruslan.pocketdoc.App;
import com.ruslan.pocketdoc.data.DataSource;
import com.ruslan.pocketdoc.data.Repository;
import com.ruslan.pocketdoc.data.specialities.Speciality;

import java.util.List;

import javax.inject.Inject;

public class SpecialitiesPresenter implements SpecialitiesContract.Presenter {

    private SpecialitiesContract.View mView;

    @Inject
    Repository mRepository;

    public SpecialitiesPresenter() {
        App.getComponent().inject(this);
    }

    @Override
    public void attachView(SpecialitiesContract.View view) {
        mView = view;
    }

    @Override
    public void detach() {
        mView = null;
    }

    @Override
    public void loadSpecialities() {
        mView.showProgressBar();
        mRepository.getSpecialities(new DataSource.OnLoadFinishedListener<Speciality>() {
            @Override
            public void onSuccess(List<Speciality> specialities) {
                mView.showSpecialities(specialities);
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
    public void onSpecialityClick(Speciality speciality) {
        mView.showStationListUi(speciality.getId());
    }
}
