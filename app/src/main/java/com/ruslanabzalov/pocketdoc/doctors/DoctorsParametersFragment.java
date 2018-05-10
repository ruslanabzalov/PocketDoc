package com.ruslanabzalov.pocketdoc.doctors;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.Switch;
import android.widget.Toast;

import com.ruslanabzalov.pocketdoc.R;

public class DoctorsParametersFragment extends Fragment {

    private static final int REQUEST_CODE_SPECIALITIES = 0;
    private static final int REQUEST_CODE_STATIONS = 1;

    private Button mDocTypesButton;
    private Button mSpecialitiesButton;
    private Button mStationsButton;
    private Switch mHomeSwitch;
    private Button mFindDoctorsButton;

    private String specId;
    private String specName;
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
        View view =
                inflater.inflate(R.layout.fragment_doctors_parameters, container, false);
        mSpecialitiesButton = view.findViewById(R.id.specialities_button);
        mSpecialitiesButton.setOnClickListener((View v) -> {
            Intent intent =
                    ParametersListActivity.newIntent(getActivity(), "Specialities");
            startActivityForResult(intent, REQUEST_CODE_SPECIALITIES);
        });
        mStationsButton = view.findViewById(R.id.stations_button);
        mStationsButton.setOnClickListener((View v) -> {
            Intent intent =
                    ParametersListActivity.newIntent(getActivity(), "Stations");
            startActivityForResult(intent, REQUEST_CODE_STATIONS);
        });
        mHomeSwitch = view.findViewById(R.id.home_switch);
        mFindDoctorsButton = view.findViewById(R.id.find_doctors_button);
        mFindDoctorsButton.setOnClickListener((View v) -> {
            Intent intent = new Intent(getContext(), DoctorsListActivity.class);
            startActivity(intent);
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isNetworkEnabled()) {
            Toast.makeText(getContext(), "Подключение отсутствует!", Toast.LENGTH_SHORT).show();
            mSpecialitiesButton.setEnabled(false);
            mStationsButton.setEnabled(false);
            mFindDoctorsButton.setEnabled(false);
        } else {
            mSpecialitiesButton.setEnabled(true);
            mStationsButton.setEnabled(true);
            mFindDoctorsButton.setEnabled(true);
        }
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

    /**
     * Вспомогательный метод, проверяющий доступность сети.
     * @return Флаг, указывающий на доступность или недоступность сети.
     */
    private boolean isNetworkEnabled() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
}
