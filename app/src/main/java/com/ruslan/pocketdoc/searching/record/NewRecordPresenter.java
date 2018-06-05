package com.ruslan.pocketdoc.searching.record;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

class NewRecordPresenter implements NewRecordContract.Presenter {

    private static final String CALENDAR_DIALOG_FRAGMENT = "session_dialog_fragment";

    private NewRecordContract.View mNewRecordView;

    NewRecordPresenter(NewRecordContract.View newRecordView) {
        mNewRecordView = newRecordView;
    }

    @Override
    public void onDestroy() {
        mNewRecordView = null;
    }

    @Override
    public void onCalendarButtonClick(FragmentManager fragmentManager) {
        openCalendarDialog(fragmentManager);
    }

    private void openCalendarDialog(FragmentManager fragmentManager) {
        DialogFragment calendarDialog = new SessionDateDialogFragment();
        calendarDialog.show(fragmentManager, CALENDAR_DIALOG_FRAGMENT);
    }
}
