package com.ruslan.pocketdoc;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.ruslan.pocketdoc.clinics.ClinicsMapFragment;
import com.ruslan.pocketdoc.specialities.SpecialitiesFragment;


/**
 * Класс, описывающий основную активность приложения.
 */
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
        mFragmentManager.addOnBackStackChangedListener(this::changeUpButtonState);

        TabLayout tabLayout = findViewById(R.id.main_tab_layout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment = mFragmentManager.findFragmentById(R.id.main_activity_fragment_container);
                switch (tab.getPosition()) {
                    case 0:
                        if (!(fragment instanceof SpecialitiesFragment)) {
                            clearBackStack();
                            replaceFragment(new SpecialitiesFragment());
                        }
                        break;
                    case 1:
                        if (!(fragment instanceof ClinicsMapFragment)) {
                            clearBackStack();
                            replaceFragment(new ClinicsMapFragment());
                        }
                        break;
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
                            replaceFragment(new SpecialitiesFragment());
                        }
                        break;
                    case 1:
                        if (!(fragment instanceof ClinicsMapFragment)) {
                            clearBackStack();
                            replaceFragment(new ClinicsMapFragment());
                        }
                        break;
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        mFragmentManager.popBackStack();
        return super.onSupportNavigateUp();
    }

    /**
     * Метод включения или отключения кнопки Up.
     */
    private void changeUpButtonState() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(mFragmentManager.getBackStackEntryCount() > 0);
    }


    /**
     * Метод замены текущего фрагмента новым.
     * @param fragment Новый фрагмент.
     */
    private void replaceFragment(Fragment fragment) {
        mFragmentManager.beginTransaction()
                .replace(R.id.main_activity_fragment_container, fragment)
                .commit();
    }

    /**
     * Метод очистки обратного стека.
     */
    private void clearBackStack() {
        int backStackCount = mFragmentManager.getBackStackEntryCount();
        while (backStackCount > 0) {
            mFragmentManager.popBackStack();
            backStackCount--;
        }
    }
}
