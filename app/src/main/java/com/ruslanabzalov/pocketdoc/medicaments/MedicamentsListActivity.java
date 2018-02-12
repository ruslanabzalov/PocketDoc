package com.ruslanabzalov.pocketdoc.medicaments;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.ruslanabzalov.pocketdoc.SingleFragmentActivity;

public class MedicamentsListActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, MedicamentsListActivity.class);
    }

    @Override
    protected Fragment createFragment() {
        return new MedicamentsListFragment();
    }
}
