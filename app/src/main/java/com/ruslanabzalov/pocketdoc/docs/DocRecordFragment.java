package com.ruslanabzalov.pocketdoc.docs;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ruslanabzalov.pocketdoc.R;
import com.ruslanabzalov.pocketdoc.database.DatabaseHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.ruslanabzalov.pocketdoc.database.DatabaseSchema.MedicalRecordsTable;
import static com.ruslanabzalov.pocketdoc.database.DatabaseSchema.MedicalRecordsTable.Cols.DOC_ADDRESS;
import static com.ruslanabzalov.pocketdoc.database.DatabaseSchema.MedicalRecordsTable.Cols.DOC_DESCRIPTION;
import static com.ruslanabzalov.pocketdoc.database.DatabaseSchema.MedicalRecordsTable.Cols.DOC_NAME;
import static com.ruslanabzalov.pocketdoc.database.DatabaseSchema.MedicalRecordsTable.Cols.DOC_TYPE;
import static com.ruslanabzalov.pocketdoc.database.DatabaseSchema.MedicalRecordsTable.Cols.RECORD_DATE;
import static com.ruslanabzalov.pocketdoc.database.DatabaseSchema.MedicalRecordsTable.Cols.USER_NAME;
import static com.ruslanabzalov.pocketdoc.database.DatabaseSchema.MedicalRecordsTable.Cols.USER_PHONE;

public class DocRecordFragment extends Fragment {

    private static final String ARG_DOC = "doc";
    private static final String DIALOG_DATE = "DialogDate";

    private SQLiteDatabase mDatabase;

    private static final int REQUEST_DATE = 0;

    private EditText mUserName;
    private EditText mUserPhoneNumber;
    private Button mUserDate;
    private Button mDocRecord;

    private Doc mDoc;

    /**
     * Статический метод для создания фрагмента типа DocInfoFragment
     * и прикрепления к нему аргументов.
     * @return
     */
    public static DocRecordFragment newInstance(Doc doc) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DOC, doc);
        DocRecordFragment fragment = new DocRecordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Метод
     * @param doc
     * @return
     */
    private static ContentValues getContentValues(Doc doc, String userName, String userPhone,
                                                  String recordDate) {
        ContentValues values = new ContentValues();
        values.put(DOC_NAME, doc.getName());
        values.put(DOC_TYPE, doc.getType());
        values.put(DOC_ADDRESS, doc.getAddress());
        values.put(DOC_DESCRIPTION, doc.getDescription());
        values.put(USER_NAME, userName);
        values.put(USER_PHONE, userPhone);
        values.put(RECORD_DATE, recordDate);
        return values;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase = new DatabaseHelper(getContext()).getWritableDatabase();
        getActivity().setTitle(getString(R.string.medical_record_activity_title));
        mDoc = (Doc) getArguments().getSerializable(ARG_DOC);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doc_record, container, false);
        mUserName = view.findViewById(R.id.user_name);
        mUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkViews();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        mUserPhoneNumber = view.findViewById(R.id.user_phone_number);
        mUserPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkViews();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        mUserDate = view.findViewById(R.id.user_date);
        mUserDate.setOnClickListener((View v) -> {
            FragmentManager manager = getFragmentManager();
            DatePickerFragment dialog = new DatePickerFragment();
            dialog.setTargetFragment(DocRecordFragment.this, REQUEST_DATE);
            dialog.show(manager, DIALOG_DATE);
            checkViews();
        });
        mDocRecord = view.findViewById(R.id.doc_request);
        mDocRecord.setOnClickListener((View v) -> {
            // Рабочий код по формированию заявки на DocDoc.
//            DataFetch.docPostRequest(mUserName.getText().toString(),
//                    mUserPhoneNumber.getText().toString(), mUserDate.getText().toString(),
//                    mDoc.getId(), mDoc.getDocsClinicId());
            Toast.makeText(getContext(),
                    "Заявка на запись успешно создана." + "Ожидайте звонка из клиники.",
                    Toast.LENGTH_LONG).show();
            addToDatabase(mDoc);
            getActivity().finish();
        });
        checkViews();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
            mUserDate.setText(String.format("%s", dateFormat.format(date)));
            checkViews();
        }
    }

    /**
     *
     * @param doc
     */
    private void addToDatabase(Doc doc) {
        ContentValues contentValues = getContentValues(doc, mUserName.getText().toString(),
                mUserPhoneNumber.getText().toString(), mUserDate.getText().toString());
        mDatabase.insert(MedicalRecordsTable.NAME, null, contentValues);
    }

    /**
     * Метод для проверки всех выбранных параметров, формирующих заявку.
     */
    private void checkViews() {
        if (!mUserName.getText().toString().equals("") &&
                !mUserPhoneNumber.getText().toString().equals("") &&
                !mUserDate.getText().equals("")) {
            mDocRecord.setEnabled(true);
        } else {
            mDocRecord.setEnabled(false);
        }
    }
}
