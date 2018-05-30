package com.ruslan.pocketdoc;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.ruslan.pocketdoc.searching.SearchingFragment;

public class MainActivity extends SingleFragmentActivity {

    @NonNull
    @Override
    protected Fragment createFragment() {
        return new SearchingFragment();
    }
}
