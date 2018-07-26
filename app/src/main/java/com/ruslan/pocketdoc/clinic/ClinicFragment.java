package com.ruslan.pocketdoc.clinic;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ruslan.pocketdoc.R;
import com.ruslan.pocketdoc.data.clinics.Clinic;

import java.util.Objects;

public class ClinicFragment extends Fragment {

    private static final String ARG_CLINIC = "clinic";

    private Clinic mClinic;

    public static Fragment newInstance(Clinic clinic) {
        Bundle arguments = new Bundle();
        arguments.putSerializable(ARG_CLINIC, clinic);
        Fragment clinicFragment = new ClinicFragment();
        clinicFragment.setArguments(arguments);
        return clinicFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mClinic = (Clinic) Objects.requireNonNull(getArguments()).getSerializable(ARG_CLINIC);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Objects.requireNonNull(getActivity()).setTitle(R.string.clinic_title);
        View rootView = inflater.inflate(R.layout.fragment_clinic_info, container, false);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
