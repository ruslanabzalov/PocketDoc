package com.ruslan.pocketdoc.doc;

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
import android.widget.PopupMenu;
import android.widget.Switch;

import com.ruslan.pocketdoc.R;
import com.ruslan.pocketdoc.doc.docs.DocsActivity;
import com.ruslan.pocketdoc.doc.specs.SpecsFragment;
import com.ruslan.pocketdoc.doc.stations.StationsFragment;

public class SearchParamsFragment extends Fragment implements MenuItem.OnMenuItemClickListener {

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
        View view = inflater.inflate(R.layout.fragment_search_params, container, false);
        mDocTypesButton = view.findViewById(R.id.doctors_type_button);
        mDocTypesButton.setOnClickListener((View v) -> showPopup(mDocTypesButton));
        mDocTypesButton.setEnabled(false);
        mSpecialitiesButton = view.findViewById(R.id.specialities_button);
        mSpecialitiesButton.setOnClickListener((View v) -> {
            Intent intent = ParamsActivity.newIntent(getActivity(), "Specs");
            startActivityForResult(intent, REQUEST_CODE_SPECIALITIES);
        });
        mStationsButton = view.findViewById(R.id.stations_button);
        mStationsButton.setOnClickListener((View v) -> {
            Intent intent = ParamsActivity.newIntent(getActivity(), "Stations");
            startActivityForResult(intent, REQUEST_CODE_STATIONS);
        });
        mHomeSwitch = view.findViewById(R.id.home_switch);
        mHomeSwitch.setEnabled(false);
        mFindDoctorsButton = view.findViewById(R.id.find_doctors_button);
        mFindDoctorsButton.setOnClickListener((View v) -> {
            Intent intent = DocsActivity.newIntent(getActivity(), specId, stationId);
            startActivity(intent);
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_SPECIALITIES:
                if (resultCode == Activity.RESULT_OK) {
                    specId = SpecsFragment.getData(data, "id");
                    specName = SpecsFragment.getData(data, "name");
                    updateUI();
                }
                break;
            case REQUEST_CODE_STATIONS:
                if (resultCode == Activity.RESULT_OK) {
                    stationId = StationsFragment.getData(data, "id");
                    stationName = StationsFragment.getData(data, "name");
                    updateUI();
                }
                break;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_search_params, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.history_of_visits_item:
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.children_doctor:
                return true;
            case R.id.adult_doctor:
                return true;
            default:
                return false;
        }
    }

    private void showPopup(Button docTypesButton) {
        PopupMenu popupMenu = new PopupMenu(getActivity(), docTypesButton);
        MenuInflater menuInflater = popupMenu.getMenuInflater();
        menuInflater.inflate(R.menu.doctors_type_button, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(this::onMenuItemClick);
        popupMenu.show();
    }


    private void updateUI() {
        if (stationName != null) {
            mStationsButton.setText(stationName);
        }
        if (specName != null) {
            mSpecialitiesButton.setText(specName);
        }
    }
}
