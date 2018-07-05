package com.ruslan.pocketdoc.data.stations;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface StationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertStations(List<Station> stations);

    @Query("SELECT COUNT(*) FROM station")
    int countAll();

    @Query("SELECT * FROM station")
    Flowable<List<Station>> getAllStations();

    @Query("DELETE FROM station")
    void clearTable();
}
