package com.example.quizkids2.main.categories;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.quizkids2.R;
import com.example.quizkids2.main.account.AccountFragment;

public class CategoriesDialogFragment extends androidx.fragment.app.DialogFragment {

    private String dialogMessage;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(dialogMessage)
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
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