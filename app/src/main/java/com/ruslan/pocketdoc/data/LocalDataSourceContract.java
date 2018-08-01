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
     * Метод получения списка клиник.
     * @return Список клиник.
     */
    Flowable<List<Clinic>> getClinics();

    /**
     * Метод сохранения списка клиник в БД.
     * @param clinics Список клиник.
     */
    void saveClinics(List<Clinic> clinics);
}
