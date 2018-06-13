package com.ruslan.pocketdoc.specialities;

import com.ruslan.pocketdoc.data.DataSource;
import com.ruslan.pocketdoc.data.Repository;
import com.ruslan.pocketdoc.data.specialities.Speciality;

import java.util.List;

class SpecialitiesPresenter implements SpecialitiesContract.Presenter {

    private SpecialitiesContract.View mView;
    private Repository mRepository;
//    private SpecialitiesContract.Interactor mInteractor;

    SpecialitiesPresenter(SpecialitiesContract.View view) {
        mView = view;
        mRepository = Repository.getInstance();
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
