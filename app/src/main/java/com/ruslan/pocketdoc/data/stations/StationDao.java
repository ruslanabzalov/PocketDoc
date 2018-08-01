package com.ruslan.pocketdoc.data.stations;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Интерфейс, описывающий DAO сущности "Станция метро".
 */
@Dao
public interface StationDao {

    /**
     * Запрос вставки списка станций.
     * @param stations Список станций.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertStations(List<Station> stations);

    /**
     * Запрос подсчёта количества станций.
     * @return Количество станций.
     */
    @Query("SELECT COUNT(*) FROM stations")
    int countAll();

    /**
     * Запрос получения списка всех станций.
     * @return Список станций.
     */
    @Query("SELECT * FROM stations ORDER BY name")
    Flowable<List<Station>> getAllStations();

    /**
     * Запрос очистки таблицы.
     */
    @Query("DELETE FROM stations")
    void clearTable();
}
