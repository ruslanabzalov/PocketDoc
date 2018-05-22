package com.ruslan.pocketdoc.searching.doctors.doctor;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.ruslan.pocketdoc.SingleFragmentActivity;

public class DoctorActivity extends SingleFragmentActivity {

    private static final String EXTRA_DOCTOR_ID = "doctor_id";

    /**
     * Method of intent creation.
     * @return DoctorsActivity intent.
     */
    public static Intent newIntent(Context context, int doctorId) {
        Intent intent = new Intent(context, DoctorActivity.class);
        intent.putExtra(EXTRA_DOCTOR_ID, doctorId);
        return intent;
    }

    @NonNull
    @Override
    protected Fragment createFragment() {
        int doctorId = getIntent().getIntExtra(EXTRA_DOCTOR_ID, 0);
        return DoctorFragment.newInstance(doctorId);
    }
}
