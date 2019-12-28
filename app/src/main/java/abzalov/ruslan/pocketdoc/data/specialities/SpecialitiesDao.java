package abzalov.ruslan.pocketdoc.data.specialities;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface SpecialitiesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSpecialities(List<Speciality> specialities);

    @Query("SELECT COUNT(*) FROM specialities")
    int countAll();

    @Query("SELECT id, name FROM specialities ORDER BY name")
    Flowable<List<Speciality>> getAllSpecialities();

    @Query("DELETE FROM specialities")
    void clearTable();
}
