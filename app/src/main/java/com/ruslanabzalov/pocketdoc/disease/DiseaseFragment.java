package com.ruslanabzalov.pocketdoc.disease;

import android.app.Activity;
import android.content.Intent;
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

import com.ruslanabzalov.pocketdoc.R;

import java.util.Date;
import java.util.UUID;

/**
 * Класс-фрагмент, отвечающий за отображение информации о заболевании.
 */
public class DiseaseFragment extends Fragment {

    /**
     * Константа-ключ для доступа к аргументу-заболеванию по идентификатору.
     */
    private static final String ARG_DISEASE_ID = "disease_id";

    /**
     * Метка DiseaseDatePicker.
     */
    private static final String DIALOG_DISEASE_DATE = "DiseaseDialogDate";

    /**
     * Константа-код запроса для получение даты от DiseaseDatePickerFragment.
     */
    private static final int REQUEST_DATE = 0;

    private Disease mDisease;

    private Button mDateButton;
    private CheckBox mCuredCheckBox;
    private EditText mTitleField;

    /**
     * Метод, создающий экземпляр фрагмента DiseaseFragment, а также задающий его аргументы.
     * @param diseaseId
     * @return
     */
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
        updateDate();
        mDateButton.setOnClickListener((View v) -> {
            FragmentManager manager = getFragmentManager();
            DiseaseDatePickerFragment diseaseDateDialog = DiseaseDatePickerFragment
                    .newInstance(mDisease.getDate());
            // Назначение целевого фрагмента
            diseaseDateDialog.setTargetFragment(DiseaseFragment.this, REQUEST_DATE);
            diseaseDateDialog.show(manager, DIALOG_DISEASE_DATE);
        });
        mCuredCheckBox = view.findViewById(R.id.disease_cured);
        mCuredCheckBox.setChecked(mDisease.isCured());
        mCuredCheckBox
                .setOnCheckedChangeListener((CompoundButton compoundButton, boolean isChecked)
                        -> mDisease.setCured(isChecked));
        return view;
    }

    /**
     * Метод, получающий данные от фрагмента DiseaseDatePickerFragment.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data
                    .getSerializableExtra(DiseaseDatePickerFragment.EXTRA_DISEASE_DATE);
            mDisease.setDate(date);
            updateDate();
        }
    }

    /**
     * Метод, обновляющий дату заболевания.
     */
    private void updateDate() {
        mDateButton.setText(mDisease.getDate().toString());
    }
}
