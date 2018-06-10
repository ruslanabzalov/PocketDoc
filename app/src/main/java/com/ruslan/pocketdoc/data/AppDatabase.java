package com.ruslan.pocketdoc.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.ruslan.pocketdoc.data.specialities.Speciality;
import com.ruslan.pocketdoc.data.specialities.SpecialityDao;

@Database(entities = {Speciality.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract SpecialityDao specialityDao();
}
