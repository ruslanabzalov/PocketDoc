package com.ruslan.pocketdoc.doc.specs;

import com.ruslan.pocketdoc.data.Spec;

import java.util.List;

public class SpecsMvpPresenter implements SpecsContract.MvpPresenter {

    private SpecsContract.MvpView mMvpView;
    private SpecsContract.MvpInteractor mMvpInteractor;

    SpecsMvpPresenter(SpecsContract.MvpView mvpView, SpecsContract.MvpInteractor mvpInteractor) {
        mMvpView = mvpView;
        mMvpInteractor = mvpInteractor;
    }

    @Override
    public void onDestroy() {
        mMvpView = null;
    }

    @Override
    public void getData() {
        mMvpInteractor.loadSpecs(new SpecsContract.MvpInteractor.OnLoadFinishedListener() {
            @Override
            public void onSuccess(List<Spec> specs) {
                if (mMvpView != null) {
                    mMvpView.showSpecs(specs);
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                if (mMvpView != null) {
                    mMvpView.showLoadErrorMessage(throwable);
                }
            }
        });
    }
}
