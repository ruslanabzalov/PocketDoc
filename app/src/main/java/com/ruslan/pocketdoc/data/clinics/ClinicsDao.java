package com.ruslan.pocketdoc.data.clinics;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * Интерфейс, описывающий DAO сущности "Клиника".
 */
@Dao
public interface ClinicsDao {

    /**
     * Запрос вставки списка клиники.
     * @param clinics Список клиник.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertClinics(List<Clinic> clinics);

    /**
     * Запрос подсчёта количества клиник.
     * @return Количество клиник.
     */
    @Query("SELECT COUNT(*) FROM clinics")
    Single<Integer> countAll();

    /**
     * Запрос получения списка всех клиник.
     * @return Список клиник.
     */
    @Query("SELECT * FROM clinics")
    Flowable<List<Clinic>> getAllClinics();

    /**
     * Запрос очистки таблицы.
     */
    @Query("DELETE FROM clinics")
    void clearTable();
}
