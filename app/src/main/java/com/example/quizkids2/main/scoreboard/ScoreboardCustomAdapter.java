package com.example.quizkids2.main.scoreboard;

import com.example.quizkids2.R;
import com.example.quizkids2.objects.User;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ScoreboardCustomAdapter extends RecyclerView.Adapter<ScoreboardCustomAdapter.ViewHolder> {

    private ArrayList<User> allUsers;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final CardView cardView;
        private final TextView highScore;
        private final TextView nickname;
        private final TextView place;

        public ViewHolder(View v) {
            super(v);

            cardView = v.findViewById(R.id.scoreCard);
            highScore = v.findViewById(R.id.highScore);
            nickname = v.findViewById(R.id.nickname);
            place = v.findViewById(R.id.place);
        }

        public CardView getCardView() {
            return cardView;
        }

        public TextView getHighScoreView() {
            return highScore;
        }

        public TextView getUserView() {
            return nickname;
        }
        public TextView getPlace() {
            return place;
        }
    }

    public ScoreboardCustomAdapter(ArrayList<User> allUsers) {
        this.allUsers = allUsers;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_list_scoreboard, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Integer placePos = position +1;
        viewHolder.getUserView().setText(allUsers.get(position).getNickname());
        viewHolder.getHighScoreView().setText(allUsers.get(position).getScore().toString());
        viewHolder.getPlace().setText(placePos.toString());
    }

    @Override
    public int getItemCount() {
        return allUsers.size();
    }

}