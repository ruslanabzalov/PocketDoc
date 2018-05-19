package com.pocketdoc.pocketdoc.doc;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.pocketdoc.pocketdoc.SingleFragmentActivity;
import com.pocketdoc.pocketdoc.doc.specs.SpecsFragment;
import com.pocketdoc.pocketdoc.doc.stations.StationsFragment;

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
