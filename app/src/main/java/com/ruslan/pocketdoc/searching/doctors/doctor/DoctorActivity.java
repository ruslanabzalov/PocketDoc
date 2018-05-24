package com.ruslan.pocketdoc.searching.doctors.doctor;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.ruslan.pocketdoc.SingleFragmentActivity;

public class DoctorActivity extends SingleFragmentActivity {

    private static final String EXTRA_DOCTOR_ID = "doctor_id";

    public static Intent newIntent(Context context, int doctorId) {
        final Intent intent = new Intent(context, DoctorActivity.class);
        intent.putExtra(EXTRA_DOCTOR_ID, doctorId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        final int doctorId = getIntent().getIntExtra(EXTRA_DOCTOR_ID, 0);
        return DoctorFragment.newInstance(doctorId);
    }
}
