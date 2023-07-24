package com.example.quizkids2.main.utils;

import android.os.Bundle;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.quizkids2.R;


public class FragmentNavigator {
    private FragmentManager fragmentManager;

    public FragmentNavigator(FragmentManager parentFragmentManager) {
        this.fragmentManager = parentFragmentManager;
    }

    public void navigateToFragment(Fragment fragment, Transition transition, boolean addToBackStack, Bundle bundle) {
        navigate(fragment, transition, addToBackStack, bundle);
    }

    public void navigateToFragment(Fragment fragment, Transition transition, boolean addToBackStack) {
        navigate(fragment, transition, addToBackStack, null);
    }

    private void navigate(Fragment fragment, Transition transition, boolean addToBackStack, Bundle bundle) {
        if (bundle != null) {
            fragment.setArguments(bundle);
        }

        if (transition == Transition.ADD) {
            if (addToBackStack) {
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_right,
                        R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right).add(R.id.fragmentContainerView, fragment).addToBackStack(null).commit();
                return;
            }
            fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_right,
                    R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right).add(R.id.fragmentContainerView, fragment).commit();
        } else {
            if (addToBackStack) {
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_right,
                        R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right).replace(R.id.fragmentContainerView, fragment).addToBackStack(null).commit();
                return;
            }
            fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in_right,
                    R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right).replace(R.id.fragmentContainerView, fragment).commit();
        }
        return;
    }
}


