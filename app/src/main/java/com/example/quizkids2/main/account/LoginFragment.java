package com.example.quizkids2.main.account;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quizkids2.R;
import com.example.quizkids2.main.categories.CategoriesFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment {

    View view;
    FirebaseAuth mAuth;
    EditText inputUsername, inputPassword;
    Button loginBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);
        mAuth = FirebaseAuth.getInstance();
        loginBtn = view.findViewById(R.id.login_btn);
        inputUsername = view.findViewById(R.id.username);
        inputPassword = view.findViewById(R.id.password);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username, password;
                username = inputUsername.getText().toString();
                password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(getActivity(),"Enter username",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getActivity(),"Enter password",Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(username, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast toast = Toast.makeText(getActivity(), "Login successful.", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.TOP, 0, 300);
                                    toast.show();

                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    String nickname = user.getDisplayName();


                                    Fragment fragment = new CategoriesFragment();
                                    FragmentManager fragmentManager = getParentFragmentManager();
                                    fragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragment).commit();

                                } else {
                                    Toast toast = Toast.makeText(getActivity(), "Login failed.", Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.TOP, 0, 300);
                                    toast.show();
                                }
                            }
                        });


            }
        });

        return view;
    }

}