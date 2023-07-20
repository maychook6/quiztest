package com.example.quizkids2.main.categories;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CategoriesDialogFragment extends androidx.fragment.app.DialogFragment {
    private final String dialogMessage;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(dialogMessage)
                .setNeutralButton("OK", (dialogInterface, i) -> dialogInterface.dismiss());

        return builder.create();
    }

    public CategoriesDialogFragment(String dialogMessage) {
        this.dialogMessage = dialogMessage;
    }
}