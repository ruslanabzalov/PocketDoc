package com.ruslanabzalov.pocketdoc.doctors.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ruslanabzalov.pocketdoc.R;
import com.ruslanabzalov.pocketdoc.docdoc.DocDocApi;
import com.ruslanabzalov.pocketdoc.docdoc.DocDocClient;
import com.ruslanabzalov.pocketdoc.doctors.model.Spec;
import com.ruslanabzalov.pocketdoc.doctors.model.SpecsList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class SpecsFragment extends Fragment {

    private static final String TAG = "SpecsFragment";
    private static final String EXTRA_DOCS_SPECIALITY_ID =
            "com.ruslanabzalov.pocketdoc.docs.docs_speciality_id";
    private static final String EXTRA_DOCS_SPECIALITY_NAME =
            "com.ruslanabzalov.pocketdoc.docs.docs_speciality_name";

    private static final int MOSCOW_ID = 1;

    private RecyclerView mSpecsRecyclerView;

    private List<Spec> mSpecs = new ArrayList<>();

    public static String getData(Intent data, String param) {
    return (param.equals("id"))
            ? data.getStringExtra(EXTRA_DOCS_SPECIALITY_ID)
            : data.getStringExtra(EXTRA_DOCS_SPECIALITY_NAME);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_specs, container, false);
        mSpecsRecyclerView = view.findViewById(R.id.specs_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mSpecsRecyclerView.setLayoutManager(linearLayoutManager);

        DocDocApi api = DocDocClient.getClient();
        specsCall(api, MOSCOW_ID);
        return view;
    }

    private class SpecsHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private TextView mSpecNameTextView;

        private Spec mSpec;

        private SpecsHolder(View view) {
            super(view);
            mSpecNameTextView = itemView.findViewById(R.id.spec_text_view);
            itemView.setOnClickListener(this);
        }

        private void bind(Spec spec) {
            mSpec = spec;
            mSpecNameTextView.setText(mSpec.getName());
        }

        @Override
        public void onClick(View v) {
            String specialityId = mSpec.getId();
            String specialityName = mSpec.getName();
            setFragmentResult(specialityId, specialityName);
        }
    }

    private class SpecsAdapter extends RecyclerView.Adapter<SpecsHolder> {

        private List<Spec> mSpecialities;

        SpecsAdapter(List<Spec> specialities) {
            mSpecialities = specialities;
        }

        @NonNull
        @Override
        public SpecsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.list_item_spec, parent, false);
            return new SpecsHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SpecsHolder specsHolder, int position) {
            specsHolder.bind(mSpecialities.get(position));
        }

        @Override
        public int getItemCount() {
            return mSpecialities.size();
        }
    }

    private void specsCall(DocDocApi api, int cityId) {
        Call<SpecsList> specialities =
                api.getSpecialities(DocDocClient.AUTHORIZATION, cityId);
        specialities.enqueue(new Callback<SpecsList>() {
            @Override
            public void onResponse(@NonNull Call<SpecsList> call,
                                   @NonNull Response<SpecsList> response) {
                SpecsList specsList = response.body();
                if (specsList != null) {
                    mSpecs = specsList.getSpecialities();
                    SpecsAdapter specsAdapter = new SpecsAdapter(mSpecs);
                    mSpecsRecyclerView.setAdapter(specsAdapter);
                }
            }

            @Override
            public void onFailure(@NonNull Call<SpecsList> call, @NonNull Throwable t) {
                Toast.makeText(getContext(),
                        getString(R.string.error_toast), Toast.LENGTH_LONG).show();
                Log.d(TAG, getString(R.string.error_toast), t);
            }
        });
    }


    private void setFragmentResult(String specialityId, String specialityName) {
        Intent data = new Intent();
        data.putExtra(EXTRA_DOCS_SPECIALITY_ID, specialityId);
        data.putExtra(EXTRA_DOCS_SPECIALITY_NAME, specialityName);
        getActivity().setResult(RESULT_OK, data);
        getActivity().finish();
    }
}
