package com.ruslan.pocketdoc;

import android.support.v4.app.Fragment;

import com.ruslan.pocketdoc.searching.SearchingFragment;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends SingleFragmentActivity {

    @NotNull
    @Override
    protected Fragment createFragment() {
        return new SearchingFragment();
    }
}
