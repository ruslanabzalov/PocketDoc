package abzalov.ruslan.pocketdoc.data.stations;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("WeakerAccess")
@Entity(tableName = "stations")
public class Station {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("Id")
    private String mId;

    @ColumnInfo(name = "name")
    @SerializedName("Name")
    private String mName;

    @ColumnInfo(name = "line_name")
    @SerializedName("LineName")
    private String mLineName;

    @ColumnInfo(name = "line_color")
    @SerializedName("LineColor")
    private String mLineColor;

    public Station(@NonNull String id) {
        mId = id;
    }

    @NonNull
    public String getId() {
        return mId;
    }

    public void setId(@NonNull String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getLineName() {
        return mLineName;
    }

    public void setLineName(String lineName) {
        mLineName = lineName;
    }

    public String getLineColor() {
        return mLineColor;
    }

    public void setLineColor(String lineColor) {
        mLineColor = lineColor;
    }
}
