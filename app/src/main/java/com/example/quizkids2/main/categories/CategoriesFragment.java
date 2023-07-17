package com.example.quizkids2.main.categories;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.quizkids2.R;
import com.example.quizkids2.main.questions.QuestionsFragment;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class CategoriesFragment extends Fragment {

    private final ArrayList<Category> categories = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.categoriesList);
        Button startGameBtn = view.findViewById(R.id.startGameBtn);
        Button skipBtn = view.findViewById(R.id.skipBtn);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        CategoriesCustomAdapter adapter = new CategoriesCustomAdapter(categories);

        if (categories.size() == 0) {
            fetchCategories(db, adapter);
        }

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        startGameBtn.setOnClickListener(v -> {
            navigateToQuestionFragment();
        });

        skipBtn.setOnClickListener(v -> {

        });

        return view;
    }

    private void navigateToQuestionFragment() {
        ArrayList<String> checkedCategories = new ArrayList<>();

        for (Category category : categories) {
            if (category.isChecked) {
                checkedCategories.add(category.title);
            }
        }

        Fragment fragment = new QuestionsFragment();
        FragmentManager fragmentManager = getParentFragmentManager();

        Bundle result = new Bundle();
        result.putStringArrayList(QuestionsFragment.ARG_CATEGORIES_LIST, checkedCategories);
        fragment.setArguments(result);

        fragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragment).addToBackStack(null).commit();
    }

    private void fetchCategories(FirebaseFirestore db, CategoriesCustomAdapter adapter) {


        CollectionReference colRef = db.collection("listOfCollections");

        colRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    categories.add(new Category(document.getId(), false));
                }
                adapter.updateCategories(categories);
            } else {
                // TODO error alert "somethnig is wrong you cunt"
            }
        });
    }

}
