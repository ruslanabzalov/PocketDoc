package com.ruslan.pocketdoc.records;

import android.support.v4.app.DialogFragment;

class NewRecordPresenter implements NewRecordContract.Presenter {

    private NewRecordContract.View mNewRecordView;

    NewRecordPresenter(NewRecordContract.View newRecordView) {
        mNewRecordView = newRecordView;
    }
}
