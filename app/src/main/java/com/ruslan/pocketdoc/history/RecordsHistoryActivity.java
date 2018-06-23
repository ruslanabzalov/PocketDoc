package com.ruslan.pocketdoc.history;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.ruslan.pocketdoc.SingleFragmentActivity;

public class RecordsHistoryActivity extends SingleFragmentActivity {

    @NonNull
    @Override
    protected Fragment createFragment() {
        return new RecordsHistoryFragment();
    }
}
