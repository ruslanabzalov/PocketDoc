package com.ruslan.pocketdoc.data;

import com.ruslan.pocketdoc.data.clinics.Clinic;
import com.ruslan.pocketdoc.data.specialities.Speciality;
import com.ruslan.pocketdoc.data.stations.Station;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * Интерфейс, описывающий методы для работы с БД Room.
 */
interface LocalDataSourceContract {

    /**
     * Метод получения списка специальностей.
     * @return Список специальностей.
     */
    Flowable<List<Speciality>> getSpecialities();

    /**
     * Метод сохранения списка специальностей в БД.
     * @param specialities Список специальностей.
     */
    void saveSpecialities(List<Speciality> specialities);

    /**
     * Метод получения списка станций метро.
     * @return Список станций метро.
     */
    Flowable<List<Station>> getStations();

    /**
     * Метод сохранения списка станций метро в БД.
     * @param stations Список станций метро.
     */
    void saveStations(List<Station> stations);

    /**
     * Метод получения количества клиник.
     * @return Количество клиник.
     */
    Single<Integer> countClinics();

    /**
     * Метод получения списка всех мед. учреждений.
     * @return Список всех мед. учреждений.
     */
    Flowable<List<Clinic>> getAllClinics();

    /**
     * Метод получения списка только клиник.
     * @return Список клиник.
     */
    Flowable<List<Clinic>> getOnlyClinics(String isDiagnostic);

    /**
     * Метод получения списка только диагностических центров.
     * @return Список только диагностических центров.
     */
    Flowable<List<Clinic>> getOnlyDiagnostics(String isDiagnostic);

    /**
     * Метод получения информации о клинике по идентификатору.
     * @param id Идентификатор клиники.
     * @return Подробная информация о клинике.
     */
    Single<Clinic> getClinicById(int id);

    /**
     * Метод сохранения списка клиник в БД.
     * @param clinics Список клиник.
     */
    void saveClinics(List<Clinic> clinics);
}
