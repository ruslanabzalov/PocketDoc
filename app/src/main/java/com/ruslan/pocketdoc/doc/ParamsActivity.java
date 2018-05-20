package com.ruslan.pocketdoc.doc;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.ruslan.pocketdoc.SingleFragmentActivity;
import com.ruslan.pocketdoc.doc.specs.SpecsFragment;
import com.ruslan.pocketdoc.doc.stations.StationsFragment;

public class ParamsActivity extends SingleFragmentActivity {

    private static String mTargetFragmentName;

    public static Intent newIntent(Context packageContext, String targetFragmentName) {
        mTargetFragmentName = targetFragmentName;
        return new Intent(packageContext, ParamsActivity.class);
    }

    @NonNull
    @Override
    protected Fragment createFragment() {
        return (mTargetFragmentName.equals("Specs") ? new SpecsFragment() : new StationsFragment());
    }
}
