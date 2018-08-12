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
     * Запрос получения списка всех мед. учреждений.
     * @return Список всех мед. учреждений.
     */
    @Query("SELECT * FROM clinics")
    Flowable<List<Clinic>> getAllClinics();

    /**
     * Запрос получения списка только клиник.
     * @param isDiagnostic Параметр для фильтра списка клиник.
     * @return Список только клиник.
     */
    @Query("SELECT * FROM clinics WHERE is_diagnostic LIKE :isDiagnostic")
    Flowable<List<Clinic>> getOnlyClinics(String isDiagnostic);

    /**
     * Запрос получения списка только диагностических центров.
     * @param isDiagnostic Параметр для фильтра списка диагн. центров.
     * @return Список только диагностических центров.
     */
    @Query("SELECT * FROM clinics WHERE is_diagnostic LIKE :isDiagnostic")
    Flowable<List<Clinic>> getOnlyDiagnostics(String isDiagnostic);

    /**
     * Запрос получения конкретной клинике по идентификатору.
     * @param id Идентификатор клиник.
     * @return Подробная информация о конкретной клинике.
     */
    @Query("SELECT * FROM clinics WHERE id=:id")
    Single<Clinic> getClinicById(int id);

    /**
     * Запрос очистки таблицы.
     */
    @Query("DELETE FROM clinics")
    void clearTable();
}
