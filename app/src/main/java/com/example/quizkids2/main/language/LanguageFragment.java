package com.example.quizkids2.main.language;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.quizkids2.R;
import com.example.quizkids2.main.account.AccountFragment;
import com.example.quizkids2.main.utils.FragmentNavigator;
import com.example.quizkids2.main.utils.Transition;

import java.util.Locale;


public class LanguageFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_language, container, false);
        Button english = view.findViewById(R.id.english);
        Button hebrew = view.findViewById(R.id.hebrew);
        FragmentNavigator fragmentNavigator =  new FragmentNavigator(getParentFragmentManager());

        english.setOnClickListener(v -> {
            changeLanguage("en", fragmentNavigator);
        });

        hebrew.setOnClickListener(v -> {
            changeLanguage("iw", fragmentNavigator);
        });

        return view;
    }

    private void changeLanguage(String language, FragmentNavigator fragmentNavigator) {
        LocaleListCompat appLocale = LocaleListCompat.forLanguageTags(language);
        AppCompatDelegate.setApplicationLocales(appLocale);
        Locale.setDefault(new Locale(language));

        fragmentNavigator.navigateToFragment(new AccountFragment(), Transition.REPLACE, false);
    }

}