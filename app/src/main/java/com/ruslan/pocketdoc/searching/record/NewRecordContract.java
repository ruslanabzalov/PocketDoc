package com.ruslan.pocketdoc.searching.record;

import android.support.v4.app.FragmentManager;

interface NewRecordContract {

    interface View {
        void showCalendar();
    }

    interface Presenter {
        void onDestroy();
        void onCalendarButtonClick(FragmentManager fragmentManager);
    }
}
