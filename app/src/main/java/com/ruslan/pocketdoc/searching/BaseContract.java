package com.ruslan.pocketdoc.searching;

public interface BaseContract {

    interface BaseView {
        void showLoadErrorMessage(Throwable throwable);
        void showProgressBar();
        void hideProgressBar();
    }

    interface BasePresenter {
        void onResume();
        void onDestroy();
    }
}
