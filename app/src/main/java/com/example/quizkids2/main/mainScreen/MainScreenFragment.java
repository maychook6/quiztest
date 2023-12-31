package com.example.quizkids2.main.mainScreen;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
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
        Button howToPlayBtn = view.findViewById(R.id.howToPlay);
        TextView mainTitle = view.findViewById(R.id.mainTitle);
        ImageView questionMark1 = view.findViewById(R.id.questionMark1);
        ImageView questionMark2 = view.findViewById(R.id.questionMark2);

        animateView(mainTitle, 300, Techniques.FadeIn, 1500);
        animateView(questionMark1, 800, Techniques.FadeIn, 5000);
        animateView(questionMark2, 1500, Techniques.FadeIn, 5000);

        playBtn.setOnClickListener(v-> {
            fetchUser(fragmentNavigator);
        });

        scoreboardBtn.setOnClickListener(v -> {
            fragmentNavigator.navigateToFragment(new ScoreboardFragment(), Transition.REPLACE, true);
        });

        howToPlayBtn.setOnClickListener(v -> {
            openHowToDialog();
        });

        return view;
    }

    private void animateView(View view, long delayMillis, Techniques technique, long duration) {
        view.setVisibility(View.INVISIBLE);
        view.postDelayed(() -> view.setVisibility(View.VISIBLE), delayMillis);
        YoYo.with(technique)
                .duration(duration)
                .playOn(view);
    }

    private void fetchUser(FragmentNavigator fragmentNavigator) {
        int timeToWait = 15;
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
                        fragmentNavigator.navigateToFragment(new CategoriesFragment(), Transition.REPLACE, true);

                    } else {
                        openTimeDialog(timeToWait - difference);
                    }
                } else {
                    fragmentNavigator.navigateToFragment(new CategoriesFragment(), Transition.REPLACE, true);
                }
            }
        });
    }

    public static long calculateTimestampDifference(long timestamp1, long timestamp2) {
        long differenceInMillis = Math.abs(timestamp2 - timestamp1);
        return TimeUnit.MILLISECONDS.toMinutes(differenceInMillis);
    }

    public void openTimeDialog(long time) {
        TimeDialogFragment dialog = new TimeDialogFragment(time);
        dialog.show(getParentFragmentManager(), "dialog");
    }

    public void openHowToDialog() {
        HowToPlayDialogFragment dialog = new HowToPlayDialogFragment();
        dialog.show(getParentFragmentManager(), "dialog");
    }
}
