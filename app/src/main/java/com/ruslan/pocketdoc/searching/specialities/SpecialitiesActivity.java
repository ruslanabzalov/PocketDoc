package com.ruslan.pocketdoc.searching.specialities;

import android.support.v4.app.Fragment;

import com.ruslan.pocketdoc.SingleFragmentActivity;

public class SpecialitiesActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new SpecialitiesFragment();
    }
}
