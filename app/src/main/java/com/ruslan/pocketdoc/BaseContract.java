package com.ruslan.pocketdoc;

public interface BaseContract {

    interface BaseView {

        void showErrorDialog(Throwable throwable);

        void showProgressBar();

        void hideProgressBar();

        void hideRefreshing();
    }

    interface BasePresenter<V extends BaseView> {

        void attachView(V view);

        void detachView();
    }
}
