package abzalov.ruslan.pocketdoc;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import abzalov.ruslan.pocketdoc.clinics.ClinicsMapFragment;
import abzalov.ruslan.pocketdoc.databinding.ActivityMainBinding;
import abzalov.ruslan.pocketdoc.drugs.DrugsFragment;
import abzalov.ruslan.pocketdoc.emergency.EmergencyFragment;
import abzalov.ruslan.pocketdoc.history.RecordsHistoryActivity;
import abzalov.ruslan.pocketdoc.specialities.SpecialitiesFragment;

/**
 * Основная активность приложения.
 */
public final class MainActivity extends AppCompatActivity {

    private FragmentManager mFragmentManager = getSupportFragmentManager();
    private Fragment mCurrentFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        abzalov.ruslan.pocketdoc.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.activityMainToolbar);

        mCurrentFragment = mFragmentManager.findFragmentById(R.id.activity_main_fragment_container);
        if (mCurrentFragment == null) {
            mFragmentManager.beginTransaction()
                    .add(R.id.activity_main_fragment_container, new SpecialitiesFragment())
                    .commit();
        }

        BottomNavigationView bottomNavigationView = binding.activityMainBottomNavigation;
        bottomNavigationView.setOnNavigationItemSelectedListener(this::wasCurrentFragmentChanged);
        bottomNavigationView.setOnNavigationItemReselectedListener(menuItem -> clearBackStack());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.records_history_menu_item) {
            startActivity(new Intent(this, RecordsHistoryActivity.class));
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Метод, возвращающий логический результат в зависимотсти от того,
     * был ли заменён текущий фрагмент после нажатия пользователем на пункт меню.
     * @param menuItem Выбранный пользователем пункт меню.
     * @return Логический результат изменения текущего фрагмента.
     */
    private boolean wasCurrentFragmentChanged(@NonNull MenuItem menuItem) {
        mCurrentFragment = mFragmentManager.findFragmentById(R.id.activity_main_fragment_container);

        switch (menuItem.getItemId()) {
            case R.id.doctors_activity_main_bottom_navigation_menu_item: {
                if (!(mCurrentFragment instanceof SpecialitiesFragment)) {
                    changeCurrentFragment(new SpecialitiesFragment());
                    return true;
                } else return false;
            }
            case R.id.drugs_activity_main_bottom_navigation_menu_item: {
                if (!(mCurrentFragment instanceof DrugsFragment)) {
                    changeCurrentFragment(new DrugsFragment());
                    return true;
                } else return false;
            }
            case R.id.clinics_activity_main_bottom_navigation_menu_item: {
                if (!(mCurrentFragment instanceof ClinicsMapFragment)) {
                    changeCurrentFragment(new ClinicsMapFragment());
                    return true;
                } else return false;
            }
            case R.id.emergency_activity_main_bottom_navigation_menu_item: {
                if (!(mCurrentFragment instanceof EmergencyFragment)) {
                    changeCurrentFragment(new EmergencyFragment());
                    return true;
                } else return false;
            }
            default: return false;
        }
    }

    /**
     * Метод замены текущего фрагмента новым.
     * @param newFragment Новый фрагмент.
     */
    private void changeCurrentFragment(@NonNull Fragment newFragment) {
        mFragmentManager.beginTransaction()
                .replace(R.id.activity_main_fragment_container, newFragment)
                .commit();
    }

    /**
     * Метод очистки back stack'а активности.
     */
    private void clearBackStack() {
        int backStackEntryCount = mFragmentManager.getBackStackEntryCount();

        while (backStackEntryCount > 0) {
            mFragmentManager.popBackStack();
            backStackEntryCount--;
        }
    }
}
