package com.ruslanabzalov.pocketdoc.doctors.controller;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.ruslanabzalov.pocketdoc.SingleFragmentActivity;

public class DoctorsActivity extends SingleFragmentActivity {

    private static final String EXTRA_SPEC_ID =
            "com.ruslanabzalov.pocketdoc.doctors.controllers.DoctorsActivity.spec_id";
    private static final String EXTRA_STATION_ID =
            "com.ruslanabzalov.pocketdoc.doctors.controllers.DoctorsActivity.station_id";

    /**
     * Метод создания объекта Intent с дополнения для запуска активности DoctorsActivity.
     * @param packageContext
     * @param specId
     * @param stationId
     * @return
     */
    public static Intent newIntent(Context packageContext, String specId, String stationId) {
        Intent intent = new Intent(packageContext, DoctorsFragment.class);
        intent.putExtra(EXTRA_SPEC_ID, specId);
        intent.putExtra(EXTRA_STATION_ID, stationId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        String specId = getIntent().getStringExtra(EXTRA_SPEC_ID);
        String stationId = getIntent().getStringExtra(EXTRA_STATION_ID);
        return DoctorsFragment.newInstance(specId, stationId);
    }
}
