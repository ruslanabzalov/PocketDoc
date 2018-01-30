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
        record.setDocName(getString(getColumnIndex(MedicalRecordsTable.Cols.DOC_NAME)));
        record.setDocType(getString(getColumnIndex(MedicalRecordsTable.Cols.DOC_TYPE)));
        record.setDocAddress(getString(getColumnIndex(MedicalRecordsTable.Cols.DOC_ADDRESS)));
        record.setDocDescription(getString(getColumnIndex(MedicalRecordsTable.Cols.DOC_DESCRIPTION)));
        record.setUserName(getString(getColumnIndex(MedicalRecordsTable.Cols.USER_NAME)));
        record.setUserPhone(getString(getColumnIndex(MedicalRecordsTable.Cols.USER_PHONE)));
        record.setRecordDate(getString(getColumnIndex(MedicalRecordsTable.Cols.RECORD_DATE)));
        return record;
    }
}
