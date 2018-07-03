package com.ruslan.pocketdoc;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.ruslan.pocketdoc.clinics.ClinicsMapFragment;
import com.ruslan.pocketdoc.specialities.SpecialitiesFragment;

public class MainActivity extends AppCompatActivity {

    private FragmentManager mFragmentManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.main_activity_toolbar);
        setSupportActionBar(toolbar);

        mFragmentManager = getSupportFragmentManager();
        if (mFragmentManager.findFragmentById(R.id.main_activity_fragment_container) == null) {
            SpecialitiesFragment specialitiesFragment = new SpecialitiesFragment();
            mFragmentManager.beginTransaction()
                    .add(R.id.main_activity_fragment_container, specialitiesFragment)
                    .commit();
        }
        mFragmentManager.addOnBackStackChangedListener(this::enableUpButton);

        TabLayout tabLayout = findViewById(R.id.main_tab_layout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment = mFragmentManager.findFragmentById(R.id.main_activity_fragment_container);
                switch (tab.getPosition()) {
                    case 0:
                        if (!(fragment instanceof SpecialitiesFragment)) {
                            clearBackStack();
                            SpecialitiesFragment specialitiesFragment = new SpecialitiesFragment();
                            replaceFragment(specialitiesFragment);
                        }
                    case 1:
                        if (!(fragment instanceof ClinicsMapFragment)) {
                            clearBackStack();
                            ClinicsMapFragment clinicsMapFragment = new ClinicsMapFragment();
                            replaceFragment(clinicsMapFragment);
                        }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Fragment fragment = mFragmentManager.findFragmentById(R.id.main_activity_fragment_container);
                switch (tab.getPosition()) {
                    case 0:
                        if (!(fragment instanceof SpecialitiesFragment)) {
                            clearBackStack();
                            SpecialitiesFragment specialitiesFragment = new SpecialitiesFragment();
                            replaceFragment(specialitiesFragment);
                        }
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        mFragmentManager.popBackStack();
        return super.onSupportNavigateUp();
    }

    private void enableUpButton() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(mFragmentManager.getBackStackEntryCount() > 0);
    }

    private void replaceFragment(Fragment fragment) {
        mFragmentManager.beginTransaction()
                .replace(R.id.main_activity_fragment_container, fragment)
                .commit();
    }

    private void clearBackStack() {
        int backStackCount = mFragmentManager.getBackStackEntryCount();
        while (backStackCount > 0) {
            mFragmentManager.popBackStack();
            backStackCount--;
        }
    }
}
