package com.ruslanabzalov.pocketdoc.illnesses;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ruslanabzalov.pocketdoc.R;

import java.util.UUID;

public class MedicamentFragment extends Fragment {

    private static final String ARG_DRUG_ID = "drug_id";

    public static MedicamentFragment newInstance(UUID drugId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DRUG_ID, drugId);
        MedicamentFragment fragment = new MedicamentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drug, container, false);
        return view;
    }
}
