package com.ruslan.pocketdoc.doc.stations;

import com.ruslan.pocketdoc.data.Station;

import java.util.List;

interface StationsContract {

    interface MvpView {
        void showStations(List<Station> stationList);
        void showLoadErrorMessage(Throwable throwable);
    }

    interface MvpPresenter {
        void onDestroy();
        void getData();
    }

    interface MvpInteractor {

        interface OnLoadFinishedListener {
            void onSuccess(List<Station> stations);
            void onFailure(Throwable throwable);
        }

        void loadStations(OnLoadFinishedListener onLoadFinishedListener);
    }
}
