package com.example.quizkids2.main.questions;

import com.example.quizkids2.R;
import com.example.quizkids2.objects.Answer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

public class QuestionCustomAdapter extends RecyclerView.Adapter<QuestionCustomAdapter.ViewHolder> {
    private static final String TAG = "QuestionCustomAdapter";

    private ArrayList<Answer> answers;

    private final RecyclerViewInterface recyclerViewInterface;

    public void updateQuestions(ArrayList<Answer> list) {
        this.answers = list;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final CardView cardView;
        private final TextView textView;

        public ViewHolder(View v, RecyclerViewInterface recyclerViewInterface) {

            super(v);

            cardView = v.findViewById(R.id.answerCard);
            textView = v.findViewById(R.id.answer);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerViewInterface != null) {
                        int pos = getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }

        public CardView getCardView() {
            return cardView;
        }
        public TextView getTextView() {
            return textView;
        }
    }
    public QuestionCustomAdapter(ArrayList<Answer> dataSet, RecyclerViewInterface recyclerViewInterface) {
        this.answers = dataSet;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_list_answers, viewGroup, false);

        return new ViewHolder(v, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getTextView().setText(answers.get(position).getAnswer());
        viewHolder.getCardView().setCardElevation(8);
    }

    @Override
    public int getItemCount() {

        return answers.size();
    }
}