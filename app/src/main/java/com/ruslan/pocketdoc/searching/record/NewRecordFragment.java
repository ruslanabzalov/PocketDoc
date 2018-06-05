package com.ruslan.pocketdoc.searching.record;

import android.os.Bundle;
import android.support.annotation.NonNull;
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

    private EditText mUserNameEditText;
    private EditText mUserPhoneEditText;
    private Button mShowCalendarButton;

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
        if (getArguments() != null) {
            mDoctorId = getArguments().getInt(ARG_DOCTOR_ID, 0);
        }
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
        mNewRecordPresenter.onDestroy();
    }

    @Override
    public void showCalendar() {
        if (getActivity() != null) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            mNewRecordPresenter.onCalendarButtonClick(fragmentManager);
        }
    }

    private void initViews(View view) {
        mUserNameEditText = view.findViewById(R.id.user_name_edit_text);
        mUserPhoneEditText = view.findViewById(R.id.user_phone_edit_text);
        mShowCalendarButton = view.findViewById(R.id.session_date_button);
        mShowCalendarButton.setOnClickListener((v) -> showCalendar());
    }
}
