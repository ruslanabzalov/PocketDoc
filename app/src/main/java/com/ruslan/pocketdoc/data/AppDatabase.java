package com.ruslan.pocketdoc.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.ruslan.pocketdoc.data.clinics.Clinic;
import com.ruslan.pocketdoc.data.clinics.ClinicsDao;
import com.ruslan.pocketdoc.data.specialities.SpecialitiesDao;
import com.ruslan.pocketdoc.data.specialities.Speciality;
import com.ruslan.pocketdoc.data.stations.Station;
import com.ruslan.pocketdoc.data.stations.StationDao;

/**
 * Абстрактный класс базы данных Room.
 */
@Database(entities =
        {Speciality.class, Station.class, Clinic.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    /**
     * Абстрактный метод доступа к DAO специальностей.
     * @return DAO специальностей.
     */
    public abstract SpecialitiesDao specialityDao();

    /**
     * Абстрактный метод доступа к DAO станций метро.
     * @return DAO станций метро.
     */
    public abstract StationDao stationDao();

    /**
     * Абстрактный метод доступа к DAO клиник.
     * @return DAO клиник.
     */
    public abstract ClinicsDao clinicsDao();
}
