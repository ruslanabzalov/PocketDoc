package com.ruslan.pocketdoc;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.ruslan.pocketdoc.clinics.ClinicsMapFragment;
import com.ruslan.pocketdoc.data.AppDatabaseImpl;
import com.ruslan.pocketdoc.history.RecordsHistoryActivity;
import com.ruslan.pocketdoc.specialities.SpecialitiesFragment;

public class MainActivity extends AppCompatActivity {

    private FragmentManager mFragmentManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.main_activity_title);

        AppDatabaseImpl.initDatabase(getApplicationContext());

        mFragmentManager = getSupportFragmentManager();
        if (mFragmentManager.findFragmentById(R.id.main_activity_fragment_container) == null) {
            mFragmentManager.beginTransaction()
                    .add(R.id.main_activity_fragment_container, new SpecialitiesFragment())
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
                            replaceCurrentFragment(new SpecialitiesFragment());
                        }
                    case 1:
                        if (!(fragment instanceof ClinicsMapFragment)) {
                            clearBackStack();
                            replaceCurrentFragment(new ClinicsMapFragment());
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
                            replaceCurrentFragment(new SpecialitiesFragment());
                        }
                }
            }
        });
    }

    /**
     * Метод создания меню в Action Bar.
     * @param menu Меню.
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_records_history:
                Intent intent = new Intent(this, RecordsHistoryActivity.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Метод, обрабатывающий нажатие на кнопку Up.
     * @return
     */
    @Override
    public boolean onSupportNavigateUp() {
        mFragmentManager.popBackStack();
        return true;
    }

    /**
     * Метод отображения кнопки Up.
     */
    private void enableUpButton() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(mFragmentManager.getBackStackEntryCount() > 0);
        }
    }

    /**
     * Метод замены текущего фрагмента на новый.
     * @param newFragment Новый фрагмент.
     */
    private void replaceCurrentFragment(Fragment newFragment) {
        mFragmentManager.beginTransaction()
                .replace(R.id.main_activity_fragment_container, newFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    /**
     * Метод очистки стека транзакций.
     */
    private void clearBackStack() {
        int backStackCount = mFragmentManager.getBackStackEntryCount();
        while (backStackCount > 0) {
            mFragmentManager.popBackStack();
            backStackCount--;
        }
    }
}
