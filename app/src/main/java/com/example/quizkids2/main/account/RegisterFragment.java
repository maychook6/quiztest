package com.example.quizkids2.main.account;
import com.example.quizkids2.R;
import com.example.quizkids2.main.utils.FragmentNavigator;
import com.example.quizkids2.main.utils.Transition;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegisterFragment extends Fragment {

    private FirebaseAuth mAuth;
    private EditText inputEmail, inputPassword, inputNickname;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        mAuth = FirebaseAuth.getInstance();
        Button registerBtn = view.findViewById(R.id.register_btn);
        inputEmail = view.findViewById(R.id.username);
        inputPassword = view.findViewById(R.id.password);
        inputNickname = view.findViewById(R.id.nickname);

        register(registerBtn);

        return view;
    }

    private void register(Button registerBtn) {
        registerBtn.setOnClickListener(v -> {
            String email = inputEmail.getText().toString();
            String password = inputPassword.getText().toString();
            String enterUsername = getString(R.string.enterUsername);
            String enterPassword = getString(R.string.enterPassword);
            String registerSuccess = getString(R.string.registerSuccess);
            String registerFail = getString(R.string.registerFail);

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(getActivity(),enterUsername,Toast.LENGTH_SHORT).show();
                return;
            }

            if (password.length() < 6) {
                Toast.makeText(getActivity(),enterPassword,Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            showToast(registerSuccess);

                            openDialog();

                            updateUserProfile();
                        } else {
                            showToast(registerFail);
                        }
                    });
        });
    }

    private void showToast(String registerSuccess) {
        Toast toast = Toast.makeText(getActivity(), registerSuccess, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 300);
        toast.show();
    }

    private void updateUserProfile() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> userdata = new HashMap<>();
        userdata.put("email", user.getEmail());
        userdata.put("nickname", inputNickname.getText().toString());
        userdata.put("score", 0);
        userdata.put("questionsCorrectIds", new ArrayList<String>());
        userdata.put("timeToPlay", 0);
        userdata.put("canPlay", true);

        db.collection("users").document(user.getUid())
                .set(userdata);
    }

    public void openDialog() {
        RegisterDialogFragment dialog = new RegisterDialogFragment();
        dialog.show(getParentFragmentManager(), "dialog");
    }
}