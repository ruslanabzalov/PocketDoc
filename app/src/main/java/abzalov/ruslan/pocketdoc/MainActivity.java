package abzalov.ruslan.pocketdoc;

import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import abzalov.ruslan.pocketdoc.clinics.ClinicsMapFragment;
import abzalov.ruslan.pocketdoc.specialities.SpecialitiesFragment;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private FragmentManager mFragmentManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.main_activity_toolbar);
        setSupportActionBar(toolbar);
        TabLayout tabLayout = findViewById(R.id.main_tab_layout);
        mFragmentManager = getSupportFragmentManager();
        if (mFragmentManager.findFragmentById(R.id.main_activity_fragment_container) == null) {
            SpecialitiesFragment specialitiesFragment = new SpecialitiesFragment();
            mFragmentManager.beginTransaction()
                    .add(R.id.main_activity_fragment_container, specialitiesFragment)
                    .commit();
        } else {
            checkUpButton();
            Fragment currentFragment =
                    mFragmentManager.findFragmentById(R.id.main_activity_fragment_container);
            if (currentFragment instanceof ClinicsMapFragment) {
                TabLayout.Tab currentTab = tabLayout.getTabAt(1);
                Objects.requireNonNull(currentTab).select();
            }
        }
        mFragmentManager.addOnBackStackChangedListener(this::checkUpButton);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        replaceFragment(new SpecialitiesFragment());
                        break;
                    case 1:
                        clearBackStack();
                        replaceFragment(new ClinicsMapFragment());
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Fragment fragment =
                        mFragmentManager.findFragmentById(R.id.main_activity_fragment_container);
                switch (tab.getPosition()) {
                    case 0:
                        if (!(fragment instanceof SpecialitiesFragment)) {
                            clearBackStack();
                        }
                        break;
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        mFragmentManager.popBackStack();
        return true;
    }

    private void checkUpButton() {
        Objects.requireNonNull(getSupportActionBar())
                .setDisplayHomeAsUpEnabled(mFragmentManager.getBackStackEntryCount() > 0);
    }

    private void replaceFragment(Fragment fragment) {
        mFragmentManager.beginTransaction()
                .replace(R.id.main_activity_fragment_container, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
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
