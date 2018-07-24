package com.ruslan.pocketdoc;

public interface BaseContract {

    interface BaseView {

        void showErrorMessage(Throwable throwable);

        void showProgressBar();

        void hideProgressBar();
    }

    interface BasePresenter<V extends BaseView> {

        void attachView(V view);

        void detachView();
    }
}
