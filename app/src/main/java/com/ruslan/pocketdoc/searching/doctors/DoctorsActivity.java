package com.ruslan.pocketdoc.searching.doctors;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.ruslan.pocketdoc.SingleFragmentActivity;

public class DoctorsActivity extends SingleFragmentActivity {

    private static final String EXTRA_SPEC_ID = "spec_id";
    private static final String EXTRA_STATION_ID = "station_id";

    public static Intent newIntent(Context context, String specId, String stationId) {
        Intent intent = new Intent(context, DoctorsActivity.class);
        intent.putExtra(EXTRA_SPEC_ID, specId);
        intent.putExtra(EXTRA_STATION_ID, stationId);
        return intent;
    }

    @NonNull
    @Override
    protected Fragment createFragment() {
        String specId = getIntent().getStringExtra(EXTRA_SPEC_ID);
        String stationId = getIntent().getStringExtra(EXTRA_STATION_ID);
        return DoctorsFragment.newInstance(specId, stationId);
    }
}
