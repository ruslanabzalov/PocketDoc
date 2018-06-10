package com.ruslan.pocketdoc.data.specialities;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface SpecialityDao {

    @Query("SELECT * FROM speciality")
    List<Speciality> getAll();
}
