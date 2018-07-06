package com.ruslan.pocketdoc.data.clinics;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface ClinicDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertClinics(List<Clinic> clinics);

    @Query("SELECT COUNT(*) FROM clinics")
    int countAll();

    @Query("SELECT * FROM clinics")
    Flowable<List<Clinic>> getAllClinics();

    @Query("SELECT * FROM clinics WHERE is_diagnostic LIKE :isDiagnostic")
    Flowable<List<Clinic>> getClinicsOrDiagnostics(String isDiagnostic);

    @Query("DELETE FROM clinics")
    void clearTable();
}
