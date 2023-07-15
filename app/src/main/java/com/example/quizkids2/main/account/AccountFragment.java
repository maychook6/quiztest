package com.example.quizkids2.main.account;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.quizkids2.R;
import com.example.quizkids2.main.categories.CategoriesFragment;
import com.google.firebase.auth.FirebaseAuth;

public class AccountFragment extends Fragment {
    View view;
    Button loginBtnMain, registerBtnMain;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_account, container, false);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        loginBtnMain = view.findViewById(R.id.login_btn_main);
        registerBtnMain = view.findViewById(R.id.register_btn_main);

        loginBtnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new LoginFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragment).commit();
            }
        });

        registerBtnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new RegisterFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragment).commit();
            }
        });


        return view;
    }
}