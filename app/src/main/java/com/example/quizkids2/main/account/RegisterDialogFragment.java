package com.example.quizkids2.main.account;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.quizkids2.R;
import com.example.quizkids2.main.utils.FragmentNavigator;
import com.example.quizkids2.main.utils.Transition;

public class RegisterDialogFragment extends androidx.fragment.app.DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        String dialogMsg = getString(R.string.registerDialogMsd);
        String dialogBtnMsg = getString(R.string.goLogin);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(dialogMsg)
                .setNeutralButton(dialogBtnMsg, (dialogInterface, i) -> {
                    new FragmentNavigator(getParentFragmentManager()).navigateToFragment(new AccountFragment(), Transition.REPLACE, false);
                });

        return builder.create();
    }
}