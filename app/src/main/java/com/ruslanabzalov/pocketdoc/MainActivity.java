package com.ruslanabzalov.pocketdoc;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.ruslanabzalov.pocketdoc.doctors.DoctorsParametersFragment;
import com.ruslanabzalov.pocketdoc.map.MapFragment;
import com.ruslanabzalov.pocketdoc.user.UserInfoFragment;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Realm.init(this); // Инициализация Realm.
        BottomNavigationView navigation = findViewById(R.id.navigation);

        FragmentManager firstFragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = firstFragmentManager.beginTransaction();
        transaction.replace(R.id.container, new DoctorsParametersFragment()).commit();

        navigation.setOnNavigationItemSelectedListener(item -> {
            FragmentManager secondFragmentManager = getSupportFragmentManager();
            FragmentTransaction newTransaction = secondFragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_doctors:
                    newTransaction.replace(R.id.container, new DoctorsParametersFragment()).commit();
                    return true;
                case R.id.navigation_map:
                    newTransaction.replace(R.id.container, new MapFragment()).commit();
                    return true;
                case R.id.navigation_user:
                    newTransaction.replace(R.id.container, new UserInfoFragment()).commit();
            }
            return false;
        });
    }
}
