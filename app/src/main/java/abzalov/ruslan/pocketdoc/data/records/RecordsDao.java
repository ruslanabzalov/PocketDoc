package abzalov.ruslan.pocketdoc.data.records;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface RecordsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRecord(Record record);

    @Query("SELECT COUNT(*) FROM records")
    int countAllRecords();

    @Query("SELECT * FROM records ORDER BY record_date")
    Flowable<List<Record>> getAllRecords();

    @Query("DELETE FROM records")
    void clearTable();
}
