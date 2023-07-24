package com.example.quizkids2.main.categories;

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
import com.example.quizkids2.main.account.AccountFragment;
import com.example.quizkids2.main.questions.QuestionsFragment;
import com.example.quizkids2.main.utils.FragmentNavigator;
import com.example.quizkids2.main.utils.Transition;
import com.example.quizkids2.objects.Category;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

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
        TextView readySetGo = view.findViewById(R.id.readySetGo);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        CategoriesCustomAdapter adapter = new CategoriesCustomAdapter(categories);

        if (categories.size() == 0) {
            fetchCategories(db, adapter);
        }

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        startGameBtn.setOnClickListener(v -> {
            onStartGameClicked(startGameBtn, readySetGo);
        });

        selectAllBtn.setOnClickListener(v -> {
            onSelectAllClicked(adapter);
        });

        return view;
    }

    private void onStartGameClicked(Button startGameBtn, TextView readySetGo) {
        ArrayList<String> checkedCategories = getCheckedCategories();

        if (checkedCategories.size() == 0) {
            String categoryDialogMsg = getString(R.string.checkCategory);
            openDialog(categoryDialogMsg);
        } else {
            String ready = getString(R.string.ready);
            String set = getString(R.string.set);
            String go = getString(R.string.go);
            ArrayList<String> beforeGameStartsMsg = new ArrayList<>();
            beforeGameStartsMsg.addAll(Arrays.asList(ready, set, go));

            startGameBtn.setVisibility(View.GONE);
            new CountDownTimer(3000, 1000) {
                int i = 0;
                @Override
                public void onTick(long l) {
                    readySetGo.setText(beforeGameStartsMsg.get(i));
                    i++;
                }

                public void onFinish() {
                    navigateToQuestionFragment(checkedCategories);
                }
            }.start();
        }
    }

    private void onSelectAllClicked(CategoriesCustomAdapter adapter) {
        for (Category category: categories) {
            category.setChecked(true);
        }
        adapter.updateCategories(categories);
    }

    private void navigateToQuestionFragment(ArrayList<String> checkedCategories) {
        Bundle result = new Bundle();
        result.putStringArrayList(QuestionsFragment.ARG_CATEGORIES_LIST, checkedCategories);

        new FragmentNavigator(getParentFragmentManager()).navigateToFragment(new QuestionsFragment(), Transition.REPLACE, true, result);
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
        String language = Locale.getDefault().getLanguage();

        if (language == "iw") {
            colRef = db.collection("listOfCollectionsHebrew");
        }

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
