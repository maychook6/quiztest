package com.example.quizkids2.main.questions;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.quizkids2.R;
import com.example.quizkids2.main.categories.CategoriesFragment;
import com.example.quizkids2.objects.Answer;
import com.example.quizkids2.objects.QA;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class QuestionsFragment extends Fragment implements RecyclerViewInterface{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_questions, container, false);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        RecyclerView recyclerView = view.findViewById(R.id.answerList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        QuestionCustomAdapter adapter = new QuestionCustomAdapter(new ArrayList<Answer>(), this);

        fetchQa(db, adapter);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

    private void fetchQa(FirebaseFirestore db, QuestionCustomAdapter adapter) {

        getParentFragmentManager().setFragmentResultListener("f1", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {

                ArrayList<String> categories = result.getStringArrayList("categoriesChecked");
                String a = "a";
//                ArrayList<String> data = result.getStringArrayList("docs");
//                int size = result.getInt("size");
//                ArrayList<String> cols = result.getStringArrayList("collections");
//
//                Random random = new Random();
//                int index = random.ints(0, size).findFirst().getAsInt();
//                String docId = data.get(index);

            }
        });

        CollectionReference colRef = db.collection("animals");

        DocumentReference docRef = colRef.document("1TmQ6kRIiT8km3b0giMQ");

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String question = new String();
                ArrayList<Answer> answers = new ArrayList<>();

                QA qa = documentSnapshot.toObject(QA.class);

                question = qa.getQuestion();
                answers.addAll(qa.getAnswers());

                adapter.updateQuestions(answers);
                setQuestion(question, getView().findViewById(R.id.question));
            }
        });
    }

    public void setQuestion (String question, TextView questionView) {
        questionView.setText(question);
    }

    @Override
    public void onItemClick(int position) {
        Fragment fragment = new QuestionsFragment();
        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragment).commit();
    }
}