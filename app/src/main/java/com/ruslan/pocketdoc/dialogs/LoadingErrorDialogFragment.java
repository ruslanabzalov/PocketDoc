package com.ruslan.pocketdoc.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;

import com.ruslan.pocketdoc.R;

import java.util.Objects;

public class LoadingErrorDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        View loadingErrorDialogView =
                inflater.inflate(R.layout.dialog_fragment_loading_error, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(loadingErrorDialogView)
                .setTitle(R.string.loading_error_title)
                .setPositiveButton(R.string.loading_error_positive_button_text,
                        (dialogInterface, i) -> loadAgain())
                .setNegativeButton(R.string.loading_error_negative_button_text,
                        (dialogInterface, i) -> closeDialog());

        Dialog loadingErrorDialog = builder.create();
        loadingErrorDialog.setCanceledOnTouchOutside(false);
        loadingErrorDialog
                .setOnKeyListener((dialogInterface, i, keyEvent) -> isBackPressed(i, keyEvent));
        return loadingErrorDialog;
    }

    private void loadAgain() {
        dismiss();
        Objects.requireNonNull(getTargetFragment())
                .onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, null);
    }

    private void closeDialog() {
        dismiss();
        Objects.requireNonNull(getTargetFragment())
                .onActivityResult(getTargetRequestCode(), Activity.RESULT_CANCELED, null);
    }

    private boolean isBackPressed(int i, KeyEvent keyEvent) {
        if (i == KeyEvent.KEYCODE_BACK) {
            // Код почему-то выполняется дважды без проверки на KeyEvent.ACTION_DOWN!
            if (keyEvent.getAction() != KeyEvent.ACTION_DOWN) {
                dismiss();
                Objects.requireNonNull(getTargetFragment())
                        .onActivityResult(getTargetRequestCode(), Activity.RESULT_CANCELED, null);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
