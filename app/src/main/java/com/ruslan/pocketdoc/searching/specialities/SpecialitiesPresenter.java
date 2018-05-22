package com.ruslan.pocketdoc.searching.specialities;

import com.ruslan.pocketdoc.data.Speciality;
import com.ruslan.pocketdoc.searching.BaseInteractor;
import com.ruslan.pocketdoc.searching.BasePresenter;
import com.ruslan.pocketdoc.searching.BaseView;

import java.util.List;

public class SpecialitiesPresenter implements BasePresenter {

    private BaseView<Speciality> mMvpView;
    private BaseInteractor<Speciality> mMvpInteractor;

    SpecialitiesPresenter(BaseView<Speciality> mvpView, BaseInteractor<Speciality> mvpInteractor) {
        mMvpView = mvpView;
        mMvpInteractor = mvpInteractor;
    }

    @Override
    public void onResume() {
        mMvpInteractor.loadData(new BaseInteractor.OnLoadFinishedListener<Speciality>() {
            @Override
            public void onSuccess(List<Speciality> specialities) {
                if (mMvpView != null) {
                    mMvpView.showItems(specialities);
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                if (mMvpView != null) {
                    mMvpView.showErrorMessage(throwable);
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        mMvpView = null;
    }
}
