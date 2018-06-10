package com.ruslan.pocketdoc.searching.stations;

import com.ruslan.pocketdoc.BaseContract;
import com.ruslan.pocketdoc.data.stations.Station;

import java.util.List;

interface StationsContract {

    interface View {

        void showLoadErrorMessage(Throwable throwable);

        void showProgressBar();

        void hideProgressBar();

        void showStationList(List<Station> stationList);

        void navigateToDoctorsActivity(String stationId);
    }

    interface Presenter extends BaseContract.BasePresenter {

        void onStationClick(Station station);
    }

    interface Interactor {

        interface OnLoadFinishedListener {

            void onSuccess(List<Station> stationList);

            void onFailure(Throwable t);
        }

        void loadStations(OnLoadFinishedListener listener);
    }
}
