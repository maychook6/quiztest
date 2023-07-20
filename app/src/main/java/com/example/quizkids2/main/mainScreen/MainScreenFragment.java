package com.example.quizkids2.main.mainScreen;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.quizkids2.R;
import com.example.quizkids2.main.categories.CategoriesFragment;
import com.example.quizkids2.main.scoreboard.ScoreboardFragment;
import com.example.quizkids2.main.utils.FragmentNavigator;
import com.example.quizkids2.main.utils.Transition;
import com.example.quizkids2.objects.User;

import java.util.concurrent.TimeUnit;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainScreenFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_screen, container, false);
        FragmentNavigator fragmentNavigator =  new FragmentNavigator(getParentFragmentManager());

        Button playBtn = view.findViewById(R.id.playBtn);
        Button scoreboardBtn = view.findViewById(R.id.scoreboardBtn);

        playBtn.setOnClickListener(view1 -> {
            fetchData(fragmentNavigator);
        });

        scoreboardBtn.setOnClickListener(view12 -> {
            fragmentNavigator.navigateToFragment(new ScoreboardFragment(), Transition.ADD);
        });

        return view;
    }

    private void fetchData(FragmentNavigator fragmentNavigator) {
        Integer timeToWait = 15;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference docRef = db.collection("users").document(userId);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                long difference;
                DocumentSnapshot document = task.getResult();
                User user = document.toObject(User.class);

                if (!user.isCanPlay()) {
                    difference = calculateTimestampDifference(user.getTimeToPlay(), System.currentTimeMillis());
                    if (difference >= timeToWait) {
                        user.setCanPlay(true);
                        db.collection("users").document(user.getId()).update("canPlay", true);
                        fragmentNavigator.navigateToFragment(new CategoriesFragment(), Transition.ADD);

                    } else {
                        openDialog(timeToWait - difference);
                    }
                } else {
                    fragmentNavigator.navigateToFragment(new CategoriesFragment(), Transition.ADD);

                }
            }
        });
    }

    public static long calculateTimestampDifference(long timestamp1, long timestamp2) {
        long differenceInMillis = Math.abs(timestamp2 - timestamp1);

        return TimeUnit.MILLISECONDS.toMinutes(differenceInMillis);
    }

    public void openDialog(long time) {
        TimeDialogFragment dialog = new TimeDialogFragment(time);
        dialog.show(getParentFragmentManager(), "dialog");
    }

}
