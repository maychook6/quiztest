package com.example.quizkids2.main.categories;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.quizkids2.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class CategoriesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.categoriesList);
        Button startGameBtn = view.findViewById(R.id.startGameBtn);
        Button skipBtn = view.findViewById(R.id.skipBtn);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        CustomAdapter adapter = new CustomAdapter(new ArrayList<>());

        fetchCategories(db, adapter);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        startGameBtn.setOnClickListener(v -> {


        });

        skipBtn.setOnClickListener(v -> {

        });

        return view;
    }

    private void fetchCategories(FirebaseFirestore db, CustomAdapter adapter) {
        CollectionReference colRef = db.collection("questions");

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

}


//            DocumentReference docRef = db.collection("questions").document("animals");
//            docRef.get().addOnSuccessListener(documentSnapshot -> {
//                AnimalsData animalsData = documentSnapshot.toObject(AnimalsData.class);
//                animalsData
//            });


