package com.ruslanabzalov.pocketdoc.docs;

import android.database.Cursor;
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
import com.ruslanabzalov.pocketdoc.database.DatabaseHelper;
import com.ruslanabzalov.pocketdoc.database.DatabaseSchema;
import com.ruslanabzalov.pocketdoc.database.RecordCursorWrapper;

import java.util.ArrayList;
import java.util.List;

public class RecordsHistoryFragment extends Fragment {

    private SQLiteDatabase mDatabase;
    private RecyclerView mRecordsHistoryRecyclerView;

    private List<Record> mRecords = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase = new DatabaseHelper(getContext()).getWritableDatabase();
        getFromDatabase();
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

    /**
     *
     * @param whereClause
     * @param whereArgs
     * @return
     */
    private RecordCursorWrapper queryRecords(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                DatabaseSchema.MedicalRecordsTable.NAME,
                null, // null - выбор всех столбцов
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new RecordCursorWrapper(cursor);
    }

    /**
     * Метод для чтения информации и записях из базы данных.
     */
    private void getFromDatabase() {
        try (RecordCursorWrapper cursorWrapper = queryRecords(null, null)) {
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()) {
                mRecords.add(cursorWrapper.getRecord());
                cursorWrapper.moveToNext();
            }
        }
    }

    private class RecordsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mDocNameTextView;
        private TextView mDocTypeTextView;
        private TextView mDocAddressTextView;
        private TextView mUserNameTextView;
        private TextView mUserPhoneTextView;
        private TextView mRecordDateTextView;

        private Record mRecord;

        private RecordsHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_record, parent, false));
            itemView.setOnClickListener(this);
            mDocNameTextView = itemView.findViewById(R.id.record_doc_name);
            mDocTypeTextView = itemView.findViewById(R.id.record_doc_type);
            mDocAddressTextView = itemView.findViewById(R.id.record_doc_address);
            mUserNameTextView = itemView.findViewById(R.id.record_user_name);
            mUserPhoneTextView = itemView.findViewById(R.id.record_user_phone);
            mRecordDateTextView = itemView.findViewById(R.id.record_date);
        }

        void bind(Record record) {
            mRecord = record;
            mDocNameTextView.setText(mRecord.getDocName());
            mDocTypeTextView.setText(mRecord.getDocType());
            mDocAddressTextView.setText(mRecord.getDocAddress());
            mUserNameTextView.setText(mRecord.getUserName());
            mUserPhoneTextView.setText(mRecord.getUserPhone());
            mRecordDateTextView.setText(mRecord.getRecordDate());
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
