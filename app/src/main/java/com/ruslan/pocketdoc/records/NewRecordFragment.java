package com.ruslan.pocketdoc.records;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ruslan.pocketdoc.R;

public class NewRecordFragment extends Fragment implements NewRecordContract.View {

    private static String ARG_DOCTOR_ID = "doctor_id";

    private static final String CALENDAR_DIALOG_FRAGMENT = "session_dialog_fragment";
    private static final int CALENDAR_DIALOG_REQUEST = 1;

    private EditText mUserNameEditText;
    private EditText mUserPhoneEditText;
    private Button mCreateRecordButton;

    private FragmentManager mFragmentManager;

    private NewRecordContract.Presenter mNewRecordPresenter;

    private int mDoctorId;

    public static Fragment newInstance(int doctorId) {
        Bundle args = new Bundle();
        args.putInt(ARG_DOCTOR_ID, doctorId);
        Fragment newRecordFragment = new NewRecordFragment();
        newRecordFragment.setArguments(args);
        return newRecordFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.new_record_title);
        mDoctorId = getArguments().getInt(ARG_DOCTOR_ID, 0);
        mFragmentManager = getActivity().getSupportFragmentManager();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_new_record, container, false);
        initViews(rootView);
        mNewRecordPresenter = new NewRecordPresenter(this);
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initViews(View view) {
        mUserNameEditText = view.findViewById(R.id.user_name_edit_text);
        mUserPhoneEditText = view.findViewById(R.id.user_phone_edit_text);
        mCreateRecordButton = view.findViewById(R.id.create_record_button);
    }
}
