package com.example.quizkids2.main.categories;

import android.content.res.TypedArray;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.quizkids2.R;
import com.example.quizkids2.main.questions.AnimalQuestionsFragment;
import com.example.quizkids2.main.questions.HistoryQuestionsFragment;
import com.example.quizkids2.main.questions.MusicQuestionsFragment;
import com.example.quizkids2.main.questions.ScienceQuestionsFragment;
import com.example.quizkids2.main.questions.SportsQuestionsFragment;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
//import com.igalata.bubblepicker.BubblePickerListener;
//import com.igalata.bubblepicker.model.BubbleGradient;
//import com.igalata.bubblepicker.model.PickerItem;
import com.kienht.bubblepicker.BubblePickerListener;
import com.kienht.bubblepicker.adapter.BubblePickerAdapter;
import com.kienht.bubblepicker.model.BubbleGradient;
import com.kienht.bubblepicker.model.PickerItem;
import com.kienht.bubblepicker.rendering.BubblePicker;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class CategoriesFragment extends Fragment {
    public static final String TAG = "whatever";
    View view;
    FirebaseFirestore db;
    BubblePicker picker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_categories, container, false);
        picker = view.findViewById(R.id.picker);
        db = FirebaseFirestore.getInstance();

        final String[] titles = getResources().getStringArray(R.array.categories);
        final TypedArray colors = getResources().obtainTypedArray(R.array.colors);
        final TypedArray images = getResources().obtainTypedArray(R.array.images);

        picker.setAdapter(new BubblePickerAdapter() {
            @Override
            public int getTotalCount() {
                return titles.length;
            }

            @NotNull
            @Override
            public PickerItem getItem(int position) {
                PickerItem item = new PickerItem();
                item.setTitle(titles[position]);
                item.setGradient(new BubbleGradient(colors.getColor((position * 2) % 8, 0),
                        colors.getColor((position * 2) % 8 + 1, 0), BubbleGradient.VERTICAL));
                item.setImgDrawable(ContextCompat.getDrawable(getActivity(), images.getResourceId(position, 0)));
                item.setTextColor(ContextCompat.getColor(getActivity(), android.R.color.white));

                return item;
            }
        });

        picker.setBubbleSize(20);
        picker.setSwipeMoveSpeed(1f);
        picker.setAlwaysSelected(false);
        picker.setMaxSelectedCount(2);

        picker.setListener(new BubblePickerListener() {
            @Override
            public void onBubbleSelected(@NotNull PickerItem item) {

                List <PickerItem> items = picker.getSelectedItems();
                if (items.size() > 1) {
                    for (PickerItem currItem : items) {
                        if (currItem.getTitle() != item.getTitle()) {
                            currItem.setSelected(true);
                        }
                    }
                }
            }


            @Override
            public void onBubbleDeselected(@NotNull PickerItem item) {

//
//                Fragment fragment;
//                FragmentManager fragmentManager = getParentFragmentManager();
//                String title = item.getTitle();
//
//                if (title != null) {
//                    switch (title) {
//                        case "Animals":
//                            fragment = new AnimalQuestionsFragment();
//                            fragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragment).commit();
//                            break;
//
//                        case "History":
//                            fragment = new HistoryQuestionsFragment();
//                            fragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragment).commit();
//                            break;
//
//                        case "Music":
//                            fragment = new MusicQuestionsFragment();
//                            fragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragment).commit();
//                            break;
//
//                        case "Sports":
//                            fragment = new SportsQuestionsFragment();
//                            fragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragment).commit();
//                            break;
//
//                        case "Science":
//                            fragment = new ScienceQuestionsFragment();
//                            fragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragment).commit();
//                            break;
//
//                    }
//                }
            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        picker.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        picker.onPause();
    }
}


//    DocumentReference animalsRef = db.collection("questions").document("animals");
//                animalsRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//@Override
//public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//        if (task.isSuccessful()) {
//        DocumentSnapshot document = task.getResult();
//        if (document.exists()) {
//        animals = document.getData();
//        Log.i(TAG, "onComplete: ", (Throwable) animals);
//        } else {
//
//        }
//        } else {
//
//        }
//        }
//        });
