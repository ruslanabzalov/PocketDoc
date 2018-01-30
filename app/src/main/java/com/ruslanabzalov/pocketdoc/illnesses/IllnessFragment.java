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

/**
 * Фрагмент, отвечающий за отображение информации о конкретном заболевании.
 */
public class IllnessFragment extends Fragment {

    /**
     * Ключ аргумента, являющегося идентификатором заболевания.
     */
    private static final String ARG_ILLNESS_ID = "illness_id";

    private Illness mIllness;

    private EditText mIllnessTitleEditText;
    private Button mMedicamentsButton;

    /**
     * Метод, создающий экземпляр фрагмента IllnessFragment, а также задающий его аргументы.
     * @param illnessId идентификатор заболевания
     * @return фрагмент IllnessFragment
     */
    public static IllnessFragment newInstance(UUID illnessId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_ILLNESS_ID, illnessId);
        IllnessFragment fragment = new IllnessFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Болезнь");
        // Извлечение идентификатора заболевания, полученного от активности MainActivity.
        UUID diseaseId = (UUID) getArguments().getSerializable(ARG_ILLNESS_ID);
        // Получение нужного заболевания по его идентификатору.
        mIllness = IllnessesList.get(getActivity()).getDisease(diseaseId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_disease, container, false);
        mIllnessTitleEditText = view.findViewById(R.id.disease_title);
        mIllnessTitleEditText.setText(mIllness.getTitle());
        mIllnessTitleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mIllness.setTitle(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
        mMedicamentsButton = view.findViewById(R.id.drugs_button);
        mMedicamentsButton.setOnClickListener((View v) ->
                startActivity(MedicamentsListActivity.newIntent(getActivity())));
        return view;
    }
}
