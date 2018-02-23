package com.ruslanabzalov.pocketdoc.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.ruslanabzalov.pocketdoc.map.Hospital;

import static com.ruslanabzalov.pocketdoc.database.DatabaseSchema.*;

public class HospitalCursorWrapper extends CursorWrapper {

    public HospitalCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Hospital getHospital() {
        Hospital hospital = new Hospital();
        hospital.setName(getString(getColumnIndex(HospitalsTable.Cols.NAME)));
        hospital.setType(getString(getColumnIndex(HospitalsTable.Cols.TYPE)));
        hospital.setDescription(getString(getColumnIndex(HospitalsTable.Cols.DESCRIPTION)));
        hospital.setAddress(getString(getColumnIndex(HospitalsTable.Cols.ADDRESS)));
        hospital.setPhone(getString(getColumnIndex(HospitalsTable.Cols.PHONE)));
        hospital.setLongitude(getString(getColumnIndex(HospitalsTable.Cols.LONGITUDE)));
        hospital.setLatitude(getString(getColumnIndex(HospitalsTable.Cols.LATITUDE)));
        return hospital;
    }
}
