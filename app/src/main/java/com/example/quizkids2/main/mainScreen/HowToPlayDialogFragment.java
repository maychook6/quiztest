package com.example.quizkids2.main.mainScreen;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.quizkids2.R;

public class HowToPlayDialogFragment extends androidx.fragment.app.DialogFragment {


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        String msg = getString(R.string.howToPlayMsg);
        String title = getString(R.string.welcomeMsg);
        String dialogBtn = getString(R.string.gotItDialogBtn);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(msg)
                .setNeutralButton(dialogBtn, (dialogInterface, i) -> dialogInterface.dismiss());
        builder.setTitle(title);
        return builder.create();
    }

}