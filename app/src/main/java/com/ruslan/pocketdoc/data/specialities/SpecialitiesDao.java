package com.ruslan.pocketdoc.data.specialities;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Интерфейс, описывающий DAO сущности "Специальность".
 */
@Dao
public interface SpecialitiesDao {

    /**
     * Запрос вставки списка специальностей.
     * @param specialities Список специальностей.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSpecialities(List<Speciality> specialities);

    /**
     * Запрос подсчёта количества специальностей.
     * @return Количество специальностей.
     */
    @Query("SELECT COUNT(*) FROM specialities")
    int countAll();

    /**
     * Запрос получения списка всех специальностей.
     * @return Список специальностей.
     */
    @Query("SELECT id, name FROM specialities ORDER BY name")
    Flowable<List<Speciality>> getAllSpecialities();

    /**
     * Запрос очистки таблицы.
     */
    @Query("DELETE FROM specialities")
    void clearTable();
}
