package abzalov.ruslan.pocketdoc.data.stations;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface StationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertStations(List<Station> stations);

    @Query("SELECT COUNT(*) FROM stations")
    int countAll();

    @Query("SELECT * FROM stations ORDER BY name")
    Flowable<List<Station>> getAllStations();

    @Query("DELETE FROM stations")
    void clearTable();
}
