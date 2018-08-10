package com.ruslan.pocketdoc.stations;

import com.ruslan.pocketdoc.BaseContract;
import com.ruslan.pocketdoc.data.stations.Station;

import java.util.List;

/**
 * Интерфейс, описывающий контракт между <code>StationsContract.View</code>
 * и <code>StationsContract.Presenter</code>.
 */
interface StationsContract {

    /**
     * Интерфейс, описывающий контракт <code>StationsContract.View</code>.
     */
    interface View extends BaseContract.BaseView {

        /**
         * Метод отображения списка станций метро.
         * @param stations Список станций метро.
         */
        void showStations(List<Station> stations);

        /**
         * Метод отображения экземпляра <code>DialogFragment</code> для выбора даты записи.
         * @param stationId Идентификатор выбранной станции метро.
         */
        void showCalendarUi(String stationId);
    }

    /**
     * Интерфейс, описывающий контракт <code>StationsContract.Presenter</code>.
     */
    interface Presenter extends BaseContract.BasePresenter<View> {

        /**
         * Метод загрузки списка станций метро.
         */
        void loadStations();

        /**
         * Метод обновления списка станций метро.
         * @param isMenuRefreshing Значение типа <code>boolean</code>,
         *                         указывающее на способ обновления.
         */
        void updateStations(boolean isMenuRefreshing);

        /**
         * Метод выбора станции метро из списка.
         * @param station Выбранная станция метро.
         */
        void chooseStation(Station station);
    }
}
