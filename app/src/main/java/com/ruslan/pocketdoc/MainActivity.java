package com.ruslan.pocketdoc;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.ruslan.pocketdoc.map.MapFragment;
import com.ruslan.pocketdoc.profile.ProfileFragment;
import com.ruslan.pocketdoc.searching.SearchingFragment;

public class MainActivity extends AppCompatActivity {

    FragmentManager mFragmentManager;
    Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFragmentManager = getSupportFragmentManager();
        mFragment = mFragmentManager.findFragmentById(R.id.main_activity_fragment_container);
        if (mFragment == null) {
            mFragmentManager.beginTransaction()
                    .add(R.id.main_activity_fragment_container, new SearchingFragment())
                    .commit();
            setTitle(R.string.title_doctors);
        } else {
            restoreActivityTitle();
        }
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(this::fragmentSelection);
    }

    private void restoreActivityTitle() {
        if (mFragment instanceof SearchingFragment) {
            setTitle(R.string.title_doctors);
        } else if (mFragment instanceof MapFragment) {
            setTitle(R.string.title_map);
        } else {
            setTitle(R.string.title_profile);
        }
    }

    private boolean fragmentSelection(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.doctors_searching_navigation_item:
                mFragment = mFragmentManager.findFragmentById(R.id.main_activity_fragment_container);
                if (!(mFragment instanceof SearchingFragment)) {
                    mFragmentManager.beginTransaction()
                            .replace(R.id.main_activity_fragment_container, new SearchingFragment())
                            .commit();
                    setTitle(R.string.title_doctors);
                }
                return true;
            case R.id.map_navigation_item:
                mFragment = mFragmentManager.findFragmentById(R.id.main_activity_fragment_container);
                if (!(mFragment instanceof MapFragment)) {
                    mFragmentManager.beginTransaction()
                            .replace(R.id.main_activity_fragment_container, new MapFragment())
                            .commit();
                    setTitle(R.string.title_map);
                }
                return true;
            case R.id.profile_navigation_item:
                mFragment = mFragmentManager.findFragmentById(R.id.main_activity_fragment_container);
                if (!(mFragment instanceof ProfileFragment)) {
                    mFragmentManager.beginTransaction()
                            .replace(R.id.main_activity_fragment_container, new ProfileFragment())
                            .commit();
                    setTitle(R.string.title_profile);
                }
                return true;
            default:
                return false;
        }
    }
}
