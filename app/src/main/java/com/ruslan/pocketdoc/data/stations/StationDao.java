package com.ruslan.pocketdoc.data.stations;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface StationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertStations(List<Station> stations);

    @Query("SELECT COUNT(*) FROM station")
    int countAll();

    @Query("SELECT * FROM station")
    List<Station> getAllStations();

    @Query("DELETE FROM station")
    void clearTable();
}
