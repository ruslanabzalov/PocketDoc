package com.ruslanabzalov.pocketdoc.doctors;

import android.support.v4.app.Fragment;

import com.ruslanabzalov.pocketdoc.SingleFragmentActivity;

public class DoctorsListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new DoctorsListFragment();
    }
}
