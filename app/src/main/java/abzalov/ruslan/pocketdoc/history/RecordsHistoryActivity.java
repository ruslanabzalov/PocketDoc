package abzalov.ruslan.pocketdoc.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import javax.inject.Inject;

import abzalov.ruslan.pocketdoc.App;
import abzalov.ruslan.pocketdoc.R;
import abzalov.ruslan.pocketdoc.data.Repository;
import abzalov.ruslan.pocketdoc.data.records.Record;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Активность, отображающая испторию записей пользователя.
 */
public class RecordsHistoryActivity extends AppCompatActivity {

    @Inject
    Repository mRepository;

    private Disposable mDisposable;

    private RecyclerView mRecordsHistoryRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        setSupportActionBar(findViewById(R.id.activity_history_toolbar));
        this.getSupportActionBar().setTitle("История записей");
        mRecordsHistoryRecyclerView = findViewById(R.id.records_history_recycler_view);
        App.getComponent().inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dispose(mDisposable);
        mDisposable = mRepository.getRecords()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(this::showRecordsHistory)
                .subscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposable != null) {
            dispose(mDisposable);
        }
    }

    private void setupAdapter(List<Record> records) {
        if (mRecordsHistoryRecyclerView.getAdapter() == null) {
            mRecordsHistoryRecyclerView.setAdapter(new RecordsAdapter(records));
        }
    }

    private void dispose(Disposable disposable) {
        if (disposable != null) {
            disposable.dispose();
        }
    }

    private void showRecordsHistory(List<Record> records) {
        mRecordsHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        setupAdapter(records);
    }

    private class RecordsHolder extends RecyclerView.ViewHolder {

        private TextView mDocTypeTextView;
        private TextView mDocFullName;
        private TextView mUserNameTextView;
        private TextView mRecordDateTextView;

        private Record mRecord;

        private RecordsHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_record, parent, false));
            mDocTypeTextView = itemView.findViewById(R.id.record_doc_type);
            mDocFullName = itemView.findViewById(R.id.record_user_name);
            mUserNameTextView = itemView.findViewById(R.id.record_user_name);
            mRecordDateTextView = itemView.findViewById(R.id.record_date);
        }

        void bind(Record record) {
            mRecord = record;
            mDocTypeTextView.setText(String.format("Специализация врача: %s", mRecord.getDocType()));
            mDocFullName.setText(String.format("ФИО врача: %s", mRecord.getDocFullName()));
            mUserNameTextView.setText(String.format("Имя посетителя: %s", mRecord.getUserName()));
            mRecordDateTextView.setText(String.format("Дата записи: %s", mRecord.getRecordDate()));
        }
    }

    private class RecordsAdapter extends RecyclerView.Adapter<RecordsHolder> {

        private List<Record> mRecords;

        RecordsAdapter(List<Record> records) {
            mRecords = records;
        }

        @Override
        public RecordsHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(RecordsHistoryActivity.this);
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
