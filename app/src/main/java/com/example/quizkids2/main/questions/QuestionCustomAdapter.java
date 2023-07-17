package com.example.quizkids2.main.questions;

import com.example.quizkids2.R;
import com.example.quizkids2.objects.Answer;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class QuestionCustomAdapter extends RecyclerView.Adapter<QuestionCustomAdapter.ViewHolder> {
    private ArrayList<Answer> answers;
    private final RecyclerViewInterface recyclerViewInterface;
    private int selectedAnswerPosition = -1;
    private boolean timedout = false;


    public void updateAnswers(ArrayList<Answer> list) {
        this.answers = list;
        this.selectedAnswerPosition = -1;
        this.timedout = false;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final CardView cardView;
        private final TextView textView;

        public ViewHolder(View v) {
            super(v);

            cardView = v.findViewById(R.id.answerCard);
            textView = v.findViewById(R.id.answer);
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
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_list_answers, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getTextView().setText(answers.get(position).getAnswer());
        viewHolder.getCardView().setCardElevation(8);
        viewHolder.itemView.setOnClickListener(v -> {
            if (timedout) return;

            if (selectedAnswerPosition != -1) {
                notifyItemChanged(selectedAnswerPosition);
            }
            selectedAnswerPosition = viewHolder.getAdapterPosition();
            notifyItemChanged(selectedAnswerPosition);
            if (recyclerViewInterface != null) {
                boolean isCorrect = answers.get(position).isCorrect();
                recyclerViewInterface.onItemClick(isCorrect);
            }
        });

        if (!timedout) {
            if (selectedAnswerPosition == position) {
                viewHolder.getCardView().setCardBackgroundColor(Color.DKGRAY);
            } else {
                viewHolder.getCardView().setCardBackgroundColor(Color.GRAY);
            }
        } else {
            viewHolder.getCardView().setCardBackgroundColor(Color.GRAY);

            if (answers.get(position).isCorrect()) {
                viewHolder.getCardView().setCardBackgroundColor(Color.GREEN);
                return;
            }

            if (selectedAnswerPosition == position) {
                viewHolder.getCardView().setCardBackgroundColor(Color.RED);
            }

        }
    }

    @Override
    public int getItemCount() {
        return answers.size();
    }

    public void onTimeout() {
        this.timedout = true;
        notifyDataSetChanged();
    }
}