package com.ruslan.pocketdoc.data.specialities;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface SpecialityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSpecialities(List<Speciality> specialities);

    @Query("SELECT COUNT(*) FROM specialities")
    int countAll();

    @Query("SELECT id, name FROM specialities ORDER BY name")
    Flowable<List<Speciality>> getAllSpecialities();

    @Query("DELETE FROM specialities")
    void clearTable();
}
