package com.ruslan.pocketdoc.specialities;

import com.ruslan.pocketdoc.BaseContract;
import com.ruslan.pocketdoc.data.specialities.Speciality;

import java.util.List;

/**
 * Интерфейс, описывающий контракт между <code>SpecialitiesContract.View</code>
 * и <code>SpecialitiesContract.Presenter</code>.
 */
public interface SpecialitiesContract {

    /**
     * Интерфейс, описывающий контракт <code>SpecialitiesContract.View</code>.
     */
    interface View extends BaseContract.BaseView {

        /**
         * Метод отображения списка специальностей.
         * @param specialities Список специальностей.
         */
        void showSpecialities(List<Speciality> specialities);

        /**
         * Метод отображения fragment со списком станций.
         * @param specialityId Идентификатор выбранной специальности.
         */
        void showStationsUi(String specialityId);
    }

    /**
     * Интерфейс, описывающий контракт <code>SpecialitiesContract.Presenter</code>.
     */
    interface Presenter extends BaseContract.BasePresenter<View> {

        /**
         * Метод загрузки списка специальностей.
         */
        void loadSpecialities();

        /**
         * Метод обновления списка специальностей.
         * @param isMenuRefreshing Значение типа <code>boolean</code>,
         *                         указывающее на способ обновления.
         */
        void updateSpecialities(boolean isMenuRefreshing);

        /**
         * Метод выбора специальности из списка.
         * @param speciality Выбранная специальность.
         */
        void chooseSpeciality(Speciality speciality);
    }
}
