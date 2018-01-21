package com.ruslanabzalov.pocketdoc.disease;

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

/**
 * Фрагмент, отвечающий за отображение информации о конкретном заболевании.
 */
public class DiseaseFragment extends Fragment {

    /**
     * Ключ для доступа к аргументу-заболеванию по идентификатору.
     */
    private static final String ARG_DISEASE_ID = "disease_id";

    private Disease mDisease;

    private EditText mTitleField;
    private Button mDrugsButton;

    /**
     * Метод, создающий экземпляр фрагмента DiseaseFragment, а также задающий его аргументы.
     * @param diseaseId идентификатор заболевания
     * @return фрагмент DiseaseFragment
     */
    public static DiseaseFragment newInstance(UUID diseaseId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DISEASE_ID, diseaseId);
        DiseaseFragment fragment = new DiseaseFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Болезнь");
        // Извлечение идентификатора заболевания, полученного от активности MainActivity.
        UUID diseaseId = (UUID) getArguments().getSerializable(ARG_DISEASE_ID);
        // Получение нужного заболевания по его идентификатору.
        mDisease = DiseasesList.get(getActivity()).getDisease(diseaseId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_disease, container, false);
        mTitleField = view.findViewById(R.id.disease_title);
        mTitleField.setText(mDisease.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mDisease.setTitle(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
        mDrugsButton = view.findViewById(R.id.drugs_button);
        mDrugsButton.setOnClickListener((View v) ->
                startActivity(DrugsListActivity.newIntent(getActivity())));
        return view;
    }
}
