package com.example.quizkids2.main.questions;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quizkids2.R;
import com.example.quizkids2.main.mainScreen.MainScreenFragment;
import com.example.quizkids2.objects.Answer;
import com.example.quizkids2.objects.QA;
import com.example.quizkids2.objects.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Collections;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class QuestionsFragment extends Fragment implements RecyclerViewInterface {
    public static final String ARG_CATEGORIES_LIST = "categoriesChecked";
    private ArrayList<String> categories;
    private final List<QA> listQA = new ArrayList<>();
    private TextView question;
    private TextView roundTimer;
    private Button nextQ;
    private List<ImageView> hearts = new ArrayList<>();
    private boolean isCorrect = false;
    private QuestionCustomAdapter adapter;
    private User user;
    private Integer scoreCounter;
    private Integer heartCounter;
    private final Random random = new Random();
    private Integer randomIndex = random.nextInt();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        categories = getArguments().getStringArrayList(ARG_CATEGORIES_LIST);

        View view = inflater.inflate(R.layout.fragment_questions, container, false);

        ImageView heartNo1 = view.findViewById(R.id.heartNo3);
        ImageView heartNo2 = view.findViewById(R.id.heartNo2);
        ImageView heartNo3 = view.findViewById(R.id.heartNo1);
        hearts = Arrays.asList(heartNo1, heartNo2, heartNo3);

        roundTimer = view.findViewById(R.id.timer);
        question = view.findViewById(R.id.question);

        nextQ = view.findViewById(R.id.nextQ);
        nextQ.setAlpha(.5f);
        nextQ.setEnabled(false);

        RecyclerView recyclerView = view.findViewById(R.id.score);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        adapter = new QuestionCustomAdapter(new ArrayList<Answer>(), this);

        heartCounter = 3;
        scoreCounter = 0;
        fetchData();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        nextQ.setOnClickListener(v -> {
            updateQA();
        });

        return view;
    }

    private void fetchData() {

        db = FirebaseFirestore.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference docRef = db.collection("users").document(userId);

        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                user = document.toObject(User.class);
                fetchListQA();
            }
        });
    }

    private void fetchListQA() {

        CollectionReference colRef = db.collection("qa");
        colRef.whereIn("category", categories).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    listQA.add(document.toObject(QA.class));
                }
                if (user.getQuestionsCorrectIds() != null) {
                    ArrayList<QA> toDelete = new ArrayList<>();
                    for (QA item : listQA) {
                        if (user.getQuestionsCorrectIds().contains(item.getId())) {
                            toDelete.add(item);
                        }
                    }
                    listQA.removeAll(toDelete);
                }
                updateQA();
            }
        });
    }

    private void updateQA() {

        if (heartCounter == 0) {
            heartCounter = 3;
        }

        if (listQA.size() > 0) {

            randomIndex =  getRandomIndex();
            question.setText(listQA.get(randomIndex).getQuestion());
            shuffleAnswers();
            adapter.updateAnswers(listQA.get(randomIndex).getAnswers());
            resetTimer(nextQ);

        }
    }


    private int getRandomIndex() {
        return (listQA.size() == 1) ? 0 : random.nextInt(listQA.size() - 1);
    }

    @Override
    public void onItemClick(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public void resetTimer(Button nextQ) {
        this.isCorrect = false;
        nextQ.setEnabled(false);
        new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                roundTimer.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                adapter.onTimeout();

                nextQ.setAlpha(1f);
                nextQ.setEnabled(true);

                if (isCorrect) {
                    scoreCounter++;
                    user.addQuestionCorrectId(listQA.get(randomIndex).getId());
                    db.collection("users").document(user.getId()).update("questionsCorrectIds", FieldValue.arrayUnion(listQA.get(randomIndex).getId()));
                    listQA.remove(listQA.get(randomIndex));
                } else {
                    heartCounter--;
                    hearts.get(heartCounter).setVisibility(View.GONE);

                    if (heartCounter == 0) {
                        user.setTimeToPlay(System.currentTimeMillis());
                        db.collection("users").document(user.getId()).update("timeToPlay", System.currentTimeMillis());
                        user.setCanPlay(false);
                        db.collection("users").document(user.getId()).update("canPlay", false);

                        nextQ.setEnabled(false);

                        if (user.getScore() < scoreCounter) {
                            user.setScore(scoreCounter);
                            db.collection("users").document(user.getId()).update("score", scoreCounter);
                            openDialog("You've beaten your highest score!\nWell done!\nHearts will be refilled in 15 minutes.", "Main screen", new MainScreenFragment());
                        } else {
                            scoreCounter = 0;
                            openDialog("Oops!\nYou're out oh hearts\nCome back in 15 minutes.", "Main screen", new MainScreenFragment());

                        }

                    }
                }
            }
        }.start();

    }

    public void shuffleAnswers() {
        Collections.shuffle(listQA.get(randomIndex).getAnswers());
    }

    public void openDialog(String dialogMessage, String buttonMessage, Fragment fragment) {
        QuestionDialogFragment dialog = new QuestionDialogFragment(dialogMessage, buttonMessage, fragment);
        dialog.setCancelable(false);
        dialog.show(getParentFragmentManager(), "dialog");
    }

}