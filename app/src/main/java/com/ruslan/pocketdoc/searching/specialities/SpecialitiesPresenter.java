package com.ruslan.pocketdoc.searching.specialities;

import com.ruslan.pocketdoc.data.Speciality;

import java.util.List;

class SpecialitiesPresenter implements SpecialitiesContract.Presenter {

    private SpecialitiesContract.View mSpecialitiesView;
    private SpecialitiesContract.Interactor mSpecialitiesInteractor;

    SpecialitiesPresenter(SpecialitiesContract.View view,
                          SpecialitiesContract.Interactor interactor) {
        mSpecialitiesView = view;
        mSpecialitiesInteractor = interactor;
    }

    @Override
    public void onDestroy() {
        mSpecialitiesView = null;
    }

    @Override
    public void getSpecialities() {
        mSpecialitiesInteractor
                .loadSpecialities(new SpecialitiesContract.Interactor.OnLoadFinishedListener() {
            @Override
            public void onSuccess(List<Speciality> specialityList) {
                if (mSpecialitiesView != null) {
                    mSpecialitiesView.showSpecialities(specialityList);
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                if (mSpecialitiesView != null) {
                    mSpecialitiesView.showLoadErrorMessage(throwable);
                }
            }
        });
    }
}
