package com.ruslan.pocketdoc.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.ruslan.pocketdoc.data.clinics.Clinic;
import com.ruslan.pocketdoc.data.clinics.ClinicDao;
import com.ruslan.pocketdoc.data.specialities.Speciality;
import com.ruslan.pocketdoc.data.specialities.SpecialityDao;
import com.ruslan.pocketdoc.data.stations.Station;
import com.ruslan.pocketdoc.data.stations.StationDao;

@Database(entities = {Speciality.class, Station.class, Clinic.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract SpecialityDao specialityDao();

    public abstract StationDao stationDao();

    public abstract ClinicDao clinicsDao();
}
