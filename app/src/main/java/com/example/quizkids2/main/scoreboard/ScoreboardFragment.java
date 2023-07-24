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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Objects;


public class ScoreboardFragment extends Fragment {

    private ScoreboardCustomAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    private User user;
    private User currUser;
    private final ArrayList<User> allUsers = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_scoreboard, container, false);
        recyclerView = view.findViewById(R.id.scoreList);
        layoutManager = new LinearLayoutManager(getActivity());
        adapter = new ScoreboardCustomAdapter(allUsers);

        fetchUsers();

        return view;
    }

    public void fetchUsers(){
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference colRef = db.collection("users");

        colRef.orderBy("score", Query.Direction.DESCENDING).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String id;
                for (QueryDocumentSnapshot document : task.getResult()) {
                    user = document.toObject(User.class);
                    allUsers.add(user);
                    if (Objects.equals(user.getId(), userId)){
                        currUser = user;
                    }
                }
                adapter.notifyDataSetChanged();

                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.getLayoutManager().scrollToPosition(allUsers.indexOf(currUser));
            }
        });
    }
}