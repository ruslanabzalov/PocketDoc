package com.ruslan.pocketdoc.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;

import com.ruslan.pocketdoc.R;

public class NoDoctorsDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setMessage(R.string.no_doctors_dialog_message)
                .setPositiveButton(R.string.no_doctors_dialog_positive_button_text, this::backToStationsListUi);
        Dialog noDoctorsDialog = builder.create();
        noDoctorsDialog.setCanceledOnTouchOutside(false);
        return noDoctorsDialog;
    }

    private void backToStationsListUi(DialogInterface dialogInterface, int i) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.popBackStack();
        dialogInterface.dismiss();
    }
}
