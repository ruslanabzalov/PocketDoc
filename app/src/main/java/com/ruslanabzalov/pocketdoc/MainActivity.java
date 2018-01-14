package com.ruslanabzalov.pocketdoc;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.ruslanabzalov.pocketdoc.database.DatabaseHelper;
import com.ruslanabzalov.pocketdoc.disease.DiseasesListFragment;
import com.ruslanabzalov.pocketdoc.docs.DocsSearchFragment;
import com.ruslanabzalov.pocketdoc.map.MapFragment;

/**
 * Главная активность приложения, отвечающая за хостинг четырёх основных фрагментов.
 */
public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private SQLiteDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();
        mDatabase = new DatabaseHelper(mContext).getWritableDatabase();
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
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
                    newTransaction.replace(R.id.container, new MapFragment())
                            .commit();
                    return true;
            }
            return false;
        });
    }
}
