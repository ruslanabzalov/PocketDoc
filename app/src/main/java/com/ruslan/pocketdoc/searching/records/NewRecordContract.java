package com.ruslan.pocketdoc.searching.records;

import android.support.v4.app.DialogFragment;

interface NewRecordContract {

    interface View {
        void showCalendar(DialogFragment dialogFragment);
    }

    interface Presenter {
        void onDestroy();
        void onCalendarButtonClick();
    }
}
