package com.ruslanabzalov.pocketdoc.doctors.controller;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ruslanabzalov.pocketdoc.R;
import com.ruslanabzalov.pocketdoc.docdoc.DocDocApi;
import com.ruslanabzalov.pocketdoc.docdoc.DocDocClient;
import com.ruslanabzalov.pocketdoc.doctors.model.Doctor;
import com.ruslanabzalov.pocketdoc.doctors.model.DoctorsList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorsFragment extends Fragment {

    private static final String ARG_SPEC_ID =
            "com.ruslanabzalov.pocketdoc.doctors.controllers.DoctorsFragment.spec_id";

    private static final String ARG_STATION_ID =
            "com.ruslanabzalov.pocketdoc.doctors.controllers.DoctorsFragment.station_id";

    private List<Doctor> mDoctorsList = new ArrayList<>();
    private String mDocsSpecId;
    private String mDocsStationId;

    public static Fragment newInstance(String specId, String stationId) {
        Bundle arguments = new Bundle();
        arguments.putString(ARG_SPEC_ID, specId);
        arguments.putString(ARG_STATION_ID, stationId);
        DoctorsFragment fragment = new DoctorsFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDocsSpecId = getArguments().getString(ARG_SPEC_ID, null);
        mDocsStationId = getArguments().getString(ARG_STATION_ID, null);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doctors, container, false);
        RecyclerView doctorsListRecyclerView = view.findViewById(R.id.doctors_list_recycler_view);
        doctorsListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        for (int i = 0; i < 100; i++) {
            mDoctorsList.add(new Doctor());
        }
//        DocDocApi api = DocDocClient.createClient();
//        getDoctors(api);
        doctorsListRecyclerView.setAdapter(new DoctorsListAdapter(mDoctorsList));
        return view;
    }

    private class DoctorsListViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private TextView mDoctorsNameTextView;
        private TextView mClinicsNameTextView;

        DoctorsListViewHolder(View view) {
            super(view);
            itemView.setOnClickListener(this);
            mDoctorsNameTextView = itemView.findViewById(R.id.doctors_name_text_view);
            mClinicsNameTextView = itemView.findViewById(R.id.clinics_name_text_view);
        }

//        public void bind(Doctor doctor) {}

        @Override
        public void onClick(View v) {
            // TODO: Обработка нажатия на определённого врача в списке
        }
    }

    private class DoctorsListAdapter extends RecyclerView.Adapter<DoctorsListViewHolder> {

        private List<Doctor> mDoctors;

        DoctorsListAdapter(List<Doctor> doctors) {
            mDoctors = doctors;
        }

        @NonNull
        @Override
        public DoctorsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.list_item_doctor, parent, false);
            return new DoctorsListViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull DoctorsListViewHolder viewHolder, int position) {
            viewHolder.mDoctorsNameTextView.setText("Имя доктора");
            viewHolder.mClinicsNameTextView.setText("Название клиники");
        }

        @Override
        public int getItemCount() {
            return mDoctors.size();
        }
    }


    private void getDoctors(DocDocApi api) {
        Call<DoctorsList> doctorsListCall = api.getDoctors(
                // Тестовые данные.
                DocDocClient.AUTHORIZATION, 0, 500, 1, 87, 168,
                "strict", "rating", 0, 0, 1, 14
        );
        doctorsListCall.enqueue(new Callback<DoctorsList>() {
            @Override
            public void onResponse(@NonNull Call<DoctorsList> call,
                                   @NonNull Response<DoctorsList> response) {

            }

            @Override
            public void onFailure(@NonNull Call<DoctorsList> call, @NonNull Throwable t) {

            }
        });
    }
}