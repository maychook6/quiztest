package com.example.quizkids2.main.mainScreen;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TimeDialogFragment extends androidx.fragment.app.DialogFragment {

    private long time;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Not yet!\nYou have " + time + " minutes to go!")
                .setNeutralButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
        });

        return builder.create();
    }

    public TimeDialogFragment(long time) {
        this.time = time;
    }

}