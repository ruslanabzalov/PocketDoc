package com.ruslan.pocketdoc.searching.specialities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ruslan.pocketdoc.R;
import com.ruslan.pocketdoc.api.DocDocApi;
import com.ruslan.pocketdoc.api.DocDocClient;
import com.ruslan.pocketdoc.data.SpecialityList;
import com.ruslan.pocketdoc.data.Speciality;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class SpecialitiesFragment extends Fragment {

    private static final String EXTRA_SPECIALITY_ID = "speciality_id";
    private static final String EXTRA_SPECIALITY_NAME = "speciality_name";
    private static final int MOSCOW_ID = 1;

    private RecyclerView mSpecialitiesRecyclerView;

    public static String getSpecialitiesFragmentResult(Intent data, String parameter) {
        switch(parameter) {
            case "id":
                return data.getStringExtra(EXTRA_SPECIALITY_ID);
            case "name":
                return data.getStringExtra(EXTRA_SPECIALITY_NAME);
            default:
                return null;
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_specialities, container, false);
        mSpecialitiesRecyclerView = view.findViewById(R.id.specs_recycler_view);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mSpecialitiesRecyclerView.setLayoutManager(linearLayoutManager);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadSpecialities();
    }

    private void loadSpecialities() {
        final DocDocApi api = DocDocClient.getClient();
        final Call<SpecialityList> specsListCall = api.getSpecialities(MOSCOW_ID);
        specsListCall.enqueue(new Callback<SpecialityList>() {
            @Override
            public void onResponse(@NonNull Call<SpecialityList> call,
                                   @NonNull Response<SpecialityList> response) {
                final SpecialityList specialityList = response.body();
                if (specialityList != null) {
                    final SpecialitiesAdapter specialitiesAdapter =
                            new SpecialitiesAdapter(specialityList.getSpecialityList(),
                                    SpecialitiesFragment.this::setSpecialitiesFragmentResult);
                    mSpecialitiesRecyclerView.setAdapter(specialitiesAdapter);
                }
            }

            @Override
            public void onFailure(@NonNull Call<SpecialityList> call, @NonNull Throwable t) {
                Toast.makeText(getActivity(),
                        getString(R.string.load_error_toast) + t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setSpecialitiesFragmentResult(Speciality speciality) {
        final String specialityId = speciality.getId();
        final String specialityName = speciality.getName();
        final Intent data = new Intent();
        data.putExtra(EXTRA_SPECIALITY_ID, specialityId);
        data.putExtra(EXTRA_SPECIALITY_NAME, specialityName);
        getActivity().setResult(RESULT_OK, data);
        getActivity().finish();
    }
}
