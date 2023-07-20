package com.example.quizkids2.main.categories;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CategoriesDialogFragment extends androidx.fragment.app.DialogFragment {
    //TODO make final
    private String dialogMessage;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(dialogMessage)
                .setNeutralButton("OK", new DialogInterface.OnClickListener() { //TODO use lamda expression
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

        return builder.create();
    }

    public CategoriesDialogFragment(String dialogMessage) {
        this.dialogMessage = dialogMessage;
    }
}