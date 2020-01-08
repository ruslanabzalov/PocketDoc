package abzalov.ruslan.pocketdoc

import abzalov.ruslan.pocketdoc.clinics.ClinicsMapFragment
import abzalov.ruslan.pocketdoc.databinding.ActivityMainBinding
import abzalov.ruslan.pocketdoc.settings.SettingsFragment
import abzalov.ruslan.pocketdoc.specialities.SpecialitiesFragment
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

/**
 * Основная activity приложения.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val fragmentManager = supportFragmentManager
    private var currentFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.activityMainToolbar)

//        currentFragment = fragmentManager.findFragmentById(R.id.activity_main_fragment_container)
//        if (currentFragment == null) {
//            fragmentManager.beginTransaction()
//                .add(R.id.activity_main_fragment_container, SpecialitiesFragment())
//                .commit()
//        }

        binding.activityMainBottomNavigation.apply {
            setOnNavigationItemSelectedListener { wasCurrentFragmentChanged(it) }
            setOnNavigationItemReselectedListener { clearBackStack() }
        }
    }

    /**
     * Функция, обрабатывающая нажатие на пункт меню
     * [com.google.android.material.bottomnavigation.BottomNavigationView] и говорящая о том,
     * был ли заменён текущий активный fragment.
     * @param menuItem Выбранный пользователем пункт меню.
     * @return Булевый результат замены текущего активного fragment'а новым.
     */
    private fun wasCurrentFragmentChanged(menuItem: MenuItem): Boolean {
        currentFragment = fragmentManager.findFragmentById(binding.activityMainFragmentContainer.id)

        return false

//        return when (menuItem.itemId) {
//            R.id.doctors_activity_main_bottom_navigation_menu_item -> {
//                if (currentFragment !is SpecialitiesFragment) {
//                    replaceCurrentFragment(SpecialitiesFragment())
//                    true
//                } else false
//            }
//            R.id.clinics_activity_main_bottom_navigation_menu_item -> {
//                if (currentFragment !is ClinicsMapFragment) {
//                    replaceCurrentFragment(ClinicsMapFragment())
//                    true
//                } else false
//            }
//            R.id.settings_activity_main_bottom_navigation_menu_item -> {
//                if (currentFragment !is SettingsFragment) {
//                    replaceCurrentFragment(SettingsFragment())
//                    true
//                } else false
//            }
//            else -> false
//        }
    }

    /**
     * Функция замены текущего fragment'а новым.
     * @param newFragment Новый fragment.
     * @return Смотреть возвращающее значение метода [androidx.fragment.app.FragmentTransaction.commit].
     */
    private fun replaceCurrentFragment(newFragment: Fragment) =
        fragmentManager.beginTransaction()
            .replace(binding.activityMainFragmentContainer.id, newFragment)
            .commit()

    /**
     * Функция очистки back stack'а activity.
     */
    private fun clearBackStack() {
        var backStackEntryCount = fragmentManager.backStackEntryCount

        while (backStackEntryCount > 0) {
            fragmentManager.popBackStack()
            backStackEntryCount--
        }
    }
}