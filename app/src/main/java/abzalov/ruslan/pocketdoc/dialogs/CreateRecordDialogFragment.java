package abzalov.ruslan.pocketdoc.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import abzalov.ruslan.pocketdoc.App;
import abzalov.ruslan.pocketdoc.R;
import abzalov.ruslan.pocketdoc.data.Repository;
import abzalov.ruslan.pocketdoc.data.doctors.Doctor;
import abzalov.ruslan.pocketdoc.data.records.Record;
import abzalov.ruslan.pocketdoc.data.specialities.Speciality;
import abzalov.ruslan.pocketdoc.di.AppComponent;
import abzalov.ruslan.pocketdoc.doctors.OnCreateRecordListener;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import javax.inject.Inject;

public class CreateRecordDialogFragment extends DialogFragment {

    private static final String ARG_CREATE_RECORD_BUTTON_TYPE = "create_record_button_type";
    private static final String ARG_CREATE_RECORD_DOCTOR = "create_record_button_type";
    private static final String ARG_DOCTOR_ID = "doctor_id";
    private static final String ARG_CLINIC_ID = "clinic_id";
    private static final String ARG_SLOT_ID = "slot_id";

    private EditText mUserNameEditText;
    private EditText mUserPhoneEditText;
    private Button mCreateRecordButton;

    private Doctor mDoctor;
    private int mCreateRecordButtonType;
    private String mUserName = "";
    private String mUserPhone = "";

    @Inject
    Repository mRepository;

    public static DialogFragment newInstance(int createRecordButtonType, Doctor doctor) {
        Bundle arguments = new Bundle();
        arguments.putInt(ARG_CREATE_RECORD_BUTTON_TYPE, createRecordButtonType);
        arguments.putSerializable(ARG_CREATE_RECORD_DOCTOR, doctor);
        DialogFragment createRecordDialogFragment = new CreateRecordDialogFragment();
        createRecordDialogFragment.setArguments(arguments);
        return createRecordDialogFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        App.getComponent().inject(this);
        mCreateRecordButtonType =
                Objects.requireNonNull(getArguments()).getInt(ARG_CREATE_RECORD_BUTTON_TYPE);
        mDoctor = (Doctor) Objects.requireNonNull(getArguments()).getSerializable(ARG_CREATE_RECORD_DOCTOR);
        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        View rootView = inflater.inflate(R.layout.dialog_fragment_create_record, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.dialog_fragment_create_record_title)
                .setView(rootView);
        initViews(rootView);
        return builder.create();
    }

    private void initViews(@NonNull View rootView) {
        mUserNameEditText = rootView.findViewById(R.id.user_name_edit_text);
        mUserNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mUserName = charSequence.toString();
                checkCreateRecordButtonEnable();
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
        mUserPhoneEditText = rootView.findViewById(R.id.user_phone_edit_text);
        mUserPhoneEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mUserPhone = charSequence.toString();
                checkCreateRecordButtonEnable();
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
        mCreateRecordButton = rootView.findViewById(R.id.create_record_button);
        mCreateRecordButton.setOnClickListener(view -> createRecord());
        mCreateRecordButton.setEnabled(false);
    }

    private void checkCreateRecordButtonEnable() {
        if (!mUserName.equals("") && !mUserPhone.equals("")) {
            mCreateRecordButton.setEnabled(true);
        } else {
            mCreateRecordButton.setEnabled(false);
        }
    }

    private void createRecord() {
        Record record = new Record(UUID.randomUUID().toString());
        if (mDoctor.getSpecialities().size() > 0) {
            String doctorSpecs = "";
            for (Speciality speciality : mDoctor.getSpecialities()) {
                doctorSpecs += speciality.getName() + ", ";
            }
            record.setDocType(doctorSpecs.substring(0, doctorSpecs.length() - 2));
        }
        record.setDocFullName(mDoctor.getName());
        record.setUserName(mUserName);
        record.setRecordDate(new SimpleDateFormat("dd.MM.YYYY").format(new Date()));

        String recordSaved = "Запись создана";

        if (mCreateRecordButtonType == OnCreateRecordListener.SIMPLE_RECORD_BUTTON) {
            Completable.fromAction(() -> mRepository.saveRecord(record))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe();
            Toast.makeText(getActivity(), recordSaved, Toast.LENGTH_SHORT).show();
            dismiss();
        } else {
            Completable.fromAction(() -> mRepository.saveRecord(record))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe();
            Toast.makeText(getActivity(), recordSaved, Toast.LENGTH_SHORT).show();
            dismiss();
        }
    }
}
