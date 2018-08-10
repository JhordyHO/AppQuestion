package com.codsit.appquestion.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codsit.appquestion.R;
import com.codsit.appquestion.model.Question;

import java.util.List;

public class RVadapter extends RecyclerView.Adapter<RVadapter.QuestionViewHolder> {

    List<Question> question;

    public RVadapter(List<Question> questions){
        this.question = questions;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question, parent, false);
        QuestionViewHolder pvh = new QuestionViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        holder.pregunta.setText(question.get(position).getQuestion());
    }

    @Override
    public int getItemCount() {
        return question.size();
    }


    ////// View Holder ///////
    public static class QuestionViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView pregunta;


        QuestionViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.id_cv);
            pregunta = itemView.findViewById(R.id.id_quest);

        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
