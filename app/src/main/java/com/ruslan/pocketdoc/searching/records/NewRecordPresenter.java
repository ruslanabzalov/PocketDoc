package com.ruslan.pocketdoc.searching.records;

import android.support.v4.app.DialogFragment;

class NewRecordPresenter implements NewRecordContract.Presenter {

    private NewRecordContract.View mNewRecordView;

    NewRecordPresenter(NewRecordContract.View newRecordView) {
        mNewRecordView = newRecordView;
    }

    @Override
    public void onDestroy() {
        mNewRecordView = null;
    }

    @Override
    public void onCalendarButtonClick() {
        if (mNewRecordView != null) {
            DialogFragment calendarDialog = new SessionDateDialogFragment();
            mNewRecordView.showCalendar(calendarDialog);
        }
    }
}
