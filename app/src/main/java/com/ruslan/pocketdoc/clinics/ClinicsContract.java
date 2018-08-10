package com.ruslan.pocketdoc.clinics;

import com.ruslan.pocketdoc.BaseContract;
import com.ruslan.pocketdoc.data.clinics.Clinic;

import java.util.List;

/**
 * Интерфейс, описывающий контракт между <code>ClinicsContract.View</code>
 * и <code>ClinicsContract.Presenter</code>.
 */
interface ClinicsContract {

    /**
     * Интерфейс, описывающий контракт <code>ClinicsContract.View</code>.
     */
    interface View extends BaseContract.BaseView {

        /**
         * Метод запуска и планирования службы <code>JobService</code>
         * для получения списка клиник.
         */
        void startClinicsJobService();

        /**
         * Метод добавления маркеров клиник на карту.
         * @param clinics Список клиник.
         */
        void addMarkers(List<Clinic> clinics);

        /**
         * Метод отображения маркеров в видимой локации.
         */
        void showClinicsInCurrentArea();

        /**
         * Метод открытия активности с подробной информацией о клинике.
         * @param clinicId Идентификатор клиники.
         */
        void showClinicInfoUi(int clinicId);
    }

    /**
     * Интерфейс, описывающий контракт <code>ClinicsContract.Presenter</code>.
     */
    interface Presenter extends BaseContract.BasePresenter<View> {

        /**
         * Метод проверки базы данных на наличие списка клиник.
         */
        void getClinicsCount();

        /**
         * Метод получения клиник из базы данных.
         */
        void getClinicsFromDb();
    }
}
