package com.ruslan.pocketdoc.clinics;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ruslan.pocketdoc.R;
import com.ruslan.pocketdoc.clinic.ClinicActivity;
import com.ruslan.pocketdoc.data.clinics.Clinic;
import com.ruslan.pocketdoc.dialogs.LoadingErrorDialogFragment;

import java.util.List;
import java.util.Objects;

public class ClinicsMapFragment extends Fragment implements ClinicsContract.View {

    private static final String TAG = ClinicsMapFragment.class.getSimpleName();
    private static final String TAG_LOADING_ERROR_DIALOG =
            LoadingErrorDialogFragment.class.getSimpleName();

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final int LOADING_ERROR_DIALOG_FRAGMENT_REQUEST_CODE = 999;

    private static final String IS_DISPLAYED_KEY = "is_displayed";
    private static final String CAMERA_POSITION_KEY = "camera_position";

    private static final LatLng MOSCOW = new LatLng(55.751244, 37.618423);
    private static final LatLngBounds MOSCOW_BOUNDS = new LatLngBounds(
            new LatLng(55.5484, 37.2881), new LatLng(55.9426, 37.8855));
    private static final float DEFAULT_ZOOM = 10f;

    private ClinicsContract.Presenter mPresenter;

    private FragmentManager mFragmentManager;
    private GoogleMap mGoogleMap;
    private UiSettings mUiSettings;

    private CameraUpdate mRestoredCameraPosition;
    private boolean mLocationPermissionGranted;
    private boolean isDisplayed;

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
        Objects.requireNonNull(getActivity()).setTitle(R.string.clinics_title);
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment supportMapFragment =
                (SupportMapFragment) mFragmentManager.findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this::initMap);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            isDisplayed = savedInstanceState.getBoolean(IS_DISPLAYED_KEY, false);
            mRestoredCameraPosition = CameraUpdateFactory
                    .newCameraPosition(savedInstanceState.getParcelable(CAMERA_POSITION_KEY));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isDisplayed) {
            mPresenter.loadClinics();
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
        savedInstanceState.putBoolean(IS_DISPLAYED_KEY, isDisplayed);
        savedInstanceState.putParcelable(CAMERA_POSITION_KEY, mGoogleMap.getCameraPosition());
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {}

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
    public void startClinicsService() {
        Objects.requireNonNull(getActivity()).startService(ClinicsService.newIntent(getContext()));
    }

    @Override
    public void showClinicsInCurrentArea(List<Clinic> clinics) {
        LatLng clinicsLatLng;
        for (Clinic clinic : clinics) {
            clinicsLatLng = new LatLng(Double.parseDouble(clinic.getLatitude()),
                    Double.parseDouble(clinic.getLongitude()));
            if (mGoogleMap.getProjection().getVisibleRegion().latLngBounds.contains(clinicsLatLng)) {
                mGoogleMap.addMarker(new MarkerOptions()
                        .title(clinic.getShortName())
                        .position(clinicsLatLng)
                        .visible(true)
                ).setTag(clinic.getId());
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
        getLocationPermission();
        mGoogleMap.setOnMarkerClickListener(this::onMarkerClick);
        mGoogleMap.setOnInfoWindowClickListener(this::onInfoClick);
        mGoogleMap.setOnCameraIdleListener(this::onIdle);
    }

    private boolean onMarkerClick(@NonNull Marker marker) {
        marker.showInfoWindow();
        return true;
    }

    private void onInfoClick(@NonNull Marker marker) {
        mPresenter.chooseClinic(Objects.requireNonNull((Integer) marker.getTag()));
    }

    private void onIdle() {
        mGoogleMap.clear();
        if (mGoogleMap.getCameraPosition().zoom >= 15f) {
            mPresenter.getClinicsFromDb();
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
            } else {
                mGoogleMap.setMyLocationEnabled(false);
                mUiSettings.setMyLocationButtonEnabled(false);
            }
        } catch (SecurityException ex) {
            Log.e(TAG, ex.getMessage(), ex);
        }
    }
}
