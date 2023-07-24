package com.example.quizkids2.main.categories;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.quizkids2.R;

public class CategoriesDialogFragment extends androidx.fragment.app.DialogFragment {
    private final String dialogMessage;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        String dialogBtnMsg = getString(R.string.okDialogBtn);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(dialogMessage)
                .setNeutralButton(dialogBtnMsg, (dialogInterface, i) -> dialogInterface.dismiss());

        return builder.create();
    }

    public CategoriesDialogFragment(String dialogMessage) {
        this.dialogMessage = dialogMessage;
    }
}