package com.ruslan.pocketdoc.searching.stations;

import com.ruslan.pocketdoc.data.Station;

import java.util.List;

interface StationsContract {

    interface View {
        void showLoadErrorMessage(Throwable throwable);
        void showProgressBar();
        void hideProgressBar();
        void showStationList(List<Station> stationList);
    }

    interface Interactor {

        interface OnLoadFinishedListener {
            void onSuccess(List<Station> stationList);
            void onFailure(Throwable t);
        }

        void loadStations(OnLoadFinishedListener listener);
    }
}
