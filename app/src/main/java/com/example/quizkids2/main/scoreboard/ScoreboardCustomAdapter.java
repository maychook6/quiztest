package com.example.quizkids2.main.scoreboard;

import com.example.quizkids2.R;
import com.example.quizkids2.objects.User;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ScoreboardCustomAdapter extends RecyclerView.Adapter<ScoreboardCustomAdapter.ViewHolder> {

    private final ArrayList<User> allUsers;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView highScore;
        private final TextView nickname;
        private final TextView place;

        public ViewHolder(View v) {
            super(v);

            highScore = v.findViewById(R.id.highScore);
            nickname = v.findViewById(R.id.nickname);
            place = v.findViewById(R.id.place);
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
        Integer placePos = position + 1;
        viewHolder.getUserView().setText(allUsers.get(position).getNickname());
        viewHolder.getHighScoreView().setText(allUsers.get(position).getScore().toString());
        viewHolder.getPlace().setText(placePos.toString() + ".");
        setFadeAnimation(viewHolder.itemView);
    }

    @Override
    public int getItemCount() {
        return allUsers.size();
    }

    public void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(400);
        view.startAnimation(anim);
    }
}