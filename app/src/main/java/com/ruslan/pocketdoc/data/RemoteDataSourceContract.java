package com.ruslan.pocketdoc.data;

import com.ruslan.pocketdoc.data.clinics.Clinic;
import com.ruslan.pocketdoc.data.doctors.Doctor;
import com.ruslan.pocketdoc.data.specialities.Speciality;
import com.ruslan.pocketdoc.data.stations.Station;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * Интерфейс, описывающий методы для работы с API DocDoc.
 */
interface RemoteDataSourceContract {

    /**
     * Метод получения списка специальностей.
     * @return Список специальностей.
     */
    Flowable<List<Speciality>> getSpecialities();

    /**
     * Метод получения списка станций метро.
     * @return Список станций метро.
     */
    Flowable<List<Station>> getStations();

    /**
     * Метод получения списка врачей.
     * @param specialityId Идентификатор специальности.
     * @param stationId Идентификатор станции метро.
     * @return Список врачей.
     */
    Flowable<List<Doctor>> getDoctors(String specialityId, String stationId);

    /**
     * Метод получения подробной информации о враче.
     * @param doctorId Идентификатор врача.
     * @return Экземпляр врача.
     */
    Single<Doctor> getDoctor(int doctorId);

    /**
     * Метод получения списка клиник.
     * @param start Начальный индекс.
     * @param count Количество клиник.
     * @return Список клиник.
     */
    Flowable<List<Clinic>> getClinics(int start, int count);
}
