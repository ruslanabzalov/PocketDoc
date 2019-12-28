package abzalov.ruslan.pocketdoc.data.clinics;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface ClinicsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertClinics(List<Clinic> clinics);

    @Query("SELECT COUNT(*) FROM clinics")
    Single<Integer> countAll();

    @Query("SELECT * FROM clinics")
    Flowable<List<Clinic>> getAllClinics();

    @Query("SELECT * FROM clinics WHERE is_diagnostic LIKE :isDiagnostic")
    Flowable<List<Clinic>> getOnlyClinics(String isDiagnostic);

    @Query("SELECT * FROM clinics WHERE is_diagnostic LIKE :isDiagnostic")
    Flowable<List<Clinic>> getOnlyDiagnostics(String isDiagnostic);

    @Query("SELECT * FROM clinics WHERE id=:id")
    Single<Clinic> getClinicById(int id);

    @Query("DELETE FROM clinics")
    void clearTable();
}
