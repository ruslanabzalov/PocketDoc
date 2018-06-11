package com.ruslan.pocketdoc.specialities;

import com.ruslan.pocketdoc.data.specialities.Speciality;

import java.util.List;

class SpecialitiesPresenter implements SpecialitiesContract.Presenter {

    private SpecialitiesContract.View mView;
    private SpecialitiesContract.Interactor mInteractor;

    SpecialitiesPresenter(SpecialitiesContract.View view, SpecialitiesContract.Interactor interactor) {
        mView = view;
        mInteractor = interactor;
    }

    @Override
    public void start() {
        if (mView != null) {
            mView.showProgressBar();
            mInteractor
                    .loadSpecialities(new SpecialitiesContract.Interactor.OnLoadFinishedListener() {
                        @Override
                        public void onSuccess(List<Speciality> specialityList) {
                            mView.showSpecialities(specialityList);
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
