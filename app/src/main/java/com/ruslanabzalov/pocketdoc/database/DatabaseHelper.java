package com.ruslanabzalov.pocketdoc.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "database.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    /**
     * Метод для создания базы данных.
     * @param sqLiteDatabase база данных SQLite.
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + DatabaseSchema.MedicalRecordsTable.NAME +
                "(" + "_id integer primary key autoincrement, " +
                DatabaseSchema.MedicalRecordsTable.Cols.DOC_TYPE + "," +
                DatabaseSchema.MedicalRecordsTable.Cols.USER_NAME + "," +
                DatabaseSchema.MedicalRecordsTable.Cols.RECORD_DATE +
                ")"
        );
        sqLiteDatabase.execSQL("create table " + DatabaseSchema.HospitalsTable.NAME +
                "(" + "_id integer primary key autoincrement, " +
                DatabaseSchema.HospitalsTable.Cols.NAME + "," +
                DatabaseSchema.HospitalsTable.Cols.TYPE + "," +
                DatabaseSchema.HospitalsTable.Cols.DESCRIPTION + "," +
                DatabaseSchema.HospitalsTable.Cols.ADDRESS + "," +
                DatabaseSchema.HospitalsTable.Cols.PHONE + "," +
                DatabaseSchema.HospitalsTable.Cols.LONGITUDE + "," +
                DatabaseSchema.HospitalsTable.Cols.LATITUDE +
                ")"
        );
    }

    /**
     * Метод для обновления базы данных.
     * @param sqLiteDatabase база данных SQLite.
     * @param oldVersion старая версия базы данных.
     * @param newVersion новая версия базы данных.
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {}
}
