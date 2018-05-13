package com.ruslanabzalov.pocketdoc;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.ruslanabzalov.pocketdoc.doctors.controller.SearchParamsFragment;
import com.ruslanabzalov.pocketdoc.map.MapFragment;
import com.ruslanabzalov.pocketdoc.user.UserFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Отображение фрагмента SearchParamsFragment при запуске приложения
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main_container, new SearchParamsFragment())
                .commit();
        setTitle(getString(R.string.title_doctors));

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(item -> {
            Fragment currentFragment;
            switch (item.getItemId()) {
                case R.id.navigation_doctors:
                    currentFragment = fragmentManager.findFragmentById(R.id.main_container);
                    if (!(currentFragment instanceof SearchParamsFragment)) {
                        fragmentManager.beginTransaction()
                                .replace(R.id.main_container, new SearchParamsFragment())
                                .commit();
                        setTitle(getString(R.string.title_doctors));
                    }
                    return true;
                case R.id.navigation_map:
                    currentFragment = fragmentManager.findFragmentById(R.id.main_container);
                    if (!(currentFragment instanceof MapFragment)) {
                        fragmentManager.beginTransaction()
                                .replace(R.id.main_container, new MapFragment())
                                .commit();
                        setTitle(getString(R.string.title_map));
                    }
                    return true;
                case R.id.navigation_user:
                    currentFragment = fragmentManager.findFragmentById(R.id.main_container);
                    if (!(currentFragment instanceof UserFragment)) {
                        fragmentManager.beginTransaction()
                                .replace(R.id.main_container, new UserFragment())
                                .commit();
                        setTitle(getString(R.string.title_user));
                    }
                    return true;
            }
            return false;
        });
    }
}
