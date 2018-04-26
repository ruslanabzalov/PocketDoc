package com.ruslanabzalov.pocketdoc.doctors;

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

import com.ruslanabzalov.pocketdoc.R;

public class DoctorsParametersFragment extends Fragment {

    private static final int REQUEST_CODE_SPECIALITIES = 0;
    private static final int REQUEST_CODE_STATIONS = 1;

    private Button mGetSpecialitiesButton;
    private Button mGetStationsButton;
    private Button mFindDoctorsButton;

    public DoctorsParametersFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(getString(R.string.title_doctors));
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =
                inflater.inflate(R.layout.fragment_doctors_parameters, container, false);
        mGetSpecialitiesButton = view.findViewById(R.id.specialities_button);
        mGetSpecialitiesButton.setOnClickListener((View v) -> {
            Intent intent =
                    ParametersListActivity.newIntent(getActivity(), "Specialities");
            startActivityForResult(intent, REQUEST_CODE_SPECIALITIES);
        });
        mGetStationsButton = view.findViewById(R.id.stations_button);
        mGetStationsButton.setOnClickListener((View v) -> {
            Intent intent =
                    ParametersListActivity.newIntent(getActivity(), "Stations");
            startActivityForResult(intent, REQUEST_CODE_STATIONS);
        });
        mFindDoctorsButton = view.findViewById(R.id.find_doctors_button);
        mFindDoctorsButton.setOnClickListener((View v) -> {
            // TODO: Открытие активности, отображающей список врачей.
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_docs_search, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.history_of_visits_item:
                // TODO: Открытие активности, отображающей историю записей.
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
