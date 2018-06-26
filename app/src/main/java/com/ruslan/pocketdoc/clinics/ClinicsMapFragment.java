package com.ruslan.pocketdoc.clinics;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.ruslan.pocketdoc.R;
import com.ruslan.pocketdoc.data.clinics.Clinic;

import java.util.List;

public class ClinicsMapFragment extends SupportMapFragment implements ClinicsContract.View {

    private final static LatLng MOSCOW = new LatLng(55.751244, 37.618423);

    private ClinicsContract.Presenter mPresenter;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mPresenter = new ClinicsPresenter();
        mPresenter.attachView(this);
        mSwipeRefreshLayout = new SwipeRefreshLayout(getActivity());
        mSwipeRefreshLayout.setEnabled(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.loadClinics();
        getMapAsync((googleMap -> {
            googleMap.setMinZoomPreference(10f);
            googleMap.setMaxZoomPreference(18f);
            CameraPosition moscowPosition = new CameraPosition.Builder()
                    .target(MOSCOW)
                    .zoom(10f)
                    .build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(moscowPosition));
        }));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_map, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_refresh_clinics:
                // TODO: Refresh all clinics.
                return true;
            case R.id.item_show_clinics:
                // TODO: Show only simple clinics.
                return true;
            case R.id.item_show_diagnostics:
                // TODO: Show only diagnostics.
                return true;
            case R.id.item_show_all_clinics:
                // TODO: Show all clinics.
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showClinics(List<Clinic> clinics) {
        if (clinics.size() != 0) {
            Toast.makeText(getActivity(), "Клиники загружены!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showErrorMessage(Throwable throwable) {
        Toast.makeText(getActivity(), "Клиники загружены не были!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressBar() {
        mSwipeRefreshLayout.setEnabled(true);
    }

    @Override
    public void hideProgressBar() {
        mSwipeRefreshLayout.setEnabled(false);
    }
}
