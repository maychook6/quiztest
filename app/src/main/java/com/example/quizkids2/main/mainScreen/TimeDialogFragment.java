package com.example.quizkids2.main.mainScreen;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.quizkids2.R;

public class TimeDialogFragment extends androidx.fragment.app.DialogFragment {

    private final long time;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        String notYetMsg = getString(R.string.notYet);
        String minutesMsg = getString(R.string.minutesMsg);
        String dialogBtn = getString(R.string.closeDialogBtn);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(notYetMsg + " " + time + " " + minutesMsg)
                .setNeutralButton(dialogBtn, (dialogInterface, i) -> dialogInterface.dismiss());

        return builder.create();
    }

    public TimeDialogFragment(long time) {
        this.time = time;
    }

}