package com.ruslan.pocketdoc.searching.doctors.doctor;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.ruslan.pocketdoc.SingleFragmentActivity;
import com.ruslan.pocketdoc.data.Doctor;

public class DoctorActivity extends SingleFragmentActivity {

    private static final String EXTRA_DOCTOR = "doctor";

    public static Intent newIntent(Context context, Doctor doctor) {
        final Intent intent = new Intent(context, DoctorActivity.class);
        intent.putExtra(EXTRA_DOCTOR, doctor);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        final int doctorId = getIntent().getIntExtra(EXTRA_DOCTOR, 0);
        return DoctorFragment.newInstance(doctorId);
    }
}
