package com.ruslanabzalov.pocketdoc.treatment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ruslanabzalov.pocketdoc.R;

import java.util.UUID;

public class MedicamentFragment extends Fragment {

    private static final String ARG_DRUG_ID = "drug_id";

    private Medicament mMedicament;

    public MedicamentFragment() {
    }

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
        getActivity().setTitle(getString(R.string.medicament_fragment_label));
        UUID medicamentId = (UUID) getArguments().getSerializable(ARG_DRUG_ID);
        mMedicament = MedicamentsList.get(getActivity()).getMedicament(medicamentId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_medicament, container, false);
        return view;
    }
}
