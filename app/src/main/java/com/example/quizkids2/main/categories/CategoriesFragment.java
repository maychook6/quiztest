package com.example.quizkids2.main.categories;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.quizkids2.R;
import com.example.quizkids2.main.questions.QuestionsFragment;
import com.example.quizkids2.main.utils.FragmentNavigator;
import com.example.quizkids2.main.utils.Transition;
import com.example.quizkids2.objects.Category;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class CategoriesFragment extends Fragment {

    private final ArrayList<Category> categories = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.categoriesList);
        Button startGameBtn = view.findViewById(R.id.startGameBtn);
        Button selectAllBtn = view.findViewById(R.id.selectAllBtn);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        CategoriesCustomAdapter adapter = new CategoriesCustomAdapter(categories);

        if (categories.size() == 0) {
            fetchCategories(db, adapter);
        }

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        startGameBtn.setOnClickListener(v -> {
            //TODO extract to a method "onStartGameClicked()"
            ArrayList<String> checkedCategories = getCheckedCategories();
            if (checkedCategories.size() == 0) {
                openDialog("Please select at least on category, or skip to choose all.");
            } else {
                navigateToQuestionFragment(checkedCategories);
            }
        });

        selectAllBtn.setOnClickListener(v -> {
            //TODO extract to a method "onSelectAllClicked()"
            for (Category category: categories) {
                category.setChecked(true);
            }
            adapter.updateCategories(categories);
        });

        return view;
    }

    private void navigateToQuestionFragment(ArrayList<String> checkedCategories) {
        Bundle result = new Bundle();
        result.putStringArrayList(QuestionsFragment.ARG_CATEGORIES_LIST, checkedCategories);

        new FragmentNavigator(getParentFragmentManager()).navigateToFragment(new QuestionsFragment(), Transition.ADD, result);
    }

    private ArrayList<String> getCheckedCategories() {
        ArrayList<String> checkedCategories = new ArrayList<>();

        for (Category category : categories) {
            if (category.isChecked()) {
                checkedCategories.add(category.getTitle());
            }
        }
        return checkedCategories;
    }

    private void fetchCategories(FirebaseFirestore db, CategoriesCustomAdapter adapter) {
        CollectionReference colRef = db.collection("listOfCollections");

        colRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    categories.add(new Category(document.getId(), false));
                }
                adapter.updateCategories(categories);
            }
        });
    }

    public void openDialog(String dialogMessage) {
        CategoriesDialogFragment dialog = new CategoriesDialogFragment(dialogMessage);
        dialog.show(getParentFragmentManager(), "dialog");
    }
}
