package com.example.quizkids2.main.utils;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.example.quizkids2.R;

public class FragmentNavigator {
    private FragmentManager fragmentManager;
    public FragmentNavigator(FragmentManager parentFragmentManager) {
        this.fragmentManager = parentFragmentManager;
    }

    public void navigateToFragment(Fragment fragment, Transition transition, Bundle bundle){
        navigate(fragment, transition, bundle);
    }

    public void navigateToFragment(Fragment fragment, Transition transition){
       navigate(fragment, transition, null);
    }

    private void navigate(Fragment fragment, Transition transition, Bundle bundle) {
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        if (transition == Transition.ADD) {
            fragmentManager.beginTransaction().add(R.id.fragmentContainerView, fragment).addToBackStack(null).commit();
        } else {
            fragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragment).addToBackStack(null).commit();
        }
    }
}


