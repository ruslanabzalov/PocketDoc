package com.ruslanabzalov.pocketdoc.doctors;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ruslanabzalov.pocketdoc.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DocsListFragment extends Fragment {

    private static final String TAG = "DocsListFragment";

    private static final String ARG_DOCS_TYPE = "docs_type";
    private static final String ARG_DOCS_METRO = "docs_metro";

    private RecyclerView mDocsRecyclerView;

    private List<Doctor> mDoctors = new ArrayList<>();

    private String mDocsTypeId;
    private String mDocsMetroId;

    public static DocsListFragment newInstance(String docsType, String docsMetro) {
        Bundle args = new Bundle();
        args.putCharSequence(ARG_DOCS_TYPE, docsType);
        args.putCharSequence(ARG_DOCS_METRO, docsMetro);
        DocsListFragment fragment = new DocsListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(getString(R.string.docs_list_activity_title));
        setRetainInstance(true);
        mDocsTypeId = Objects.requireNonNull(getArguments().getCharSequence(ARG_DOCS_TYPE))
                .toString();
        mDocsMetroId = Objects.requireNonNull(getArguments().getCharSequence(ARG_DOCS_METRO))
                .toString();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_docs_list, container, false);
        mDocsRecyclerView = view.findViewById(R.id.docs_list_recycler_view);
        mDocsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        setupAdapter();
        return view;
    }

    private void setupAdapter() {
        if (isAdded()) {
            mDocsRecyclerView.setAdapter(new DocsAdapter(mDoctors));
        }
    }

    private class DocsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mNameTextView;
        private TextView mExperienceTextView;
        private TextView mPriceTextView;
        private TextView mRatingTextView;
        private TextView mAddressTextView;

        private Doctor mDoctor;

        private DocsHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_doc, parent, false));
            itemView.setOnClickListener(this);
            mNameTextView = itemView.findViewById(R.id.doc_name);
            mExperienceTextView = itemView.findViewById(R.id.doc_experience);
            mPriceTextView = itemView.findViewById(R.id.doc_price);
            mRatingTextView = itemView.findViewById(R.id.doc_rating);
            mAddressTextView = itemView.findViewById(R.id.doc_clinic_address);
        }

        public void bind(Doctor doctor) {
            mDoctor = doctor;
            mNameTextView.setText(mDoctor.getName());
//            mExperienceTextView.setText(String.format("Опыт работы: %s лет/года",
//                    mDoctor.getExperience()));
//            mPriceTextView.setText(String.format("Цена одного посещения: %s\u20BD",
//                    mDoctor.getPrice()));
            mRatingTextView.setText(String.format("Рейтинг: %s из 5", mDoctor.getRating()));
//            mAddressTextView.setText(String.format("Адрес клиники: %s", mDoctor.getAddress()));
        }

        @Override
        public void onClick(View v) {
            Intent intent = DocInfoActivity.newIntent(getActivity(), mDoctor);
            startActivity(intent);
        }
    }

    private class DocsAdapter extends RecyclerView.Adapter<DocsHolder> {
        private List<Doctor> mDoctors;

        public DocsAdapter(List<Doctor> doctors) {
            mDoctors = doctors;
        }

        @Override
        public DocsHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new DocsHolder(layoutInflater, viewGroup);
        }

        @Override
        public void onBindViewHolder(DocsHolder docsHolder, int position) {
            Doctor doctor = mDoctors.get(position);
            docsHolder.bind(doctor);
        }

        @Override
        public int getItemCount() {
            return mDoctors.size();
        }
    }
}
