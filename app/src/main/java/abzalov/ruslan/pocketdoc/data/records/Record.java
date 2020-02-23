package abzalov.ruslan.pocketdoc.data.records;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "records")
public class Record {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    private String mId;

    @ColumnInfo(name = "doc_type")
    private String mDocType;

    @ColumnInfo(name = "doc_full_name")
    private String mDocFullName;

    @ColumnInfo(name = "user_name")
    private String mUserName;

    @ColumnInfo(name = "record_date")
    private String mRecordDate;

    public Record(@NonNull String id) {
        mId = id;
    }

    public String getId() {
        return mId;
    }

    public String getDocType() {
        return mDocType;
    }

    public void setDocType(String docType) {
        mDocType = docType;
    }

    public String getDocFullName() {
        return mDocFullName;
    }

    public void setDocFullName(String docFullName) {
        mDocFullName = docFullName;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getRecordDate() {
        return mRecordDate;
    }

    public void setRecordDate(String recordDate) {
        mRecordDate = recordDate;
    }
}
