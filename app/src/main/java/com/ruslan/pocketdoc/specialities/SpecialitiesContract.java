package com.ruslan.pocketdoc.specialities;

import com.ruslan.pocketdoc.BaseContract;
import com.ruslan.pocketdoc.data.specialities.Speciality;

import java.util.List;

/**
 * Интерфейс, описывающий контракт между SpecialitiesContract.View и SpecialitiesContract.Presenter.
 */
interface SpecialitiesContract {

    /**
     * Интерфейс, описывающий SpecialitiesContract.View.
     */
    interface View extends BaseContract.BaseView {

        /**
         * Метод отображения списка специальностей.
         * @param specialities Список специальностей.
         */
        void showSpecialities(List<Speciality> specialities);

        /**
         * Метод отображения фрагмента со списком станций.
         * @param specialityId Идентификатор выбранной специальности.
         */
        void showStationsUi(String specialityId);

        /**
         * Метод открытия активности, отображающей историю записей.
         */
        void showRecordsHistoryListUi();
    }

    /**
     * Интерфейс, описывающий SpecialitiesContract.Presenter.
     */
    interface Presenter extends BaseContract.BasePresenter<View> {

        /**
         * Метод загрузки списка специальностей.
         */
        void loadSpecialities();

        /**
         * Метод обновления списка специальностей.
         * @param isMenuRefreshing Флаг, указывающий на способ обновления.
         */
        void updateSpecialities(boolean isMenuRefreshing);

        /**
         * Метод выбора специальности из списка.
         * @param speciality Выбранная специальность.
         */
        void chooseSpeciality(Speciality speciality);
    }
}
