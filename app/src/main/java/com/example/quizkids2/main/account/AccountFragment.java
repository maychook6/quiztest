package com.example.quizkids2.main.account;
import com.example.quizkids2.R;
import com.example.quizkids2.main.utils.FragmentNavigator;
import com.example.quizkids2.main.utils.Transition;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class AccountFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        Button loginBtnMain = view.findViewById(R.id.login_btn_main);
        Button registerBtnMain = view.findViewById(R.id.register_btn_main);
        FragmentNavigator fragmentNavigator =  new FragmentNavigator(getParentFragmentManager());

        loginBtnMain.setOnClickListener(v -> {
            fragmentNavigator.navigateToFragment(new LoginFragment(), Transition.ADD);
        });

        registerBtnMain.setOnClickListener(v -> {
            fragmentNavigator.navigateToFragment(new RegisterFragment(), Transition.ADD);
        });

        return view;
    }

}