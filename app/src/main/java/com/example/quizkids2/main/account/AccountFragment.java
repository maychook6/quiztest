package com.example.quizkids2.main.account;
import com.example.quizkids2.R;
import com.example.quizkids2.main.language.LanguageFragment;
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
        Button loginBtn = view.findViewById(R.id.loginBtn);
        Button registerBtn = view.findViewById(R.id.registerBtn);
        Button changeLanguage = view.findViewById(R.id.changeLanguage);
        FragmentNavigator fragmentNavigator =  new FragmentNavigator(getParentFragmentManager());

        loginBtn.setOnClickListener(v -> {
            fragmentNavigator.navigateToFragment(new LoginFragment(), Transition.REPLACE, true);
        });

        registerBtn.setOnClickListener(v -> {
            fragmentNavigator.navigateToFragment(new RegisterFragment(), Transition.REPLACE, true);
        });

        changeLanguage.setOnClickListener(v -> {
            fragmentNavigator.navigateToFragment(new LanguageFragment(), Transition.REPLACE, false);
        });

        return view;
    }
}