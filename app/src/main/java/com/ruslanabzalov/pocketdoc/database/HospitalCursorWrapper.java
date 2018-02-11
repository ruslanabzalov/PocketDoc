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
        String name = getString(getColumnIndex(HospitalsTable.Cols.NAME));
        String type = getString(getColumnIndex(HospitalsTable.Cols.TYPE));
        String description = getString(getColumnIndex(HospitalsTable.Cols.DESCRIPTION));
        String address = getString(getColumnIndex(HospitalsTable.Cols.ADDRESS));
        String phone = getString(getColumnIndex(HospitalsTable.Cols.PHONE));
        String longitude = getString(getColumnIndex(HospitalsTable.Cols.LONGITUDE));
        String latitude = getString(getColumnIndex(HospitalsTable.Cols.LATITUDE));
        Hospital hospital = new Hospital();
        hospital.setName(name);
        hospital.setType(type);
        hospital.setDescription(description);
        hospital.setAddress(address);
        hospital.setPhone(phone);
        hospital.setLongitude(longitude);
        hospital.setLatitude(latitude);
        return hospital;
    }
}
