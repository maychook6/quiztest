package com.example.quizkids2.main.account;
import com.example.quizkids2.R;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quizkids2.main.mainScreen.MainScreenFragment;
import com.example.quizkids2.main.utils.FragmentNavigator;
import com.example.quizkids2.main.utils.Transition;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        Button loginBtn = view.findViewById(R.id.loginBtn);
        EditText username = view.findViewById(R.id.username);
        EditText password = view.findViewById(R.id.password);

        loginBtn.setOnClickListener(v -> {
            login(mAuth, username, password);
        });

        return view;
    }

    private void login(FirebaseAuth mAuth, EditText username, EditText password) {
        String usernameStr = username.getText().toString();
        String passwordStr = password.getText().toString();
        String enterUsername = getString(R.string.enterUsername);
        String enterPassword = getString(R.string.enterPassword);
        String loginSuccess = getString(R.string.loginSuccess);
        String loginFail = getString(R.string.loginFail);

        if (TextUtils.isEmpty(usernameStr)) {
            Toast.makeText(getActivity(),enterUsername,Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(passwordStr)) {
            Toast.makeText(getActivity(),enterPassword,Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(usernameStr, passwordStr)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        showToast(loginSuccess, Toast.LENGTH_SHORT);
                        new FragmentNavigator(getParentFragmentManager()).navigateToFragment(new MainScreenFragment(), Transition.REPLACE, true);
                    } else {
                        showToast(loginFail, Toast.LENGTH_LONG);
                    }
                });
    }

    private void showToast(String text, int lengthShort) {
        Toast toast = Toast.makeText(getActivity(), text, lengthShort);
        toast.setGravity(Gravity.TOP, 0, 300);
        toast.show();
    }
}