package com.ruslanabzalov.pocketdoc.map;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ruslanabzalov.pocketdoc.R;

public class ClinicFragment extends Fragment {

    private static final String ARG_CLINIC = "clinic";

    private Hospital mClinic;

    public static Fragment newInstance(Hospital clinic) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CLINIC, clinic);
        ClinicFragment fragment = new ClinicFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mClinic = (Hospital) getArguments().getSerializable(ARG_CLINIC);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clinic, container, false);
        TextView mClinicNameTextView = view.findViewById(R.id.clinic_name);
        mClinicNameTextView.setText(mClinic.getName());
        TextView mClinicAddressTextView = view.findViewById(R.id.clinic_address);
        mClinicAddressTextView.setText(mClinic.getAddress());
        TextView mClinicPhoneTextView = view.findViewById(R.id.clinic_phone);
        mClinicPhoneTextView.setText(mClinic.getPhone());
        TextView mClinicDescriptionTextView = view.findViewById(R.id.clinic_description);
        mClinicDescriptionTextView.setText(mClinic.getDescription());
        Button mCallButton = view.findViewById(R.id.telephone_button);
        mCallButton.setOnClickListener((View v) -> {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",
                    mClinicPhoneTextView.getText().toString(), null));
            startActivity(intent);
        });
        return view;
    }
}
