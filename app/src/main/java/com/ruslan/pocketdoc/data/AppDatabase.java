package com.ruslan.pocketdoc.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.ruslan.pocketdoc.data.specialities.Speciality;
import com.ruslan.pocketdoc.data.specialities.SpecialityDao;
import com.ruslan.pocketdoc.data.stations.Station;
import com.ruslan.pocketdoc.data.stations.StationDao;

@Database(entities = {Speciality.class, Station.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract SpecialityDao specialityDao();

    public abstract StationDao stationDao();

    private static AppDatabase INSTANCE = null;

//    public static AppDatabase getInstance(Context context) {
//        if (INSTANCE == null) {
//            synchronized(AppDatabase.class) {
//                INSTANCE = Room.databaseBuilder(context, AppDatabase.class, "pocketdoc-db").build();
//            }
//        }
//        return INSTANCE;
//    }
//
//    public static void destroyInstance() {
//        INSTANCE = null;
//    }
}
