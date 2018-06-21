package com.ruslan.pocketdoc;

public interface BaseContract {

    interface BaseView {

        void showErrorMessage(Throwable throwable);

        void showProgressBar();

        void hideProgressBar();
    }

    interface BasePresenter<V> {

        void attachView(V view);

        void detach();
    }
}
