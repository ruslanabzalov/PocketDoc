package abzalov.ruslan.pocketdoc.doctors;

import abzalov.ruslan.pocketdoc.data.doctors.Doctor;

@FunctionalInterface
public interface OnCreateRecordListener {

    int SIMPLE_RECORD_BUTTON = 0;
    int SCHEDULE_BUTTON = 1;

    void onCreateRecord(int createRecordButtonType, Doctor doctor);
}
