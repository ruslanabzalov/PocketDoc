package com.ruslanabzalov.pocketdoc.doctors;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.ruslanabzalov.pocketdoc.SingleFragmentActivity;

public class RecordsHistoryActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, DocsListActivity.class);
    }

    @Override
    protected Fragment createFragment() {
        return new RecordsHistoryFragment();
    }
}
