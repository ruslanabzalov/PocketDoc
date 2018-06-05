package com.ruslan.pocketdoc.searching.records;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.ruslan.pocketdoc.R;

public class SessionDateDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        View datePickerView =
                getActivity().getLayoutInflater().inflate(R.layout.dialog_date_picker, null);
        return new AlertDialog.Builder(getActivity())
                .setMessage("Выберите дату приёма")
                .setView(datePickerView)
                .setPositiveButton(R.string.ok, null)
                .setNegativeButton(R.string.cancel, null)
                .create();
    }
}
