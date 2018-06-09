package com.ruslan.pocketdoc.searching.specialities;

import com.ruslan.pocketdoc.data.Speciality;
import com.ruslan.pocketdoc.BaseContract;

import java.util.List;

class SpecialitiesPresenter implements BaseContract.BasePresenter {

    private SpecialitiesContract.View mSpecialitiesView;
    private SpecialitiesContract.Interactor mSpecialitiesInteractor;

    SpecialitiesPresenter(SpecialitiesContract.View view,
                          SpecialitiesContract.Interactor interactor) {
        mSpecialitiesView = view;
        mSpecialitiesInteractor = interactor;
    }

    @Override
    public void start() {
        if (mSpecialitiesView != null) {
            mSpecialitiesView.showProgressBar();
            mSpecialitiesInteractor
                    .loadSpecialities(new SpecialitiesContract.Interactor.OnLoadFinishedListener() {
                        @Override
                        public void onSuccess(List<Speciality> specialityList) {
                            mSpecialitiesView.showSpecialities(specialityList);
                            mSpecialitiesView.hideProgressBar();
                        }

                        @Override
                        public void onFailure(Throwable throwable) {
                            mSpecialitiesView.showLoadErrorMessage(throwable);
                            mSpecialitiesView.hideProgressBar();
                        }
                    });
        }
    }

    @Override
    public void stop() {
        mSpecialitiesView = null;
    }
}
