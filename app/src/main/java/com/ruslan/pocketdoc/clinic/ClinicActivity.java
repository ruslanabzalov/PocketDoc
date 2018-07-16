package com.ruslan.pocketdoc.clinic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.ruslan.pocketdoc.R;

public class ClinicActivity extends AppCompatActivity {

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, ClinicActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = findViewById(R.id.activity_clinic_info_toolbar);
        setSupportActionBar(toolbar);
        setContentView(R.layout.activity_clinic_info);
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.findFragmentById(R.id.activity_clinic_info_fragment) == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.activity_clinic_info_fragment, new ClinicFragment())
                    .commit();
        }
    }
}
