package com.pocketdoc.pocketdoc.doc.stations;

import com.pocketdoc.pocketdoc.data.Station;

import java.util.List;

interface StationsContract {

    interface Presenter {
        void onDestroy();
        void getData();
    }

    interface MainView {
        void setDataToRecyclerView(List<Station> stations);
        void onFailureFetchedMessage(Throwable throwable);
    }

    interface Fetcher {

        interface OnStationsFetchListener {
            void onSuccess(List<Station> stations);
            void onFailure(Throwable throwable);
        }

        void getStationsData(OnStationsFetchListener onStationsFetchListener);
    }
}
