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
import java.util.Locale;
import java.util.Random;
import java.util.Collections;


public class QuestionsFragment extends Fragment implements RecyclerViewInterface {
    public static final String ARG_CATEGORIES_LIST = "categoriesChecked";
    private ArrayList<String> categories;
    private final List<QA> listQA = new ArrayList<>();
    private TextView questionTV;
    private TextView timerTV;
    private CountDownTimer timer;
    int randomIndex;
    private Button nextQuestionBtn;
    private Button submitAnswerBtn;
    private List<ImageView> hearts = new ArrayList<>();
    private boolean isCorrect = false;
    private AnswersCustomAdapter adapter;
    private User user;
    private Integer scoreCounter;
    private Integer heartCounter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    String secondsRemaining;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        categories = getArguments().getStringArrayList(ARG_CATEGORIES_LIST);

        View view = inflater.inflate(R.layout.fragment_questions, container, false);

        ImageView heartNo1 = view.findViewById(R.id.heartNo3);
        ImageView heartNo2 = view.findViewById(R.id.heartNo2);
        ImageView heartNo3 = view.findViewById(R.id.heartNo1);
        hearts = Arrays.asList(heartNo1, heartNo2, heartNo3);

        timerTV = view.findViewById(R.id.timer);
        questionTV = view.findViewById(R.id.question);
        secondsRemaining = getString(R.string.secondsRemaining);

        submitAnswerBtn = view.findViewById(R.id.submitAnswer);

        nextQuestionBtn = view.findViewById(R.id.nextQuestion);
        nextQuestionBtn.setAlpha(.5f);
        nextQuestionBtn.setEnabled(false);

        RecyclerView recyclerView = view.findViewById(R.id.score);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        adapter = new AnswersCustomAdapter(new ArrayList<>(), this);

        heartCounter = 3;
        scoreCounter = 0;
        fetchUser();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        submitAnswerBtn.setOnClickListener(v -> {
            timer.cancel();
            onSubmitOrFinish();
        });

        nextQuestionBtn.setOnClickListener(v -> {
            submitAnswerBtn.setAlpha(1f);
            submitAnswerBtn.setEnabled(true);
            updateQA();
        });

        return view;
    }

    private void fetchUser() {
        db = FirebaseFirestore.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference docRef = db.collection("users").document(userId);

        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                user = document.toObject(User.class);
                fetchQaList();
            }
        });
    }

    private void fetchQaList() {
        String language = Locale.getDefault().getLanguage();
        CollectionReference colRef = db.collection("qa");
        if (language == "iw") {
            colRef = db.collection("qaHebrew");
        }

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
        if (listQA.size() > 0) {
            randomIndex =  getRandomIndex();
            questionTV.setText(listQA.get(randomIndex).getQuestion());
            shuffleAnswers();
            adapter.updateAnswers(listQA.get(randomIndex).getAnswers());
            resetTimer();
        }
    }


    private int getRandomIndex() {
        Random random = new Random();
        return (listQA.size() == 1) ? 0 : random.nextInt(listQA.size() - 1);
    }

    @Override
    public void onItemClick(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public void resetTimer() {
        this.isCorrect = false;
        nextQuestionBtn.setEnabled(false);
        timer = new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                timerTV.setText(secondsRemaining + " " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                onSubmitOrFinish();
            }
        }.start();

    }

    private void onSubmitOrFinish() {
        timerTV.setText(secondsRemaining + " 0");
        adapter.onTimeout();

        submitAnswerBtn.setAlpha(.5f);
        submitAnswerBtn.setEnabled(false);

        nextQuestionBtn.setAlpha(1f);
        nextQuestionBtn.setEnabled(true);

        if (isCorrect) {
            scoreCounter++;
            user.addQuestionCorrectId(listQA.get(randomIndex).getId());
            db.collection("users").document(user.getId()).update("questionsCorrectIds", FieldValue.arrayUnion(listQA.get(randomIndex).getId()));
            listQA.remove(listQA.get(randomIndex));
        } else {
            String highScoreBeaten = getString(R.string.highScoreBeaten);
            String disqualified = getString(R.string.disqualified);
            String mainScreenBtn = getString(R.string.mainScreenBtn);

            heartCounter--;
            hearts.get(heartCounter).setVisibility(View.GONE);

            if (heartCounter == 0) {

                user.setTimeToPlay(System.currentTimeMillis());
                user.setCanPlay(false);
                db.collection("users").document(user.getId()).update("timeToPlay", System.currentTimeMillis(),
                        "canPlay", false);
                nextQuestionBtn.setEnabled(false);

                if (user.getScore() < scoreCounter) {
                    user.setScore(scoreCounter);
                    db.collection("users").document(user.getId()).update("score", scoreCounter);
                    openDialog(highScoreBeaten, mainScreenBtn, new MainScreenFragment());
                } else {
                    scoreCounter = 0;
                    openDialog(disqualified, mainScreenBtn, new MainScreenFragment());
                }
            }
        }
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