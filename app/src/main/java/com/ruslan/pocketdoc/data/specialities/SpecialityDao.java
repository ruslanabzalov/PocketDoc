package com.ruslan.pocketdoc.data.specialities;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface SpecialityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSpecialities(List<Speciality> specialities);

    @Query("SELECT * FROM speciality")
    List<Speciality> getAllSpecialities();
}
