package com.ruslanabzalov.pocketdoc;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.ruslanabzalov.pocketdoc.doctors.DoctorsParametersFragment;
import com.ruslanabzalov.pocketdoc.map.MapFragment;
import com.ruslanabzalov.pocketdoc.user.UserInfoFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = findViewById(R.id.navigation);

        // Отображение фрагмента DoctorsParametersFragment при запуске приложения.
        FragmentManager firstFragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = firstFragmentManager.beginTransaction();
        transaction.replace(R.id.fr_container, new DoctorsParametersFragment()).commit();
        setTitle(getString(R.string.title_doctors));

        navigation.setOnNavigationItemSelectedListener(item -> {
            Fragment currentFragment;
            FragmentManager secondFragmentManager = getSupportFragmentManager();
            String lol = "lol";
            FragmentTransaction newTransaction = secondFragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_doctors:
                    currentFragment = secondFragmentManager.findFragmentById(R.id.fr_container);
                    if (currentFragment instanceof MapFragment ||
                            currentFragment instanceof UserInfoFragment) {
                        newTransaction.replace(R.id.fr_container, new DoctorsParametersFragment()).commit();
                        setTitle(getString(R.string.title_doctors));
                    }
                    return true;
                case R.id.navigation_map:
                    currentFragment = secondFragmentManager.findFragmentById(R.id.fr_container);
                    if (currentFragment instanceof DoctorsParametersFragment ||
                            currentFragment instanceof UserInfoFragment) {
                        newTransaction.replace(R.id.fr_container, new MapFragment()).commit();
                        setTitle(getString(R.string.title_map));
                    }
                    return true;
                case R.id.navigation_user:
                    currentFragment = secondFragmentManager.findFragmentById(R.id.fr_container);
                    if (currentFragment instanceof DoctorsParametersFragment ||
                            currentFragment instanceof MapFragment) {
                        newTransaction.replace(R.id.fr_container, new UserInfoFragment()).commit();
                        setTitle(getString(R.string.title_user));
                    }
                    return true;
            }
            return false;
        });
    }
}
