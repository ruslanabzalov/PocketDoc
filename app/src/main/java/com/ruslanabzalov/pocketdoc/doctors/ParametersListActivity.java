package com.ruslanabzalov.pocketdoc.doctors;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.ruslanabzalov.pocketdoc.SingleFragmentActivity;
import com.ruslanabzalov.pocketdoc.doctors.specialities.SpecialitiesFragment;

public class ParametersListActivity extends SingleFragmentActivity {

    private static String mTargetFragmentName;

    public static Intent newIntent(Context packageContext, String targetFragmentName) {
        mTargetFragmentName = targetFragmentName;
        return new Intent(packageContext, ParametersListActivity.class);
    }

    @Override
    protected Fragment createFragment() {
        return (mTargetFragmentName.equals("Specialities")
                ? new SpecialitiesFragment()
                : new StationsFragment());
    }
}
