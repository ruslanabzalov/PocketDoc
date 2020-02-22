package abzalov.ruslan.pocketdoc.clinics;

import android.Manifest;
import android.app.Activity;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import abzalov.ruslan.pocketdoc.R;
import abzalov.ruslan.pocketdoc.clinic.ClinicActivity;
import abzalov.ruslan.pocketdoc.data.clinics.Clinic;
import abzalov.ruslan.pocketdoc.dialogs.LoadingErrorDialogFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Фрагмент, отображающий карту с клиниками.
 */
public final class ClinicsMapFragment extends Fragment implements ClinicsContract.View {

    private static final String TAG = "ClinicsMapFragment";
    private static final String TAG_LOADING_ERROR_DIALOG = "LoadingErrorDialogFragment";

    private static final int JOB_ID = 12;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final int LOADING_ERROR_DIALOG_FRAGMENT_REQUEST_CODE = 999;

    private static final String IS_DISPLAYED_KEY = "is_displayed";
    private static final String CAMERA_POSITION_KEY = "camera_position";
    private static final String ARE_MARKERS_IN_DB_KEY = "are_markers_in_db";
    private static final String SHOW_ONLY_CLINICS_KEY = "show_only_clinics";
    private static final String SHOW_ONLY_DIAGNOSTICS_KEY = "show_only_diagnostics";

    private static final LatLng MOSCOW = new LatLng(55.751244, 37.618423);
    private static final LatLngBounds MOSCOW_BOUNDS = new LatLngBounds(
            new LatLng(55.5484, 37.2881), new LatLng(55.9426, 37.8855)
    );
    private static final float DEFAULT_ZOOM = 10f;

    private Activity mMainActivity;

    private ClinicsContract.Presenter mPresenter;

    private FragmentManager mFragmentManager;
    private ActionBar mSupportActionBar;
    private GoogleMap mGoogleMap;
    private UiSettings mUiSettings;
    private Menu mMenu;

    private CameraUpdate mRestoredCameraPosition;
    private boolean mLocationPermissionGranted;
    private List<Marker> mMarkers = new ArrayList<>();
    private boolean mIsDisplayed;
    private boolean mAreMarkersInDb;
    private boolean mAreMarkersAdded;
    private boolean mShowOnlyClinics;
    private boolean mShowOnlyDiagnostics;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mMainActivity = Objects.requireNonNull(getActivity(), "Activity is null!");
        mSupportActionBar = ((AppCompatActivity) mMainActivity).getSupportActionBar();

        mFragmentManager = getChildFragmentManager();
        mPresenter = new ClinicsPresenter();
        mPresenter.attachView(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mIsDisplayed = savedInstanceState.getBoolean(IS_DISPLAYED_KEY, false);
            mRestoredCameraPosition = CameraUpdateFactory
                    .newCameraPosition(savedInstanceState.getParcelable(CAMERA_POSITION_KEY));
            mAreMarkersInDb =
                    savedInstanceState.getBoolean(ARE_MARKERS_IN_DB_KEY, false);
            mShowOnlyClinics =
                    savedInstanceState.getBoolean(SHOW_ONLY_CLINICS_KEY, false);
            mShowOnlyDiagnostics =
                    savedInstanceState.getBoolean(SHOW_ONLY_DIAGNOSTICS_KEY, false);
        }

        Objects.requireNonNull(mSupportActionBar, "ActionBar is null!")
                .setTitle(R.string.clinics_title);

        View view = inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment supportMapFragment =
                (SupportMapFragment) mFragmentManager.findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this::initMap);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!mAreMarkersAdded) {
            mPresenter.getClinicsCount();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(IS_DISPLAYED_KEY, mIsDisplayed);
        savedInstanceState.putParcelable(CAMERA_POSITION_KEY, mGoogleMap.getCameraPosition());
        savedInstanceState.putBoolean(ARE_MARKERS_IN_DB_KEY, mAreMarkersInDb);
        savedInstanceState.putBoolean(SHOW_ONLY_CLINICS_KEY, mShowOnlyClinics);
        savedInstanceState.putBoolean(SHOW_ONLY_DIAGNOSTICS_KEY, mShowOnlyDiagnostics);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
                break;
        }
        updateLocationUi();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        mMenu = menu;
        inflater.inflate(R.menu.fragment_map, mMenu);
        if (mShowOnlyClinics || mShowOnlyDiagnostics) {
            restoreMenuChecks();
        }
    }

    private void restoreMenuChecks() {
        if (mShowOnlyClinics) {
            mMenu.findItem(R.id.item_show_only_clinics).setChecked(true);
        }
        if (mShowOnlyDiagnostics) {
            mMenu.findItem(R.id.item_show_only_diagnostics).setChecked(true);
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        if (mAreMarkersInDb) {
            setOptionsMenuVisible(menu, true);
        } else {
            setOptionsMenuVisible(menu, false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.item_show_only_clinics:
                changeMenuChecks(menuItem, R.id.item_show_only_diagnostics);
                checkMenuItems();
                return true;
            case R.id.item_show_only_diagnostics:
                changeMenuChecks(menuItem, R.id.item_show_only_clinics);
                checkMenuItems();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    private void changeMenuChecks(@NonNull MenuItem menuItem, @IdRes int menuItemId) {
        if (menuItem.isChecked()) {
            menuItem.setChecked(false);
        } else {
            menuItem.setChecked(true);
            MenuItem otherMenuItem = mMenu.findItem(menuItemId);
            if (otherMenuItem.isChecked()) {
                otherMenuItem.setChecked(false);
            }
        }
    }

    private void checkMenuItems() {
        MenuItem clinicsMenuItem = mMenu.findItem(R.id.item_show_only_clinics);
        MenuItem diagnosticsMenuItem = mMenu.findItem(R.id.item_show_only_diagnostics);
        if (clinicsMenuItem.isChecked()) {
            mShowOnlyClinics = true;
            mShowOnlyDiagnostics = false;
            mGoogleMap.clear();
            mMarkers.clear();
            mPresenter.getOnlyClinicsFromDb();
        } else if (diagnosticsMenuItem.isChecked()) {
            mShowOnlyClinics = false;
            mShowOnlyDiagnostics = true;
            mGoogleMap.clear();
            mMarkers.clear();
            mPresenter.getOnlyDiagnosticsFromDb();
        } else {
            mShowOnlyClinics = false;
            mShowOnlyDiagnostics = false;
            mGoogleMap.clear();
            mMarkers.clear();
            mPresenter.getAllClinicsFromDb();
        }
    }

    @Override
    public void setOptionsMenuVisible(Menu menu, boolean isVisible) {
        menu.findItem(R.id.item_show_only_clinics).setVisible(isVisible);
        menu.findItem(R.id.item_show_only_diagnostics).setVisible(isVisible);
    }

    @Override
    public void scheduleClinicsJobService() {
        ComponentName componentName =
                new ComponentName(Objects.requireNonNull(getActivity()), ClinicsJobService.class);
        long oneDayInMillis = 86400000;
        JobInfo jobInfo;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            jobInfo = new JobInfo.Builder(JOB_ID, componentName)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                    .setPeriodic(oneDayInMillis)
                    .build();
        } else {
            jobInfo = new JobInfo.Builder(JOB_ID, componentName)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                    .setRequiresBatteryNotLow(true)
                    .setPeriodic(oneDayInMillis)
                    .build();
        }
        JobScheduler jobScheduler =
                (JobScheduler) getActivity().getSystemService(Context.JOB_SCHEDULER_SERVICE);
        Objects.requireNonNull(jobScheduler).cancelAll();
        Objects.requireNonNull(jobScheduler).schedule(jobInfo);
    }

    @Override
    public void addMarkers(List<Clinic> clinics) {
        if (!mAreMarkersInDb) {
            mAreMarkersInDb = true;
            Objects.requireNonNull(getActivity()).invalidateOptionsMenu();
        }
        Marker marker;
        if (mMarkers.size() == 0) {
            for (Clinic clinic : clinics) {
                marker = mGoogleMap.addMarker(new MarkerOptions()
                        .title(clinic.getShortName())
                        .position(new LatLng(Double.parseDouble(clinic.getLatitude()),
                                Double.parseDouble(clinic.getLongitude())))
                        .visible(false)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_marker))
                );
                marker.setTag(clinic.getId());
                mMarkers.add(marker);
            }
            onIdle();
        }
        mAreMarkersAdded = true;
    }

    @Override
    public void showClinicsInCurrentArea() {
        for (Marker marker : mMarkers) {
            if (mGoogleMap.getProjection().getVisibleRegion().latLngBounds
                    .contains(marker.getPosition())) {
                marker.setVisible(true);
            } else {
                if (marker.isVisible()) {
                    marker.setVisible(false);
                }
            }
        }
    }

    @Override
    public void showErrorDialog(Throwable throwable) {
        Log.d(TAG, throwable.getMessage(), throwable);
        if (mFragmentManager.findFragmentByTag(TAG_LOADING_ERROR_DIALOG) == null) {
            DialogFragment loadingErrorDialogFragment = new LoadingErrorDialogFragment();
            loadingErrorDialogFragment
                    .setTargetFragment(this, LOADING_ERROR_DIALOG_FRAGMENT_REQUEST_CODE);
            loadingErrorDialogFragment
                    .show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(),
                            TAG_LOADING_ERROR_DIALOG);
        }
    }

    @Override
    public void showClinicInfoUi(int clinicId) {
        startActivity(ClinicActivity.newIntent(getActivity(), clinicId));
    }

    private void initMap(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mUiSettings = mGoogleMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(true);
        mUiSettings.setMapToolbarEnabled(false);
        mGoogleMap.setMinZoomPreference(DEFAULT_ZOOM);
        mGoogleMap.setMaxZoomPreference(18f);
        CameraPosition moscowPosition = new CameraPosition.Builder()
                .target(MOSCOW)
                .zoom(DEFAULT_ZOOM)
                .build();
        mGoogleMap.moveCamera(
                (mRestoredCameraPosition != null)
                        ? mRestoredCameraPosition
                        : CameraUpdateFactory.newCameraPosition(moscowPosition)
        );
        mGoogleMap.setLatLngBoundsForCameraTarget(MOSCOW_BOUNDS);
        mGoogleMap.setOnInfoWindowClickListener(this::onInfoClick);
        mGoogleMap.setOnCameraIdleListener(this::onIdle);
        getLocationPermission();
    }

    private void onInfoClick(@NonNull Marker marker) {
        showClinicInfoUi(Objects.requireNonNull((Integer) marker.getTag()));
    }

    private void onIdle() {
        if (mMarkers.size() == 0) {
            if (mShowOnlyClinics) {
                mPresenter.getOnlyClinicsFromDb();
            } else if (mShowOnlyDiagnostics) {
                mPresenter.getOnlyDiagnosticsFromDb();
            } else {
                mPresenter.getAllClinicsFromDb();
            }
        }
        if (mGoogleMap.getCameraPosition().zoom >= 15f) {
            showClinicsInCurrentArea();
        } else {
            hideMarkers();
        }
    }

    private void hideMarkers() {
        for (Marker marker : mMarkers) {
            marker.setVisible(false);
        }
    }

    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
            updateLocationUi();
        } else {
            requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    private void updateLocationUi() {
        try {
            if (mLocationPermissionGranted) {
                mGoogleMap.setMyLocationEnabled(true);
                mUiSettings.setMyLocationButtonEnabled(true);
            } else {
                mGoogleMap.setMyLocationEnabled(false);
                mUiSettings.setMyLocationButtonEnabled(false);
            }
        } catch (SecurityException ex) {
            Log.e(TAG, "Location error: " + ex.getMessage(), ex);
        }
    }

    // Ненужные методы!

    @Override
    public void showProgressBar() {}

    @Override
    public void hideProgressBar() {}

    @Override
    public void hideRefreshing() {}
}
