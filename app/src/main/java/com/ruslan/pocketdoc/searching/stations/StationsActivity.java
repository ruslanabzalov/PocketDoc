package com.ruslan.pocketdoc.searching.stations;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.ruslan.pocketdoc.SingleFragmentActivity;

public class StationsActivity extends SingleFragmentActivity {

    private static final String EXTRA_SPECIALITY_ID = "speciality_id";

    public static Intent newIntent(Context context, String specialityId) {
        Intent intent = new Intent(context, StationsActivity.class);
        intent.putExtra(EXTRA_SPECIALITY_ID, specialityId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        String specialityId = getIntent().getStringExtra(EXTRA_SPECIALITY_ID);
        return StationsFragment.newInstance(specialityId);
    }
}
