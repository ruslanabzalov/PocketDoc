package com.ruslanabzalov.pocketdoc;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.ruslanabzalov.pocketdoc.disease.DiseasesListFragment;
import com.ruslanabzalov.pocketdoc.docs.DocsSearchFragment;
import com.ruslanabzalov.pocketdoc.map.MapFragment;
import com.ruslanabzalov.pocketdoc.profile.ProfileFragment;

/**
 * Главная активность приложения, отвечающая за хостинг четырёх основных фрагментов.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        // Отображение фрагмента DocsSearchFragment при создании активности MainActivity.
        transaction.replace(R.id.container, new DocsSearchFragment()).commit();
        navigation.setOnNavigationItemSelectedListener(item -> {
            FragmentManager newFm = getSupportFragmentManager();
            FragmentTransaction newTransaction = newFm.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_docs:
                    newTransaction.replace(R.id.container, new DocsSearchFragment()).commit();
                    return true;
                case R.id.navigation_medication:
                    newTransaction.replace(R.id.container, new DiseasesListFragment()).commit();
                    return true;
                case R.id.navigation_map:
                    newTransaction.replace(R.id.container, new MapFragment()).commit();
                    return true;
                case R.id.navigation_profile:
                    newTransaction.replace(R.id.container, new ProfileFragment()).commit();
                    return true;
            }
            return false;
        });
    }
}
