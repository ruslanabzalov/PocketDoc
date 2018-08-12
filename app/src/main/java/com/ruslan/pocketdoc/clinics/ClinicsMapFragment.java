package com.ruslan.pocketdoc.clinics;

import android.Manifest;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
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
import com.ruslan.pocketdoc.R;
import com.ruslan.pocketdoc.clinic.ClinicActivity;
import com.ruslan.pocketdoc.data.clinics.Clinic;
import com.ruslan.pocketdoc.dialogs.LoadingErrorDialogFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Класс, описывающий фрагмент, отображающий карту.
 */
public class ClinicsMapFragment extends Fragment implements ClinicsContract.View {

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

    private ClinicsContract.Presenter mPresenter;

    private FragmentManager mFragmentManager;
    private GoogleMap mGoogleMap;
    private UiSettings mUiSettings;
    private Menu mMenu;

    private CameraUpdate mRestoredCameraPosition;
    private boolean mLocationPermissionGranted;
    private List<Marker> mMarkers = new ArrayList<>();
    private boolean mIsDisplayed;
    private boolean mAreMarkersInDb;
    private boolean mShowOnlyClinics;
    private boolean mShowOnlyDiagnostics;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
        Objects.requireNonNull(getActivity()).setTitle(R.string.clinics_title);
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment supportMapFragment =
                (SupportMapFragment) mFragmentManager.findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this::initMap);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
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

    /**
     * Метод восстановления состояния пунктов меню.
     */
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
                mGoogleMap.clear();
                mMarkers.clear();
                mPresenter.getOnlyClinicsFromDb();
                return true;
            case R.id.item_show_only_diagnostics:
                changeMenuChecks(menuItem, R.id.item_show_only_clinics);
                checkMenuItems();
                mGoogleMap.clear();
                mMarkers.clear();
                mPresenter.getOnlyDiagnosticsFromDb();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    /**
     * Метод изменения выбора пунктов меню
     * @param menuItem Пункт меню.
     * @param menuItemId Идентификатор другого пункта меню.
     */
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

    /**
     * Метод проверки выбранных пунктов меню.
     */
    private void checkMenuItems() {
        MenuItem clinicsMenuItem = mMenu.findItem(R.id.item_show_only_clinics);
        MenuItem diagnosticsMenuItem = mMenu.findItem(R.id.item_show_only_diagnostics);
        if (clinicsMenuItem.isChecked()) {
            mShowOnlyClinics = true;
            mShowOnlyDiagnostics = false;
        } else if (diagnosticsMenuItem.isChecked()) {
            mShowOnlyClinics = false;
            mShowOnlyDiagnostics = true;
        } else {
            mShowOnlyClinics = false;
            mShowOnlyDiagnostics = false;
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
        // Отмена всех предыдущих задач, если они не успели завершиться до начала новой.
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

    /**
     * Метод инициализации Google Maps.
     * @param googleMap Объект карт Google Maps.
     */
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

    /**
     * Метод отображения информации о мед. учреждении
     * при нажатии на окно маркера.
     * @param marker Маркер, на окно которого происходит нажатие.
     */
    private void onInfoClick(@NonNull Marker marker) {
        showClinicInfoUi(Objects.requireNonNull((Integer) marker.getTag()));
    }

    /**
     * Метод загрузки или отображения мед. учреждений
     * при остановке движения камеры.
     */
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

    /**
     * Метод сокрытия маркеров.
     */
    private void hideMarkers() {
        for (Marker marker : mMarkers) {
            marker.setVisible(false);
        }
    }

    /**
     * Метод запроса разрешения на считывание информации о местоположении устройства.
     */
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

    /**
     * Метод обновления UI после проверки разрешения на получение информации
     * о местоположении устройства.
     */
    private void updateLocationUi() {
        try {
            if (mLocationPermissionGranted) {
                mGoogleMap.setMyLocationEnabled(true);
                mUiSettings.setMyLocationButtonEnabled(true);
                mPresenter.getClinicsCount();
            } else {
                mGoogleMap.setMyLocationEnabled(false);
                mUiSettings.setMyLocationButtonEnabled(false);
                mPresenter.getClinicsCount();
            }
        } catch (SecurityException ex) {
            Log.e(TAG, "Location error: " + ex.getMessage(), ex);
        }
    }

    /*
    Ненужные методы!
     */

    @Override
    public void showProgressBar() {}

    @Override
    public void hideProgressBar() {}

    @Override
    public void hideRefreshing() {}
}
