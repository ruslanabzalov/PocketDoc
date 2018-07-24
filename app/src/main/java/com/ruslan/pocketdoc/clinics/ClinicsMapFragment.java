package com.ruslan.pocketdoc.clinics;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ruslan.pocketdoc.R;
import com.ruslan.pocketdoc.clinic.ClinicActivity;
import com.ruslan.pocketdoc.data.clinics.Clinic;

import java.util.List;
import java.util.Objects;

public class ClinicsMapFragment extends Fragment implements ClinicsContract.View {

    private final static LatLng MOSCOW = new LatLng(55.751244, 37.618423);

    private ClinicsContract.Presenter mPresenter;

    private FragmentManager mFragmentManager;
    private GoogleMap mGoogleMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mPresenter = new ClinicsPresenter();
        mPresenter.attachView(this);
        mFragmentManager = getChildFragmentManager();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Objects.requireNonNull(getActivity()).setTitle(R.string.clinics_title);
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment supportMapFragment = (SupportMapFragment) mFragmentManager.findFragmentById(R.id.map);
        supportMapFragment.getMapAsync((googleMap -> {
            mGoogleMap = googleMap;
            mGoogleMap.setMinZoomPreference(10f);
            mGoogleMap.setMaxZoomPreference(18f);
            CameraPosition moscowPosition = new CameraPosition.Builder()
                    .target(MOSCOW)
                    .zoom(10f)
                    .build();
            mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(moscowPosition));
            mGoogleMap.setOnMarkerClickListener(this::isMarkerClicked);
        }));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.loadClinics();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
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
    public void showSuccessLoadingMessage() {
        Toast.makeText(getActivity(), "Клиники успешно загружены!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorDialog(Throwable throwable) {
        Toast.makeText(getActivity(), "Клиники загружены не были!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showClinics(List<Clinic> clinics) {
        for (Clinic clinic : clinics) {
            mGoogleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(Double.parseDouble(clinic.getLatitude()),
                            Double.parseDouble(clinic.getLongitude())))
                    .title(clinic.getName())
            );
        }
    }

    @Override
    public void showProgressBar() {}

    @Override
    public void hideProgressBar() {}

    @Override
    public void showClinicInfoUi(int clinicId) {}

    private boolean isMarkerClicked(Marker marker) {
        Intent intent = ClinicActivity.newIntent(getContext());
        startActivity(intent);
        return true;
    }
}
