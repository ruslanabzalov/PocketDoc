package com.ruslan.pocketdoc.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.ruslan.pocketdoc.data.clinics.Clinic;
import com.ruslan.pocketdoc.data.clinics.ClinicsDao;
import com.ruslan.pocketdoc.data.specialities.SpecialitiesDao;
import com.ruslan.pocketdoc.data.specialities.Speciality;
import com.ruslan.pocketdoc.data.stations.Station;
import com.ruslan.pocketdoc.data.stations.StationDao;

@Database(entities =
        {Speciality.class, Station.class, Clinic.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract SpecialitiesDao specialityDao();

    public abstract StationDao stationDao();

    public abstract ClinicsDao clinicsDao();
}
