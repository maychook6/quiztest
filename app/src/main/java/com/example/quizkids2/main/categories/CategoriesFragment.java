package com.example.quizkids2.main.categories;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.quizkids2.R;
import com.example.quizkids2.main.questions.QuestionsFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.model.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CategoriesFragment extends Fragment implements CheckBoxRecyclerViewInterface{

    ArrayList<String> categoriesChecked = new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.categoriesList);
        Button startGameBtn = view.findViewById(R.id.startGameBtn);
        Button skipBtn = view.findViewById(R.id.skipBtn);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        CategoriesCustomAdapter adapter = new CategoriesCustomAdapter(new ArrayList<>(), this);

        fetchCategories(db, adapter);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        startGameBtn.setOnClickListener(v -> {
            Fragment fragment = new QuestionsFragment();
            FragmentManager fragmentManager = getParentFragmentManager();

            Bundle result = new Bundle();
            result.putStringArrayList("categoriesChecked", categoriesChecked);
            fragmentManager.setFragmentResult("f1", result);

            //fetchDocs(db, fragmentManager);

            fragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragment).commit();
        });

        skipBtn.setOnClickListener(v -> {

        });

        return view;
    }

    private void fetchCategories(FirebaseFirestore db, CategoriesCustomAdapter adapter) {

        CollectionReference colRef = db.collection("listOfCollections");

        colRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<String> list = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    list.add(document.getId());
                }
                adapter.updateCategories(list);
            } else {
                // TODO error alert "somethnig is wrong you cunt"
            }
        });
    }

    private void fetchDocs (FirebaseFirestore db, FragmentManager fragmentManager) {

        Bundle result = new Bundle();

        CollectionReference collectionListRef = db.collection("listOfCollections");
        collectionListRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<String> cols = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        cols.add(document.getId());
                    }
                    result.putStringArrayList("collections", cols);
                }
            }
        });


        CollectionReference colRef = db.collection("animals");
        colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<String> docs = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        docs.add(document.getId());
                    }

                    Bundle result = new Bundle();
                    result.putStringArrayList("docs", docs);
                    result.putInt("size", docs.size());
                    fragmentManager.setFragmentResult("f1", result);
                }
            }
        });
    }

    @Override
    public void onItemCheck(int position) {

    }
}


//            DocumentReference docRef = db.collection("questions").document("animals");
//            docRef.get().addOnSuccessListener(documentSnapshot -> {
//                AnimalsData animalsData = documentSnapshot.toObject(AnimalsData.class);
//                animalsData
//            });


