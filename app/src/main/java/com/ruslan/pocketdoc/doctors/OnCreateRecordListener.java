package com.ruslan.pocketdoc.doctors;

@FunctionalInterface
public interface OnCreateRecordListener {

    int SIMPLE_RECORD_BUTTON = 0;
    int SCHEDULE_BUTTON = 1;

    void onCreateRecord(int createRecordButtonType);
}
