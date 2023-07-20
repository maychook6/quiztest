package com.example.quizkids2.main.account;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.quizkids2.R;

public class RegisterDialogFragment extends androidx.fragment.app.DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Registration Successful, please login.")
                .setNeutralButton("Go login", (dialogInterface, i) -> {

                    //TODO use util class FragmentNavigator
                    Fragment fragment = new AccountFragment();
                    FragmentManager fragmentManager = getParentFragmentManager();
                    fragmentManager.beginTransaction().add(R.id.fragmentContainerView, fragment).addToBackStack(null).commit();
                });

        return builder.create();
    }
}