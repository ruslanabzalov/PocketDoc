package com.ruslan.pocketdoc.searching;

public interface BaseContract {

    interface BaseView {
        void showLoadErrorMessage(Throwable throwable);
    }

    interface BasePresenter {
        void onDestroy();
    }
}
