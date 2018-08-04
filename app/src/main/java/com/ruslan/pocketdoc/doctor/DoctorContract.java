package com.ruslan.pocketdoc.doctor;

import com.ruslan.pocketdoc.BaseContract;
import com.ruslan.pocketdoc.data.doctors.Doctor;

/**
 * Интерфейс, описывающий контракт между DoctorContract.View и DoctorContract.Presenter.
 */
interface DoctorContract {

    /**
     * Интерфейс, описывающий DoctorContract.View.
     */
    interface View extends BaseContract.BaseView {

        /**
         * Метод отображения подробной информации о враче.
         * @param doctor Конкретный врач.
         */
        void showDoctorInfo(Doctor doctor);

        /**
         * Метод отображения DialogFragment для оформления записи к врачу.
         */
        void showNewRecordUi();
    }

    /**
     * Интерфейс, описывающий DoctorContract.Presenter.
     */
    interface Presenter extends BaseContract.BasePresenter<View> {

        /**
         * Метод загрузки подробной информации о враче.
         * @param doctorId Идентификатор врача.
         */
        void loadDoctorInfo(int doctorId);

        /**
         * Метод обновления подробной информации о враче.
         * @param doctorId Идентификатор врача.
         * @param isMenuRefreshing Флаг, указывающий на способ обновления.
         */
        void updateDoctorInfo(int doctorId, boolean isMenuRefreshing);
    }
}
