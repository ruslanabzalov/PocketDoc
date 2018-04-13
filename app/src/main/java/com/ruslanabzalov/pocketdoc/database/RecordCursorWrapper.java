package com.ruslanabzalov.pocketdoc.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.ruslanabzalov.pocketdoc.doctors.Record;

import static com.ruslanabzalov.pocketdoc.database.DatabaseSchema.MedicalRecordsTable.Cols;

public class RecordCursorWrapper extends CursorWrapper {
    public RecordCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Record getRecord() {
        Record record = new Record();
        record.setDocType(getString(getColumnIndex(Cols.DOC_TYPE)));
        record.setUserName(getString(getColumnIndex(Cols.USER_NAME)));
        record.setRecordDate(getString(getColumnIndex(Cols.RECORD_DATE)));
        return record;
    }
}
