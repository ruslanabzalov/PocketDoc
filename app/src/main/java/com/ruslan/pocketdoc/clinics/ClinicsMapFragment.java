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
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ruslan.pocketdoc.R;
import com.ruslan.pocketdoc.clinic.ClinicActivity;
import com.ruslan.pocketdoc.data.clinics.Clinic;
import com.ruslan.pocketdoc.dialogs.LoadingErrorDialogFragment;

import java.util.List;
import java.util.Objects;

public class ClinicsMapFragment extends Fragment implements ClinicsContract.View {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private static final String TAG = ClinicsMapFragment.class.getSimpleName();

    private static final String TAG_LOADING_ERROR_DIALOG =
            LoadingErrorDialogFragment.class.getSimpleName();

    private static final int LOADING_ERROR_DIALOG_FRAGMENT_REQUEST_CODE = 999;

    private static final LatLng MOSCOW = new LatLng(55.751244, 37.618423);

    private static final String IS_DISPLAYED_KEY = "is_displayed";

    private ClinicsContract.Presenter mPresenter;

    private FragmentManager mFragmentManager;
    private GoogleMap mGoogleMap;
    private UiSettings mUiSettings;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    private boolean mLocationPermissionGranted;
    private boolean isDisplayed = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mFragmentManager = getChildFragmentManager();
        mFusedLocationProviderClient = LocationServices
                .getFusedLocationProviderClient(Objects.requireNonNull(getActivity()));
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
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                    break;
                }
        }
        updateLocationUi();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {}

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_map, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_refresh_clinics:
                mPresenter.updateClinics();
                mGoogleMap.clear();
                return true;
            case R.id.item_show_clinics:
                mPresenter.getOnlyClinics();
                mGoogleMap.clear();
                return true;
            case R.id.item_show_diagnostics:
                mPresenter.getOnlyDiagnostics();
                mGoogleMap.clear();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showClinics(List<Clinic> clinics) {
        Toast.makeText(getActivity(), clinics.size() + "", Toast.LENGTH_SHORT).show();
        if (!isDisplayed) {
            for (Clinic clinic : clinics) {
                mGoogleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(Double.parseDouble(clinic.getLatitude()),
                                Double.parseDouble(clinic.getLongitude())))
                        .title(clinic.getName())
                ).setTag(clinic.getId());
                isDisplayed = true;
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
        mGoogleMap.setMinZoomPreference(10f);
        mGoogleMap.setMaxZoomPreference(18f);
        CameraPosition moscowPosition = new CameraPosition.Builder()
                .target(MOSCOW)
                .zoom(10f)
                .build();
        mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(moscowPosition));
        mGoogleMap.setOnInfoWindowClickListener(marker -> mPresenter.chooseClinic((Integer) marker.getTag()));
        getLocationPermission();
    }

    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
            updateLocationUi();
        } else {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
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
            Log.e(TAG, ex.getMessage(), ex);
        }
    }
}
