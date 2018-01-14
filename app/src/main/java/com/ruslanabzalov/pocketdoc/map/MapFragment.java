package com.ruslanabzalov.pocketdoc.map;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ruslanabzalov.pocketdoc.DataFetch;

import java.util.List;

/**
 * Фрагмент, отвечающий за отображение на карте медицинских центров.
 */
public class MapFragment extends SupportMapFragment implements GoogleMap.OnMarkerClickListener {

    private GoogleMap mGoogleMap;
    private Marker mMarker;

    private List<Hospital> mHospitals;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Карта");
        getMapAsync((GoogleMap googleMap) -> {
            mGoogleMap = googleMap;
            mGoogleMap.setOnMarkerClickListener(this);
            // Позиционирование камеры на городе Москва.
            CameraPosition cameraPosition = new CameraPosition
                    .Builder()
                    .target(new LatLng(55.751244, 37.618423))
                    .zoom(10)
                    .build();
            mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        });
        new FetchHospitalsTask().execute();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        mMarker = marker;
        Intent intent = ClinicActivity.newIntent(getContext(), (Hospital) marker.getTag());
        startActivity(intent);
        return true;
    }

    private void addMarkers() {
        if (mHospitals.size() == 0 || mHospitals == null) {
            Toast.makeText(getActivity(), "Клиники не найдены!", Toast.LENGTH_SHORT).show();
        } else {
            for (Hospital clinic : mHospitals) {
                if (clinic.getLatitude().equals("null") ||
                        clinic.getLongitude().equals("null")) {
                    continue;
                }
                mMarker = mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(
                        Double.parseDouble(clinic.getLatitude()),
                        Double.parseDouble(clinic.getLongitude()))));
                mMarker.setTag(clinic);
            }
        }
    }

    private class FetchHospitalsTask extends AsyncTask<Void, Void, List<Hospital>> {

        @Override
        protected List<Hospital> doInBackground(Void... params) {
            return new DataFetch().fetchHospitals();
        }

        @Override
        protected void onPostExecute(List<Hospital> hospitals) {
            mHospitals = hospitals;
            addMarkers();
        }
    }
}