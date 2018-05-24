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
import com.ruslan.pocketdoc.searching.specialities.SpecialitiesActivity;
import com.ruslan.pocketdoc.searching.specialities.SpecialitiesFragment;
import com.ruslan.pocketdoc.searching.stations.StationsActivity;
import com.ruslan.pocketdoc.searching.stations.StationsFragment;

public class SearchingFragment extends Fragment {

    private static final int REQUEST_CODE_SPECIALITIES = 0;
    private static final int REQUEST_CODE_STATIONS = 1;

    private Button mShowSpecialitiesButton;
    private Button mShowStationsButton;
    private Button mShowDoctorsButton;

    private String specialityId;
    private String specialityName;
    private String stationId;
    private String stationName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_searching, container, false);
        mShowSpecialitiesButton = view.findViewById(R.id.specialities_button);
        mShowSpecialitiesButton.setOnClickListener(v -> startSpecialitiesActivity());
        mShowStationsButton = view.findViewById(R.id.stations_button);
        mShowStationsButton.setOnClickListener(v -> startStationsActivity());
        mShowDoctorsButton = view.findViewById(R.id.find_doctors_button);
        mShowDoctorsButton.setOnClickListener(v -> startDoctorsActivity());
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_SPECIALITIES:
                if (resultCode == Activity.RESULT_OK) {
                    specialityId = SpecialitiesFragment.getSpecialitiesFragmentResult(data, "id");
                    specialityName = SpecialitiesFragment.getSpecialitiesFragmentResult(data, "name");
                    updateUI();
                }
                break;
            case REQUEST_CODE_STATIONS:
                if (resultCode == Activity.RESULT_OK) {
                    stationId = StationsFragment.getStationsFragmentResult(data, "id");
                    stationName = StationsFragment.getStationsFragmentResult(data, "name");
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
            case R.id.favorite_doctors_item:
                // TODO: Start Activity with favorite doctors.
                return true;
            case R.id.records_history_item:
                // TODO: Start Activity with records history.
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    private void startSpecialitiesActivity() {
        final Intent intent = new Intent(getActivity(), SpecialitiesActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SPECIALITIES);
    }

    private void startStationsActivity() {
        final Intent intent = new Intent(getActivity(), StationsActivity.class);
        startActivityForResult(intent, REQUEST_CODE_STATIONS);
    }

    private void startDoctorsActivity() {
        final Intent intent = DoctorsActivity.newIntent(getActivity(), specialityId, stationId);
        startActivity(intent);
    }

    private void updateUI() {
        if (stationName != null) {
            mShowStationsButton.setText(stationName);
        }
        if (specialityName != null) {
            mShowSpecialitiesButton.setText(specialityName);
        }
    }
}
