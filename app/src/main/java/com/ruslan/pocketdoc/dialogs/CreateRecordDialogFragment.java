package com.ruslan.pocketdoc.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.ruslan.pocketdoc.R;

import java.util.Objects;

public class CreateRecordDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        View rootView = inflater.inflate(R.layout.dialog_fragment_create_record, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.dialog_fragment_create_record_title)
                .setView(rootView);
        Dialog createRecordDialog = builder.create();
        createRecordDialog.setCanceledOnTouchOutside(false);
        return createRecordDialog;
    }
}
