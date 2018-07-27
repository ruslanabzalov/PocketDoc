package com.ruslan.pocketdoc.clinic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.ruslan.pocketdoc.R;

import java.util.Objects;

public class ClinicActivity extends AppCompatActivity {

    private static final String CLINIC_ID_EXTRA = "clinic_id";

    public static Intent newIntent(Context context, int clinicId) {
        Intent intent = new Intent(context, ClinicActivity.class);
        intent.putExtra(CLINIC_ID_EXTRA, clinicId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_info);
        Toolbar toolbar = findViewById(R.id.activity_clinic_info_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.findFragmentById(R.id.activity_clinic_info_fragment) == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.activity_clinic_info_fragment, ClinicFragment.newInstance(getIntent().getIntExtra(CLINIC_ID_EXTRA, 0)))
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
