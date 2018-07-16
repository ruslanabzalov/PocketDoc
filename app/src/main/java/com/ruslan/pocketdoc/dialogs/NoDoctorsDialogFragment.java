package com.ruslan.pocketdoc.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;

import com.ruslan.pocketdoc.R;

public class NoDoctorsDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setMessage(R.string.no_doctors_dialog_message)
                .setPositiveButton(R.string.no_doctors_dialog_positive_button_text, this::backToStationsListUi);
        Dialog noDoctorsDialog = builder.create();
        noDoctorsDialog.setOnKeyListener(this::isBackPressed);
        noDoctorsDialog.setCanceledOnTouchOutside(false);
        return noDoctorsDialog;
    }

    private void backToStationsListUi(DialogInterface dialogInterface, int i) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.popBackStack();
        dialogInterface.dismiss();
    }

    private boolean isBackPressed(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        if (i == KeyEvent.KEYCODE_BACK) {
            // Код почему-то выполняется дважды без проверки на KeyEvent.ACTION_DOWN!
            if (keyEvent.getAction() != KeyEvent.ACTION_DOWN) {
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_CANCELED, null);
                dialogInterface.dismiss();
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
