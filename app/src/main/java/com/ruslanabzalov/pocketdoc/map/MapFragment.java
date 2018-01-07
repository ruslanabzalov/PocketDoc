package com.ruslanabzalov.pocketdoc.map;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

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
        getMapAsync((GoogleMap googleMap) -> {
            mGoogleMap = googleMap;
            // Запрос разрешения на отображения его геопозиции.
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

    private class FetchHospitalsTask extends AsyncTask<Void, Void, List<Hospital>> {

        @Override
        protected List<Hospital> doInBackground(Void... params) {
            return new DataFetch().fetchHospitals();
        }

        @Override
        protected void onPostExecute(List<Hospital> hospitals) {
            mHospitals = hospitals;
            if (mHospitals.size() == 0 || mHospitals == null) {
                Toast.makeText(getActivity(), "Клиники не найдены!", Toast.LENGTH_SHORT).show();
            } else {
                for (int i = 0; i < mHospitals.size(); i++) {
                    mMarker = mGoogleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(
                                    Double.parseDouble(mHospitals.get(i).getLatitude()),
                                    Double.parseDouble(mHospitals.get(i).getLongitude()))));
                    mMarker.setTag(mHospitals.get(i));
                }
            }
        }
    }
}