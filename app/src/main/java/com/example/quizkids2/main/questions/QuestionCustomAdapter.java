package com.example.quizkids2.main.questions;

import com.example.quizkids2.R;
import com.example.quizkids2.objects.Answer;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//TODO change class name to AnswersCustomAdapter
public class QuestionCustomAdapter extends RecyclerView.Adapter<QuestionCustomAdapter.ViewHolder> {
    private ArrayList<Answer> answers;
    private final RecyclerViewInterface recyclerViewInterface;
    private int selectedAnswerPosition = -1;
    private boolean timedOut = false;


    public void updateAnswers(ArrayList<Answer> list) {
        this.answers = list;
        this.selectedAnswerPosition = -1;
        this.timedOut = false;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final CardView answerCardView;
        private final TextView answerTextView;

        public ViewHolder(View v) {
            super(v);
            answerCardView = v.findViewById(R.id.answerCard);
            answerTextView = v.findViewById(R.id.answer);
        }

        public CardView getAnswerCardView() {
            return answerCardView;
        }

        public TextView getAnswerTextView() {
            return answerTextView;
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
        viewHolder.getAnswerTextView().setText(answers.get(position).getAnswer());
        viewHolder.getAnswerCardView().setCardElevation(8);
        viewHolder.itemView.setOnClickListener(v -> {
            if (timedOut) return;

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

        int unansweredColor = ContextCompat.getColor(viewHolder.answerCardView.getContext(), R.color.pinkGradient);
        int answeredColor = ContextCompat.getColor(viewHolder.answerCardView.getContext(), R.color.answeredPink);
        int rightAnswerColor = ContextCompat.getColor(viewHolder.answerCardView.getContext(), R.color.rightAnswer);
        int wrongAnswerColor = ContextCompat.getColor(viewHolder.answerCardView.getContext(), R.color.wrongAnswer);

        //TODO extract to a method "HandleAnswerBackgroundColor()" (lines 83-100)
        if (!timedOut) {
            if (selectedAnswerPosition == position) {
                viewHolder.getAnswerCardView().setCardBackgroundColor(answeredColor);
            } else {
                viewHolder.getAnswerCardView().setCardBackgroundColor(unansweredColor);
            }
        } else {
            viewHolder.getAnswerCardView().setCardBackgroundColor(unansweredColor);

            if (answers.get(position).isCorrect()) {
                viewHolder.getAnswerCardView().setCardBackgroundColor(rightAnswerColor);
                return;
            }

            if (selectedAnswerPosition == position) {
                viewHolder.getAnswerCardView().setCardBackgroundColor(wrongAnswerColor);
            }

        }
    }

    @Override
    public int getItemCount() {
        return answers.size();
    }

    public void onTimeout() {
        this.timedOut = true;
        notifyDataSetChanged();
    }
}