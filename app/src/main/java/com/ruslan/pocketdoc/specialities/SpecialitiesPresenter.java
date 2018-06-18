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

    public SpecialitiesPresenter(SpecialitiesContract.View view) {
        App.getComponent().inject(this);
        mView = view;
    }

    @Override
    public void start() {
        if (mView != null) {
            mView.showProgressBar();
            mRepository.getSpecialities(new DataSource.OnLoadFinishedListener<Speciality>() {
                @Override
                public void onSuccess(List<Speciality> items) {
                    mView.showSpecialities(items);
                    mView.hideProgressBar();
                }

                @Override
                public void onFailure(Throwable throwable) {
                    mView.showLoadErrorMessage(throwable);
                    mView.hideProgressBar();
                }
            });
        }
    }

    @Override
    public void stop() {
        mView = null;
    }

    @Override
    public void onSpecialityClick(Speciality speciality) {
        mView.navigateToStationsList(speciality.getId());
    }
}
