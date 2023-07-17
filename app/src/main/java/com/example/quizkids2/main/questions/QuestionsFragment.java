package com.example.quizkids2.main.questions;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.quizkids2.R;
import com.example.quizkids2.objects.Answer;
import com.example.quizkids2.objects.QA;
import com.example.quizkids2.objects.User;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuestionsFragment extends Fragment implements RecyclerViewInterface {
    public static final String ARG_CATEGORIES_LIST = "categoriesChecked";
    private ArrayList<String> categories;
    private List<QA> listQA = new ArrayList<>();
    private TextView question;
    private TextView timer;
    private Button nextQ;
    private boolean isCorrect = false;
    private QuestionCustomAdapter adapter;
    private User user;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        categories = getArguments().getStringArrayList(ARG_CATEGORIES_LIST);

        View view = inflater.inflate(R.layout.fragment_questions, container, false);

        timer = view.findViewById(R.id.timerTV);
        question = view.findViewById(R.id.question);
        nextQ = view.findViewById(R.id.nextQ);
        nextQ.setAlpha(.5f);
        nextQ.setEnabled(false);
        RecyclerView recyclerView = view.findViewById(R.id.answerList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        adapter = new QuestionCustomAdapter(new ArrayList<Answer>(), this);

        fetchListQA();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        nextQ.setOnClickListener(v -> {
            updateQA();
        });

        return view;
    }

    private void fetchUserData() {
        CollectionReference colRef = db.collection("users");
        colRef.whereEqualTo("email", categories);

        colRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    listQA.add(document.toObject(QA.class));
                }
                updateQA();
            }
        });
    }

    private void fetchListQA() {

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        String userId = sharedPref.getString("userId", null);

        DocumentReference docRef = db.collection("qa").document(userId);

        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                user = document.toObject(User.class);
                updateQA();
            }
        });

    }

    private void updateQA () {

        List<Integer> generated = new ArrayList<>();
        Random rng = new Random();

        Integer next = rng.nextInt(listQA.size()) + 1;
        if (!generated.contains(next))
        {
            question.setText(listQA.get(next).getQuestion());
            adapter.updateAnswers(listQA.get(next).getAnswers());
            generated.add(next);
            resetTimer();
        }
    }

    @Override
    public void onItemClick(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }


    public void resetTimer() {
        this.isCorrect = false;
        nextQ.setEnabled(false);
        new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                timer.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                adapter.onTimeout();
                nextQ.setAlpha(1f);
                nextQ.setEnabled(true);
            }
        }.start();

    }
}