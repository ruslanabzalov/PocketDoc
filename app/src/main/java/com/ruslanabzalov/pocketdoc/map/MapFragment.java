package com.ruslanabzalov.pocketdoc.map;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import com.ruslanabzalov.pocketdoc.database.DatabaseHelper;
import com.ruslanabzalov.pocketdoc.database.HospitalCursorWrapper;

import java.util.ArrayList;
import java.util.List;

import static com.ruslanabzalov.pocketdoc.database.DatabaseSchema.HospitalsTable;

/**
 * Класс, отвечающий за отображение фрагмента Google Map с маркерами медицинских центров.
 */
public class MapFragment extends SupportMapFragment implements GoogleMap.OnMarkerClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private SQLiteDatabase mDatabase;
    private GoogleApiClient mGoogleApiClient;
    private GoogleMap mGoogleMap;
    private LocationRequest mLocationRequest;
    private Marker mMarker;

    private List<Hospital> mHospitals;
    private boolean enableMenu;

    private static ContentValues getContentValues(Hospital hospital) {
        ContentValues values = new ContentValues();
        values.put(HospitalsTable.Cols.NAME, hospital.getName());
        values.put(HospitalsTable.Cols.TYPE, hospital.getType());
        values.put(HospitalsTable.Cols.DESCRIPTION, hospital.getDescription());
        values.put(HospitalsTable.Cols.ADDRESS, hospital.getAddress());
        values.put(HospitalsTable.Cols.PHONE, hospital.getPhone());
        values.put(HospitalsTable.Cols.LONGITUDE, hospital.getLongitude());
        values.put(HospitalsTable.Cols.LATITUDE, hospital.getLatitude());
        return values;
    }

    /**
     * Метод создания фрагмента MapFragment.
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mDatabase = new DatabaseHelper(getContext()).getWritableDatabase();
        getActivity().setTitle(getString(R.string.map_fragment_label));
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
        if (isDatabaseEmpty()) {
            new FetchHospitalsTask().execute();
            enableMenu = false;
            getActivity().invalidateOptionsMenu();
        } else {
            Toast.makeText(getActivity(), "Необходимые данные о клиниках уже загружены.",
                    Toast.LENGTH_SHORT).show();
            enableMenu = true;
            getActivity().invalidateOptionsMenu();
        }
    }

    /**
     * Метод создания меню.
     * @param menu объект меню.
     * @param inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_map, menu);
    }

    /**
     * Метод обработки нажатия на тот или иной пункт меню.
     * @param item пункт меню.
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.update_data:
                mGoogleMap.clear();
                mDatabase.delete(HospitalsTable.NAME, null, null);
                new FetchHospitalsTask().execute();
                Toast.makeText(getActivity(),
                        "Обновление данных о медицинских центрах. Ожидайте.",
                        Toast.LENGTH_SHORT).show();
                enableMenu = false;
                getActivity().invalidateOptionsMenu();
                return true;
            case R.id.hospitals:
                mGoogleMap.clear();
                mHospitals = getHospitals();
                addMarkers(mHospitals);
                return true;
            case R.id.policlinics:
                mGoogleMap.clear();
                return true;
            case R.id.policlinics_kids:
                mGoogleMap.clear();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        if (enableMenu) {
            menu.findItem(R.id.update_data).setEnabled(true);
            menu.findItem(R.id.hospitals).setEnabled(true);
            menu.findItem(R.id.policlinics).setEnabled(true);
            menu.findItem(R.id.policlinics_kids).setEnabled(true);
        } else {
            menu.findItem(R.id.update_data).setEnabled(false);
            menu.findItem(R.id.hospitals).setEnabled(false);
            menu.findItem(R.id.policlinics).setEnabled(false);
            menu.findItem(R.id.policlinics_kids).setEnabled(false);
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

    /**
     * Метод обработки нажатия на маркер.
     * @param marker объект маркер.
     * @return
     */
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

    /**
     * Метод для получения из базы данных необходимой информации о медицинских учреждениях.
     * @param whereClause
     * @param whereArgs
     * @return
     */
    private HospitalCursorWrapper queryHospitals(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                HospitalsTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new HospitalCursorWrapper(cursor);
    }

    /**
     * Метод для получения списка сохранённых медицинских учреждений из базы данных.
     * @return список сохранённых медицинских учреждений.
     */
    private List<Hospital> getHospitals() {
        List<Hospital> hospitals = new ArrayList<>();
        try (HospitalCursorWrapper cursorWrapper = queryHospitals(null, null)) {
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()) {
                hospitals.add(cursorWrapper.getHospital());
                cursorWrapper.moveToNext();
            }
        }
        return hospitals;
    }

    /**
     * Метод добавления маркеров медицинских центров на карту.
     * @param hospitals список медицинских центров.
     */
    private void addMarkers(List<Hospital> hospitals) {
        for (Hospital clinic : hospitals) {
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

    /**
     * Метод для добавления в базу данных информации о медицинском учреждении.
     * @param hospital медицинское учреждение.
     */
    private void addHospitalsToDatabase(Hospital hospital) {
        ContentValues values = getContentValues(hospital);
        mDatabase.insert(HospitalsTable.NAME, null, values);
    }

    /**
     * Метод для проверки базы данных на наличие информации о медицинских учреждениях.
     * @return
     */
    private boolean isDatabaseEmpty() {
        try (HospitalCursorWrapper cursorWrapper = queryHospitals(null, null)) {
            return cursorWrapper.getCount() == 0;
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
            if (mHospitals.size() == 0) {
                enableMenu = false;
                getActivity().invalidateOptionsMenu();
                Toast.makeText(getActivity(), "Клиники не найдены!", Toast.LENGTH_SHORT).show();
            } else {
                for (Hospital hospital : hospitals) {
                    addHospitalsToDatabase(hospital);
                }
                enableMenu = true;
                getActivity().invalidateOptionsMenu();
                Toast.makeText(getActivity(), "Информация о клиниках загружена!",
                        Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), "Выберите необходимый тип клиник в верхнем меню.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}
