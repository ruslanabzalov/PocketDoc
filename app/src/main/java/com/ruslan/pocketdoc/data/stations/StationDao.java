package com.ruslan.pocketdoc.data.stations;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface StationDao {

    @Query("SELECT * FROM station")
    List<Station> getAll();
}
