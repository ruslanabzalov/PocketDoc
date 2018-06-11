package com.ruslan.pocketdoc.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.ruslan.pocketdoc.data.specialities.Speciality;
import com.ruslan.pocketdoc.data.specialities.SpecialityDao;
import com.ruslan.pocketdoc.data.stations.Station;
import com.ruslan.pocketdoc.data.stations.StationDao;

@Database(entities = {Speciality.class, Station.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract SpecialityDao specialityDao();

    public abstract StationDao stationDao();
}
