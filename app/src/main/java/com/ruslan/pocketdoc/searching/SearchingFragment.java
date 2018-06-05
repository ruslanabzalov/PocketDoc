package com.ruslan.pocketdoc.searching;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ruslan.pocketdoc.R;
import com.ruslan.pocketdoc.searching.doctors.DoctorsActivity;
import com.ruslan.pocketdoc.searching.records.history.RecordsHistoryActivity;
import com.ruslan.pocketdoc.searching.specialities.SpecialitiesActivity;
import com.ruslan.pocketdoc.searching.specialities.SpecialitiesFragment;
import com.ruslan.pocketdoc.searching.stations.StationsActivity;
import com.ruslan.pocketdoc.searching.stations.StationsFragment;

public class SearchingFragment extends Fragment {

    private static final int REQUEST_CODE_SPECIALITIES = 0;
    private static final int REQUEST_CODE_STATIONS = 1;

    private static final String SPECIALITY_ID = "speciality_id";
    private static final String SPECIALITY_NAME = "speciality_name";
    private static final String STATION_ID = "station_id";
    private static final String STATION_NAME = "station_name";

    private Button mShowSpecialitiesButton;
    private Button mShowStationsButton;
    private Button mShowDoctorsButton;

    private String mSpecialityId;
    private String mSpecialityName;
    private String mStationId;
    private String mStationName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_searching, container, false);
        initViews(rootView);
        restoreButtonsState(savedInstanceState);
        updateUI();
        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        savedInstanceState.putString(SPECIALITY_ID, mSpecialityId);
        savedInstanceState.putString(SPECIALITY_NAME, mSpecialityName);
        savedInstanceState.putString(STATION_ID, mStationId);
        savedInstanceState.putString(STATION_NAME, mStationName);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_SPECIALITIES:
                if (resultCode == Activity.RESULT_OK) {
                    mSpecialityId =
                            SpecialitiesFragment.getSpecialitiesFragmentResult(data, "id");
                    mSpecialityName =
                            SpecialitiesFragment.getSpecialitiesFragmentResult(data, "name");
                    updateUI();
                }
                break;
            case REQUEST_CODE_STATIONS:
                if (resultCode == Activity.RESULT_OK) {
                    mStationId =
                            StationsFragment.getStationsFragmentResult(data, "id");
                    mStationName =
                            StationsFragment.getStationsFragmentResult(data, "name");
                    updateUI();
                }
                break;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_searching, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.records_history_item:
                startRecordsHistoryActivity();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    private void initViews(View rootView) {
        mShowSpecialitiesButton = rootView.findViewById(R.id.specialities_button);
        mShowSpecialitiesButton.setOnClickListener(v -> startSpecialitiesActivity());
        mShowStationsButton = rootView.findViewById(R.id.stations_button);
        mShowStationsButton.setOnClickListener(v -> startStationsActivity());
        mShowDoctorsButton = rootView.findViewById(R.id.find_doctors_button);
        mShowDoctorsButton.setOnClickListener(v -> startDoctorsActivity());
    }

    private void startSpecialitiesActivity() {
        Intent intent = new Intent(getActivity(), SpecialitiesActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SPECIALITIES);
    }

    private void startStationsActivity() {
        Intent intent = new Intent(getActivity(), StationsActivity.class);
        startActivityForResult(intent, REQUEST_CODE_STATIONS);
    }

    private void startDoctorsActivity() {
        Intent intent = DoctorsActivity.newIntent(getActivity(), mSpecialityId, mStationId);
        startActivity(intent);
    }

    private void startRecordsHistoryActivity() {
        Intent intent = new Intent(getActivity(), RecordsHistoryActivity.class);
        startActivity(intent);
    }

    private void restoreButtonsState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mSpecialityId = savedInstanceState.getString(SPECIALITY_ID, null);
            mSpecialityName = savedInstanceState.getString(SPECIALITY_NAME, null);
            mStationId = savedInstanceState.getString(STATION_ID, null);
            mStationName = savedInstanceState.getString(STATION_NAME, null);
        }
    }

    private void updateUI() {
        if (mStationName != null) {
            mShowStationsButton.setText(mStationName);
        }
        if (mSpecialityName != null) {
            mShowSpecialitiesButton.setText(mSpecialityName);
        }
    }
}
