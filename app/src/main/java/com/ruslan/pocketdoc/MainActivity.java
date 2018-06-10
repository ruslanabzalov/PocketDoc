package com.ruslan.pocketdoc;

import android.support.v4.app.Fragment;

import com.ruslan.pocketdoc.searching.specialities.SpecialitiesFragment;

public class MainActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new SpecialitiesFragment();
    }
}
