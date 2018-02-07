package com.ruslanabzalov.pocketdoc.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.ruslanabzalov.pocketdoc.database.DatabaseSchema.MedicalRecordsTable;
import com.ruslanabzalov.pocketdoc.docs.Record;

public class RecordCursorWrapper extends CursorWrapper {
    public RecordCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Record getRecord() {
        Record record = new Record();
        record.setDocType(getString(getColumnIndex(MedicalRecordsTable.Cols.DOC_TYPE)));
        record.setUserName(getString(getColumnIndex(MedicalRecordsTable.Cols.USER_NAME)));
        record.setRecordDate(getString(getColumnIndex(MedicalRecordsTable.Cols.RECORD_DATE)));
        return record;
    }
}
