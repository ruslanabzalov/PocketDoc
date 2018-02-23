package com.ruslanabzalov.pocketdoc.treatment;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.ruslanabzalov.pocketdoc.SingleFragmentActivity;

import java.util.UUID;

public class MedicamentActivity extends SingleFragmentActivity {

    private static final String EXTRA_DRUG_ID = "com.ruslanabzalov.pocketdoc.disease.drug_id";

    public static Intent newIntent(Context packageContext, UUID drugId) {
        Intent intent = new Intent(packageContext, MedicamentActivity.class);
        intent.putExtra(EXTRA_DRUG_ID, drugId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        UUID drugId = (UUID) getIntent().getSerializableExtra(EXTRA_DRUG_ID);
        return MedicamentFragment.newInstance(drugId);
    }
}
