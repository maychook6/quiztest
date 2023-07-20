package com.example.quizkids2.main.questions;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.quizkids2.R;
//TODO remove unused imports
import com.example.quizkids2.main.account.AccountFragment;
import com.example.quizkids2.main.mainScreen.MainScreenFragment;

public class QuestionDialogFragment extends androidx.fragment.app.DialogFragment {

    //TODO make final
    private String dialogMessage;
    //TODO not in use!
    private String buttonMessage;
    //TODO make final
    private Fragment fragment;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(dialogMessage)
                .setNeutralButton("Go to main screen", new DialogInterface.OnClickListener() { //TODO use lamda
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FragmentManager fragmentManager = getParentFragmentManager();
                        fragmentManager.beginTransaction().add(R.id.fragmentContainerView, fragment).addToBackStack(null).commit();
                    }
        });

        return builder.create();
    }

    public QuestionDialogFragment(String dialogMessage, String buttonMessage, Fragment fragment){
        this.dialogMessage = dialogMessage;
        this.buttonMessage = buttonMessage;
        this.fragment = fragment;
    }
}