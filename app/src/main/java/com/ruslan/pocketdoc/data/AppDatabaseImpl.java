package com.ruslan.pocketdoc.data;

import android.arch.persistence.room.Room;
import android.content.Context;

public class AppDatabaseImpl {

    private static AppDatabase sDatabase = null;

    private AppDatabaseImpl() {}

    public static AppDatabase getInstance() {
        return sDatabase;
    }

    public static void initDatabase(Context context) {
        if (sDatabase == null) {
            sDatabase = Room.databaseBuilder(context, AppDatabase.class, "pocketdoc_db").build();
        }
    }
}
