package com.ruslan.pocketdoc.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;

import com.ruslan.pocketdoc.R;

public class LoadingErrorDialogFragment extends DialogFragment {

    private Dialog mLoadingErrorDialog;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View loadingErrorDialogView = inflater.inflate(R.layout.dialog_fragment_loading_error, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(loadingErrorDialogView)
                .setTitle(R.string.loading_error_title)
                .setNeutralButton(R.string.loading_error_neutral_button_text, this::loadAgain)
                .setNegativeButton(R.string.loading_error_negative_button_text, this::closeDialog);
        mLoadingErrorDialog = builder.create();
        mLoadingErrorDialog.setCanceledOnTouchOutside(false);
        return mLoadingErrorDialog;
    }

    @Override
    public void onResume() {
        super.onResume();
        mLoadingErrorDialog.setOnKeyListener((this::isBackPressed));
    }

    private void loadAgain(DialogInterface dialogInterface, int i) {
        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, null);
    }

    private void closeDialog(DialogInterface dialogInterface, int i) {
        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_CANCELED, null);
    }

    private boolean isBackPressed(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        if (i == KeyEvent.KEYCODE_BACK) {
            // Код выполняется дважды без проверки на KeyEvent.ACTION_DOWN!
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
