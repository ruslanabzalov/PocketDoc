package com.ruslanabzalov.pocketdoc.map;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.ruslanabzalov.pocketdoc.SingleFragmentActivity;

public class ClinicActivity extends SingleFragmentActivity {

    private static final String EXTRA_CLINIC = "com.ruslanabzalov.pocketdoc.map.clinic";

    public static Intent newIntent(Context packageContext, Hospital clinic) {
        Intent intent = new Intent(packageContext, ClinicActivity.class);
        intent.putExtra(EXTRA_CLINIC, clinic);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        Hospital clinic = (Hospital) getIntent().getSerializableExtra(EXTRA_CLINIC);
        return new ClinicFragment().newInstance(clinic);
    }
}
