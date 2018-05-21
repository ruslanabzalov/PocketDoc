package com.ruslan.pocketdoc.searching;

import java.util.List;

public interface BaseInteractor<T> {

    interface OnLoadFinishedListener<T> {

        void onSuccess(List<T> t);

        void onFailure(Throwable throwable);
    }

    void loadData(OnLoadFinishedListener<T> onLoadFinishedListener);
}
