package com.ruslan.pocketdoc.doctors;

import com.ruslan.pocketdoc.BaseContract;
import com.ruslan.pocketdoc.data.doctors.Doctor;

import java.util.List;

/**
 * Интерфейс, описывающий контракт между DoctorsContract.View и DoctorsContract.Presenter.
 */
interface DoctorsContract {

    /**
     * Интерфейс, описывающий DoctorsContract.View.
     */
    interface View extends BaseContract.BaseView {

        /**
         * Метод отображения списка врачей.
         * @param doctorList Список врачей.
         */
        void showDoctors(List<Doctor> doctorList);

        /**
         * Метод отображения фрагмента, содержащего подробную информацию о враче.
         * @param doctorId Идентификатор выбранного врача.
         */
        void showDoctorInfoUi(int doctorId);
    }

    /**
     * Интерфейс, описывающий DoctorsContract.Presenter.
     */
    interface Presenter extends BaseContract.BasePresenter<View> {

        /**
         * Метод загрузки списка врачей.
         * @param specialityId Идентификатор специальности.
         * @param stationId Идентификатор станции метро.
         */
        void loadDoctors(String specialityId, String stationId);

        /**
         * Метод установки установки расписания врачей на выбранную дату.
         * @param doctors Список врачей.
         * @param preferredDate Выбранная дата записи.
         */
        void setDoctorsSchedules(List<Doctor> doctors, String preferredDate);

        /**
         * Метод обновления списка врачей.
         * @param specialityId Идентификатор специальности.
         * @param stationId Идентификатор станции метро.
         * @param isMenuRefreshing Флаг, указывающий на способ обновления.
         */
        void updateDoctors(String specialityId, String stationId, boolean isMenuRefreshing);

        /**
         * Метод выбора врача из списка.
         * @param doctor Выбранный врач.
         */
        void chooseDoctor(Doctor doctor);
    }
}
