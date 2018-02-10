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

import com.ruslanabzalov.pocketdoc.R;

import java.util.UUID;

public class MedicamentFragment extends Fragment {

    private static final String ARG_DRUG_ID = "drug_id";

    private Medicament mMedicament;

    private EditText mMedicamentTitleEditText;
    private Button mUseButton;
    private Button mDurationInDaysButton;
    private Button mDosageButton;
    private Button mHourlyGapsButton;
    private Button mFirstReceptionButton;
    private Button mSecondReceptionButton;
    private Button mSetNotificationButton;

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
        mUseButton = view.findViewById(R.id.use_button);
        mDurationInDaysButton = view.findViewById(R.id.duration_in_days_button);
        mDosageButton = view.findViewById(R.id.dosage_button);
        mHourlyGapsButton = view.findViewById(R.id.hourly_gaps_button);
        mFirstReceptionButton = view.findViewById(R.id.first_reception_button);
        mSecondReceptionButton = view.findViewById(R.id.second_reception_button);
        mSetNotificationButton = view.findViewById(R.id.set_notification_button);
        checkButtons();
        return view;
    }

    /**
     * Метод для проверки выбора всех параметров создания напоминаний.
     */
    private void checkButtons() {
        if (!mUseButton.getText().equals("")
                && !mDurationInDaysButton.getText().equals("") && !mDosageButton.getText().equals("")
                && !mHourlyGapsButton.getText().equals("") && !mFirstReceptionButton.getText().equals("")
                && !mSecondReceptionButton.getText().equals("")
                && !mMedicamentTitleEditText.getText().toString().equals("")) {
            mSetNotificationButton.setEnabled(true);
        }
    }
}
