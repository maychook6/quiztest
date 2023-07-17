package com.example.quizkids2.main.account;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegisterFragment extends Fragment {

    View view;
    FirebaseAuth mAuth;
    EditText inputEmail, inputPassword, inputNickname;
    Button registerBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_register, container, false);
        mAuth = FirebaseAuth.getInstance();
        registerBtn = view.findViewById(R.id.register_btn);
        inputEmail = view.findViewById(R.id.username);
        inputPassword = view.findViewById(R.id.password);
        inputNickname = view.findViewById(R.id.nickname);


       registerBtn.setOnClickListener(view -> {

           String email = inputEmail.getText().toString();
           String password = inputPassword.getText().toString();

           if (TextUtils.isEmpty(email)) {
               Toast.makeText(getActivity(),"Enter username",Toast.LENGTH_SHORT).show();
               return;
           }

           if (TextUtils.isEmpty(password)) {
               Toast.makeText(getActivity(),"Enter password",Toast.LENGTH_SHORT).show();
               return;
           }

           mAuth.createUserWithEmailAndPassword(email, password)
                   .addOnCompleteListener(task -> {
                       if (task.isSuccessful()) {
                           Toast toast = Toast.makeText(getActivity(), "Account created", Toast.LENGTH_SHORT);
                           toast.setGravity(Gravity.TOP, 0, 300);
                           toast.show();

                           openDialog();

                           Fragment fragment = new AccountFragment();
                           FragmentManager fragmentManager = getParentFragmentManager();
                           fragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragment).commit();
                           updateUserProfile();
                       } else {
                           Toast toast = Toast.makeText(getActivity(), "Registration failed", Toast.LENGTH_LONG);
                           toast.setGravity(Gravity.TOP, 0, 300);
                           toast.show();

                       }
                   });
       });

        return view;
    }

    private void updateUserProfile() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> userdata = new HashMap<>();
        userdata.put("email", user.getEmail());
        userdata.put("nickname", inputNickname.getText().toString());
        userdata.put("score", 0);
        userdata.put("QuestionsCorrectIds", new ArrayList<String>());

        db.collection("users").document(user.getUid())
                .set(userdata);

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("userId", user.getUid());
        editor.apply();

    }

    public void openDialog() {
        DialogFragment dialog = new DialogFragment();
        dialog.show(getParentFragmentManager(), "dialog");
    }
}