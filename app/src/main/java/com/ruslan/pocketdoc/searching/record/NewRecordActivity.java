package com.ruslan.pocketdoc.searching.record;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.ruslan.pocketdoc.SingleFragmentActivity;

public class NewRecordActivity extends SingleFragmentActivity {

    private static String DOCTOR_ID_EXTRA = "doctor_id_extra";

    public static Intent newIntent(Context context, int doctorId) {
        Intent intent = new Intent(context, NewRecordActivity.class);
        intent.putExtra(DOCTOR_ID_EXTRA, doctorId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        int doctorId = getIntent().getIntExtra(DOCTOR_ID_EXTRA, 0);
        return NewRecordFragment.newInstance(doctorId);
    }
}
