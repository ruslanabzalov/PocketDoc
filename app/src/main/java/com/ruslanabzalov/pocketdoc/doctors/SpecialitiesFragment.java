package com.ruslanabzalov.pocketdoc.doctors;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ruslanabzalov.pocketdoc.R;
import com.ruslanabzalov.pocketdoc.docdoc.DocDocApi;
import com.ruslanabzalov.pocketdoc.docdoc.DocDocClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class SpecialitiesFragment extends Fragment {

    private static final String EXTRA_DOCS_SPECIALITY_ID
            = "com.ruslanabzalov.pocketdoc.docs.docs_speciality_id";
    private static final String EXTRA_DOCS_SPECIALITY_NAME
            = "com.ruslanabzalov.pocketdoc.docs.docs_speciality_name";

    private static final int MOSCOW_ID = 1;

    private List<Speciality> mSpecialities = new ArrayList<>();

    private RecyclerView mSpecialitiesRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Специализации врачей");
        DocDocApi api = DocDocClient.createClient();
        specialitiesCall(api, MOSCOW_ID);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =
                inflater.inflate(R.layout.fragment_specialities_list, container, false);
        mSpecialitiesRecyclerView = view.findViewById(R.id.specialities_list_recycler_view);
        mSpecialitiesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    private void specialitiesCall(DocDocApi api, int cityId) {
        Call<SpecialitiesList> specialities =
                api.getSpecialities(DocDocClient.AUTHORIZATION, cityId);
        specialities.enqueue(new Callback<SpecialitiesList>() {
            @Override
            public void onResponse(@NonNull Call<SpecialitiesList> call,
                                   @NonNull Response<SpecialitiesList> response) {
                mSpecialities = response.body().getSpecialities();
                mSpecialitiesRecyclerView.setAdapter(new SpecialitiesAdapter(mSpecialities));
            }

            @Override
            public void onFailure(@NonNull Call<SpecialitiesList> call, @NonNull Throwable t) {
                Toast.makeText(getContext(),
                        getString(R.string.error_toast), Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }

    private void fragmentResult(String specialityId, String specialityName) {
        Intent data = new Intent();
        data.putExtra(EXTRA_DOCS_SPECIALITY_ID, specialityId);
        data.putExtra(EXTRA_DOCS_SPECIALITY_NAME, specialityName);
        getActivity().setResult(RESULT_OK, data);
        getActivity().finish();
    }

    private class SpecialitiesHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private TextView mSpecialityNameTextView;

        private Speciality mSpeciality;

        private SpecialitiesHolder(View view) {
            super(view);
            itemView.setOnClickListener(this);
            mSpecialityNameTextView = itemView.findViewById(R.id.speciality_text_view);
        }

        private void bind(Speciality speciality) {
            mSpeciality = speciality;
            mSpecialityNameTextView.setText(mSpeciality.getName());
        }

        @Override
        public void onClick(View v) {
            String specialityId = mSpeciality.getId();
            String specialityName = mSpeciality.getName();
            fragmentResult(specialityId, specialityName);
        }
    }

    private class SpecialitiesAdapter extends RecyclerView.Adapter<SpecialitiesHolder> {

        private List<Speciality> mSpecialities;

        SpecialitiesAdapter(List<Speciality> specialities) {
            mSpecialities = specialities;
        }

        @NonNull
        @Override
        public SpecialitiesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_speciality, parent, false);
            return new SpecialitiesHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SpecialitiesHolder specialitiesHolder, int position) {
            specialitiesHolder.bind(mSpecialities.get(position));
        }

        @Override
        public int getItemCount() {
            return mSpecialities.size();
        }
    }
}
