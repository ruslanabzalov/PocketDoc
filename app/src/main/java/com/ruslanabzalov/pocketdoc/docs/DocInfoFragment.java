package com.ruslanabzalov.pocketdoc.docs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ruslanabzalov.pocketdoc.R;

/**
 * Класс, отвечающий за отображение информации о конкретном враче и записи к нему на приём.
 */
public class DocInfoFragment extends Fragment {

    private static final String ARG_DOC = "doc";

    private Doc mDoc;

    /**
     * Статический метод для создания фрагмента типа DocInfoFragment
     * и прикрепления к нему аргументов.
     * @return
     */
    public static DocInfoFragment newInstance(Doc doc) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DOC, doc);
        DocInfoFragment fragment = new DocInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Информация о враче");
        mDoc = (Doc) getArguments().getSerializable(ARG_DOC);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doc_info, container, false);
        TextView docNameTextView = view.findViewById(R.id.doctor_name);
        docNameTextView.setText(mDoc.getName());
        TextView docExperienceTextView = view.findViewById(R.id.doctor_experience);
        docExperienceTextView.setText(String.format("Опыт работы: %s лет/года",
                mDoc.getExperience()));
        TextView docClinicAddressTextView = view.findViewById(R.id.doctor_address);
        docClinicAddressTextView.setText(mDoc.getAddress());
        TextView docDescriptionTextView = view.findViewById(R.id.doctor_description);
        docDescriptionTextView.setText(mDoc.getDescription());
        TextView docPriceTextView = view.findViewById(R.id.doctor_price);
        docPriceTextView.setText(String.format("Стоимость посещения: %s\u20BD", mDoc.getPrice()));
        Button docRequest = view.findViewById(R.id.doc_request);
        docRequest.setOnClickListener((View v) -> {
            Intent intent = DocRecordActivity.newIntent(getActivity(), mDoc);
            startActivity(intent);
        });
        return view;
    }
}
