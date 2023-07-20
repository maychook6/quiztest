package com.example.quizkids2.main.account;
import com.example.quizkids2.R;

import android.os.Bundle;
import androidx.annotation.NonNull;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginFragment extends Fragment {
    private FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        Button loginBtn = view.findViewById(R.id.login_btn);
        EditText username = view.findViewById(R.id.username);
        EditText password = view.findViewById(R.id.password);

        loginBtn.setOnClickListener(v -> {

            String usernameStr, passwordStr;
            usernameStr = username.getText().toString();
            passwordStr = password.getText().toString();

            if (TextUtils.isEmpty(usernameStr)) {
                Toast.makeText(getActivity(),"Enter username",Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(passwordStr)) {
                Toast.makeText(getActivity(),"Enter password",Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.signInWithEmailAndPassword(usernameStr, passwordStr)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast toast = Toast.makeText(getActivity(), "Login successful.", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP, 0, 300);
                                toast.show();

                                new FragmentNavigator(getParentFragmentManager()).navigateToFragment(new MainScreenFragment(), Transition.ADD);
                            } else {
                                Toast toast = Toast.makeText(getActivity(), "Login failed.", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.TOP, 0, 300);
                                toast.show();
                            }
                        }
                    });
        });

        return view;
    }
}