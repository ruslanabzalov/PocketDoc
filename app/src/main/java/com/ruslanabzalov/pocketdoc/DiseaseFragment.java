package com.ruslanabzalov.pocketdoc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.UUID;

/**
 * Класс-фрагмент, отвечающий за отображение информации о заболевании.
 * */
public class DiseaseFragment extends Fragment {

    /**
     * Константа-ключ для доступа к аргументу-заболеванию по идентификатору.
     * */
    private static final String ARG_DISEASE_ID = "disease_id";
    /**
     * Метка DiseaseDatePicker.
     * */
    private static final String DIALOG_DISEASE_DATE = "DiseaseDialogDate";

    private Disease mDisease;

    private Button mDateButton;
    private CheckBox mCuredCheckBox;
    private EditText mTitleField;

    /**
     * Метод, создающий экземпляр фрагмента DiseaseFragment,
     * а также упаковывающий и задающий его аргументы.
     * */
    public static DiseaseFragment newInstance(UUID diseaseId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DISEASE_ID, diseaseId); // Упаковка аргумента
        DiseaseFragment fragment = new DiseaseFragment();
        fragment.setArguments(args); // Присоединение аргумента фрагменту
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Извлечение ID заболевания
        UUID diseaseId = (UUID) getArguments().getSerializable(ARG_DISEASE_ID);
        // Получение нужного экземпляра класса Disease по ID
        mDisease = DiseasesList.get(getActivity()).getDisease(diseaseId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_disease, container, false);
        mTitleField = view.findViewById(R.id.disease_title);
        mTitleField.setText(mDisease.getTitle());
        // Добавление названия заболевания
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence sequence, int start, int count, int after) {
                // Пустая реализация
            }

            @Override
            public void onTextChanged(CharSequence sequence, int start, int before, int count) {
                mDisease.setTitle(sequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Пустая реализация
            }
        });
        mDateButton = view.findViewById(R.id.disease_date);
        mDateButton.setText(mDisease.getDate().toString());
        mDateButton.setOnClickListener((View v) -> {
            FragmentManager manager = getFragmentManager();
            DiseaseDatePickerFragment diseaseDialog = DiseaseDatePickerFragment
                    .newInstance(mDisease.getDate());
            diseaseDialog.show(manager, DIALOG_DISEASE_DATE);
        });
        mCuredCheckBox = view.findViewById(R.id.disease_cured);
        mCuredCheckBox.setChecked(mDisease.isCured());
        mCuredCheckBox
                .setOnCheckedChangeListener((CompoundButton compoundButton, boolean isChecked)
                        -> mDisease.setCured(isChecked));
        return view;
    }
}
