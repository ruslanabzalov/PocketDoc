package com.ruslanabzalov.pocketdoc.illnesses;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ruslanabzalov.pocketdoc.R;

import java.util.UUID;

public class MedicamentFragment extends Fragment {

    private static final String ARG_DRUG_ID = "drug_id";

    private Medicament mMedicament;

    private EditText mMedicamentTitleEditText;

    private Button mButton1;
    //private Button mButton2;
    private Button mButton3;
    private Button mButton4;
    private Button mButton5;
    private Button mButton6;
    private Button mButton7;
    private Button mSetNotification;

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
        getActivity().setTitle("Лекарство");
        UUID medicamentId = (UUID) getArguments().getSerializable(ARG_DRUG_ID);
        mMedicament = MedicamentsList.get(getActivity()).getMedicament(medicamentId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drug, container, false);
        mMedicamentTitleEditText = view.findViewById(R.id.medicament_name);
        mMedicamentTitleEditText.setText(mMedicament.getName());
        mMedicamentTitleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mMedicament.setName(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
        mMedicamentTitleEditText.setText("Аспирин");
        mButton1 = view.findViewById(R.id.a);
        mButton1.setText("После еды");
        mButton3 = view.findViewById(R.id.c);
        mButton3.setText("10");
        mButton4 = view.findViewById(R.id.d);
        mButton4.setText("1 таблетка(-и)");
        mButton5 = view.findViewById(R.id.e);
        mButton5.setText("2 часа(-ов)");
        mButton6 = view.findViewById(R.id.f);
        mButton6.setText("10:00");
        mButton7 = view.findViewById(R.id.g);
        mButton7.setText("20:00");
        mSetNotification = view.findViewById(R.id.set_notif);
        mSetNotification.setOnClickListener((View v) -> {
            Toast.makeText(getContext(), "Напоминание создано", Toast.LENGTH_LONG).show();
        });
        //checkButtons();
        return view;
    }

//    private void checkButtons() {
//        if (!mButton1.getText().equals("")// && !mButton2.getText().equals("")
//                && !mButton3.getText().equals("") && !mButton4.getText().equals("")
//                && !mButton5.getText().equals("") && !mButton6.getText().equals("")
//                && !mButton7.getText().equals("")
//                && !mMedicamentTitleEditText.getText().toString().equals("")) {
//            mSetNotification.setEnabled(true);
//        }
//    }
}
