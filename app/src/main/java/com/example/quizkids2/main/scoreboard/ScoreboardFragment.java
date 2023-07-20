package com.example.quizkids2.main.scoreboard;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.quizkids2.R;
import com.example.quizkids2.objects.User;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.core.OrderBy;


import java.util.ArrayList;
import java.util.Arrays;

public class ScoreboardFragment extends Fragment {

    private ScoreboardCustomAdapter adapter;
    private User user;
    private final ArrayList<User> allUsers = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_scoreboard, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.scoreList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        adapter = new ScoreboardCustomAdapter(allUsers);

        fetchUsers();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

    public void fetchUsers(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference colRef = db.collection("users");

        colRef.orderBy("score", Query.Direction.DESCENDING).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    user = document.toObject(User.class);
                    allUsers.add(user);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

}