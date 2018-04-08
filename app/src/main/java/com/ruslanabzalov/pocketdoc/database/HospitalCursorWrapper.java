//package com.ruslanabzalov.pocketdoc.database;
//
//import android.database.Cursor;
//import android.database.CursorWrapper;
//
//import com.ruslanabzalov.pocketdoc.map.Clinic;
//
//import static com.ruslanabzalov.pocketdoc.database.DatabaseSchema.*;
//
//public class HospitalCursorWrapper extends CursorWrapper {
//
//    public HospitalCursorWrapper(Cursor cursor) {
//        super(cursor);
//    }
//
//    public Clinic getHospital() {
//        Clinic clinic = new Clinic();
//        clinic.setName(getString(getColumnIndex(HospitalsTable.Cols.NAME)));
////        clinic.setType(getString(getColumnIndex(HospitalsTable.Cols.TYPE)));
//        clinic.setDescription(getString(getColumnIndex(HospitalsTable.Cols.DESCRIPTION)));
////        clinic.setAddress(getString(getColumnIndex(HospitalsTable.Cols.ADDRESS)));
////        clinic.setPhone(getString(getColumnIndex(HospitalsTable.Cols.PHONE)));
////        clinic.setLongitude(getString(getColumnIndex(HospitalsTable.Cols.LONGITUDE)));
////        clinic.setLatitude(getString(getColumnIndex(HospitalsTable.Cols.LATITUDE)));
//        return clinic;
//    }
//}
