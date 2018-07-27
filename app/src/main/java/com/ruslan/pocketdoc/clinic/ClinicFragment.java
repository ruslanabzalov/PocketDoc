package com.ruslan.pocketdoc.clinic;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ruslan.pocketdoc.R;

import java.util.Objects;

public class ClinicFragment extends Fragment {

    private static final String ARG_CLINIC = "clinic_id";

    private int mClinicId;

    public static Fragment newInstance(int clinicId) {
        Bundle arguments = new Bundle();
        arguments.putInt(ARG_CLINIC, clinicId);
        Fragment clinicFragment = new ClinicFragment();
        clinicFragment.setArguments(arguments);
        return clinicFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mClinicId = Objects.requireNonNull(getArguments()).getInt(ARG_CLINIC);
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
