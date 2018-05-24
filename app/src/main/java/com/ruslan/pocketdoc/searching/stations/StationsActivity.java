package com.ruslan.pocketdoc.searching.stations;

import android.support.v4.app.Fragment;

import com.ruslan.pocketdoc.SingleFragmentActivity;

public class StationsActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new StationsFragment();
    }
}
