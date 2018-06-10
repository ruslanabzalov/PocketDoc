package com.ruslan.pocketdoc.searching.history;

import android.support.v4.app.Fragment;

import com.ruslan.pocketdoc.SingleFragmentActivity;

public class RecordsHistoryActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new RecordsHistoryFragment();
    }
}
