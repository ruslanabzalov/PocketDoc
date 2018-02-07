package com.ruslanabzalov.pocketdoc.map;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ruslanabzalov.pocketdoc.DataFetch;
import com.ruslanabzalov.pocketdoc.R;

import java.util.List;

public class MapFragment extends SupportMapFragment implements GoogleMap.OnMarkerClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private GoogleApiClient mGoogleApiClient;
    private GoogleMap mGoogleMap;
    private LocationRequest mLocationRequest;
    private Marker mMarker;

    private List<Hospital> mHospitals;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(getString(R.string.map_fragment_label));
        setHasOptionsMenu(true);
        getMapAsync((GoogleMap googleMap) -> {
            mGoogleMap = googleMap;
            mGoogleMap.setOnMarkerClickListener(this);
            CameraPosition cameraPosition = new CameraPosition
                    .Builder()
                    .target(new LatLng(55.751244, 37.618423))
                    .zoom(10)
                    .build();
            mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
                    buildGoogleApiClient();
                    mGoogleMap.setMyLocationEnabled(true);
                } else {
                    checkLocationPermission();
                }
            }
            else {
                buildGoogleApiClient();
                mGoogleMap.setMyLocationEnabled(true);
            }
        });
        new FetchHospitalsTask().execute();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_map, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.hospitals:
                // TODO: Выбор отображаемых маркеров.
                return true;
            case R.id.policlinics:
                // TODO: Выбор отображаемых маркеров.
                return true;
            case R.id.policlinics_kids:
                // TODO: Выбор отображаемых маркеров.
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.
                    removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        mMarker = marker;
        Intent intent = ClinicActivity.newIntent(getContext(), (Hospital) marker.getTag());
        startActivity(intent);
        return true;
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi
                    .requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {}

    @Override
    public void onLocationChanged(Location location) {}

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(getContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION) ==
                            PackageManager.PERMISSION_GRANTED) {
                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mGoogleMap.setMyLocationEnabled(true);
                    }
                } else {
                    Toast.makeText(getContext(), "Доступ запрещён!!", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission," +
                                "please accept to use location functionality")
                        .setPositiveButton("OK",
                                (dialogInterface, i) -> ActivityCompat
                                        .requestPermissions(getActivity(),
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                MY_PERMISSIONS_REQUEST_LOCATION ))
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION );
            }
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
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
    }
}
