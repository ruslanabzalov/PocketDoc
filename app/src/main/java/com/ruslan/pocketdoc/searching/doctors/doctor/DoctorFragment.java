package com.ruslan.pocketdoc.searching.doctors.doctor;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ruslan.pocketdoc.R;

public class DoctorFragment extends Fragment {

    private static final String ARG_DOCTOR_ID = "doctor_id";

    private int mDoctorId;

    /**
     * Method of DoctorFragment creation.
     * @return DoctorsFragment instance.
     */
    public static Fragment newInstance(int doctorId) {
        Bundle args = new Bundle();
        args.putInt(ARG_DOCTOR_ID, doctorId);
        DoctorFragment doctorFragment = new DoctorFragment();
        doctorFragment.setArguments(args);
        return doctorFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDoctorId = getArguments().getInt(ARG_DOCTOR_ID, 0);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doctor, container, false);
        return view;
    }
}
