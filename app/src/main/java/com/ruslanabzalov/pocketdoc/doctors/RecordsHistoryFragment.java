package com.ruslanabzalov.pocketdoc.doctors;

import android.database.sqlite.SQLiteDatabase;
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

public class RecordsHistoryFragment extends Fragment {

    private SQLiteDatabase mDatabase;
    private RecyclerView mRecordsHistoryRecyclerView;

    private List<Record> mRecords = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(getString(R.string.visits));
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_records_history, container, false);
        mRecordsHistoryRecyclerView = view.findViewById(R.id.records_history_recycler_view);
        mRecordsHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        setupAdapter();
        return view;
    }

    private void setupAdapter() {
        if (isAdded()) {
            mRecordsHistoryRecyclerView.setAdapter(new RecordsAdapter(mRecords));
        }
    }

    private class RecordsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mDocTypeTextView;
        private TextView mUserNameTextView;
        private TextView mRecordDateTextView;

        private Record mRecord;

        private RecordsHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_record, parent, false));
            itemView.setOnClickListener(this);
            mDocTypeTextView = itemView.findViewById(R.id.record_doc_type);
            mUserNameTextView = itemView.findViewById(R.id.record_user_name);
            mRecordDateTextView = itemView.findViewById(R.id.record_date);
        }

        void bind(Record record) {
            mRecord = record;
            mDocTypeTextView.setText(String.format("Специализация врача: %s", mRecord.getDocType()));
            mUserNameTextView.setText(String.format("Имя посетителя: %s", mRecord.getUserName()));
            mRecordDateTextView.setText(String.format("Дата записи: %s", mRecord.getRecordDate()));
        }

        @Override
        public void onClick(View v) {}
    }

    private class RecordsAdapter extends RecyclerView.Adapter<RecordsHolder> {

        private List<Record> mRecords;

        RecordsAdapter(List<Record> records) {
            mRecords = records;
        }

        @Override
        public RecordsHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new RecordsHolder(layoutInflater, viewGroup);
        }

        @Override
        public void onBindViewHolder(RecordsHolder recordsHolder, int position) {
            Record record = mRecords.get(position);
            recordsHolder.bind(record);
        }

        @Override
        public int getItemCount() {
            return mRecords.size();
        }
    }
}
