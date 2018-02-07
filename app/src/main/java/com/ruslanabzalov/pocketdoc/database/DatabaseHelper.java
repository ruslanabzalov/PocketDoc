package com.ruslanabzalov.pocketdoc.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ruslanabzalov.pocketdoc.database.DatabaseSchema.MedicalRecordsTable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "database.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + MedicalRecordsTable.NAME +
                "(" + "_id integer primary key autoincrement, " +
                MedicalRecordsTable.Cols.DOC_TYPE + "," +
                MedicalRecordsTable.Cols.USER_NAME + "," +
                MedicalRecordsTable.Cols.RECORD_DATE +
                ")"
        );
    }

    /**
     * Метод, обновляющий базу данных.
     * @param sqLiteDatabase база данных SQLite.
     * @param oldVersion старая версия базы данных.
     * @param newVersion новая версия базы данных.
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {}
}
