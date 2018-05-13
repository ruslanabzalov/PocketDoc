package com.ruslanabzalov.pocketdoc.doctors.controller;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.ruslanabzalov.pocketdoc.SingleFragmentActivity;

public class ParamsActivity extends SingleFragmentActivity {

    private static String mTargetFragmentName;

    public static Intent newIntent(Context packageContext, String targetFragmentName) {
        mTargetFragmentName = targetFragmentName;
        return new Intent(packageContext, ParamsActivity.class);
    }

    @Override
    protected Fragment createFragment() {
        return (mTargetFragmentName.equals("Specs") ? new SpecsFragment() : new StationsFragment());
    }
}
