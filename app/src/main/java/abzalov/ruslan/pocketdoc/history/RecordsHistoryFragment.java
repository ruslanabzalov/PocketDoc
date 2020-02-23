package abzalov.ruslan.pocketdoc.history;

public class RecordsHistoryFragment {//extends Fragment {
//
//    private SQLiteDatabase mDatabase;
//    private RecyclerView mRecordsHistoryRecyclerView;
//
//    private List<Record> mRecords = new ArrayList<>();
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        mDatabase = new DatabaseHelper(getContext()).getWritableDatabase();
//        getFromDatabase();
//        getActivity().setTitle(getString(R.string.visits));
//        setRetainInstance(true);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_records_history, container, false);
//        mRecordsHistoryRecyclerView = view.findViewById(R.id.records_history_recycler_view);
//        mRecordsHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        setupAdapter();
//        return view;
//    }
//
//    private void setupAdapter() {
//        if (isAdded()) {
//            mRecordsHistoryRecyclerView.setAdapter(new RecordsAdapter(mRecords));
//        }
//    }
//
//    private RecordCursorWrapper queryRecords(String whereClause, String[] whereArgs) {
//        Cursor cursor = mDatabase.query(
//                DatabaseSchema.MedicalRecordsTable.NAME,
//                null,
//                whereClause,
//                whereArgs,
//                null,
//                null,
//                null
//        );
//        return new RecordCursorWrapper(cursor);
//    }
//
//    private void getFromDatabase() {
//        try (RecordCursorWrapper cursorWrapper = queryRecords(null, null)) {
//            cursorWrapper.moveToFirst();
//            while (!cursorWrapper.isAfterLast()) {
//                mRecords.add(cursorWrapper.getRecord());
//                cursorWrapper.moveToNext();
//            }
//        }
//    }
//
//    private class RecordsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//
//        private TextView mDocTypeTextView;
//        private TextView mUserNameTextView;
//        private TextView mRecordDateTextView;
//
//        private Record mRecord;
//
//        private RecordsHolder(LayoutInflater inflater, ViewGroup parent) {
//            super(inflater.inflate(R.layout.list_item_record, parent, false));
//            itemView.setOnClickListener(this);
//            mDocTypeTextView = itemView.findViewById(R.id.record_doc_type);
//            mUserNameTextView = itemView.findViewById(R.id.record_user_name);
//            mRecordDateTextView = itemView.findViewById(R.id.record_date);
//        }
//
//        void bind(Record record) {
//            mRecord = record;
//            mDocTypeTextView.setText(String.format("Специализация врача: %s", mRecord.getDocType()));
//            mUserNameTextView.setText(String.format("Имя посетителя: %s", mRecord.getUserName()));
//            mRecordDateTextView.setText(String.format("Дата записи: %s", mRecord.getRecordDate()));
//        }
//
//        @Override
//        public void onClick(View v) {}
//    }
//
//    private class RecordsAdapter extends RecyclerView.Adapter<RecordsHolder> {
//
//        private List<Record> mRecords;
//
//        RecordsAdapter(List<Record> records) {
//            mRecords = records;
//        }
//
//        @Override
//        public RecordsHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
//            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
//            return new RecordsHolder(layoutInflater, viewGroup);
//        }
//
//        @Override
//        public void onBindViewHolder(RecordsHolder recordsHolder, int position) {
//            Record record = mRecords.get(position);
//            recordsHolder.bind(record);
//        }
//
//        @Override
//        public int getItemCount() {
//            return mRecords.size();
//        }
//    }
}
