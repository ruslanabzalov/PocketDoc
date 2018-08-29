package com.ruslan.pocketdoc.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ruslan.pocketdoc.R;
import com.ruslan.pocketdoc.doctors.OnCreateRecordListener;

import java.util.Objects;

/**
 * Класс, описывающий DialogFragment, позволяющий создавать заявку на запись к врачу.
 */
public class CreateRecordDialogFragment extends DialogFragment {

    private static final String ARG_CREATE_RECORD_BUTTON_TYPE = "create_record_button_type";

    private EditText mUserNameEditText;
    private EditText mUserPhoneEditText;
    private Button mCreateRecordButton;

    private int mCreateRecordButtonType;
    private String mUserName = "";
    private String mUserPhone = "";

    public static DialogFragment newInstance(int createRecordButtonType) {
        Bundle arguments = new Bundle();
        arguments.putInt(ARG_CREATE_RECORD_BUTTON_TYPE, createRecordButtonType);
        DialogFragment createRecordDialogFragment = new CreateRecordDialogFragment();
        createRecordDialogFragment.setArguments(arguments);
        return createRecordDialogFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mCreateRecordButtonType =
                Objects.requireNonNull(getArguments()).getInt(ARG_CREATE_RECORD_BUTTON_TYPE);
        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        View rootView = inflater.inflate(R.layout.dialog_fragment_create_record, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.dialog_fragment_create_record_title)
                .setView(rootView);
        initViews(rootView);
        return builder.create();
    }

    /**
     * Метод инициализации элементов типа <code>View</code>.
     * @param rootView Корневой элемент типа <code>View</code>.
     */
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

    /**
     * Метод включения кнопки формирования заявки в зависимости от заполненных данных пользователя.
     */
    private void checkCreateRecordButtonEnable() {
        if (!mUserName.equals("") && !mUserPhone.equals("")) {
            mCreateRecordButton.setEnabled(true);
        } else {
            mCreateRecordButton.setEnabled(false);
        }
    }

    /**
     * Метод формирования заявки.
     */
    private void createRecord() {
        if (mCreateRecordButtonType == OnCreateRecordListener.SIMPLE_RECORD_BUTTON) {
            // TODO: Создать POST-запрос для записи не по расписанию.
        } else {
            // TODO: Передать сюда идентификатор расписания.
            // TODO: Создать POST-запрос для записи по расписанию.
        }
    }
}
