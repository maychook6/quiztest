package com.example.quizkids2.main.categories;

import com.example.quizkids2.R;
import com.example.quizkids2.main.questions.RecyclerViewInterface;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CategoriesCustomAdapter extends RecyclerView.Adapter<CategoriesCustomAdapter.ViewHolder> {
    private static final String TAG = "CategoriesCustomAdapter";

    private List<String> categories;

    private final CheckBoxRecyclerViewInterface checkBoxRecyclerViewInterface;

    public void updateCategories(List<String> list) {
        this.categories = list;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final CheckBox checkBox;
        public ViewHolder(View v, CheckBoxRecyclerViewInterface checkBoxRecyclerViewInterface) {

            super(v);

            checkBox = v.findViewById(R.id.categoryCB);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (checkBoxRecyclerViewInterface != null) {
                        int pos = getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION) {
                            checkBoxRecyclerViewInterface.onItemCheck(pos);
                        }
                    }
                }
            });
        }

        public CheckBox getCheckBox() {
            return checkBox;
        }

    }

    public CategoriesCustomAdapter(ArrayList<String> dataSet, CheckBoxRecyclerViewInterface checkBoxRecyclerViewInterface) {
        this.categories = dataSet;
        this.checkBoxRecyclerViewInterface = checkBoxRecyclerViewInterface;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_list_categories, viewGroup, false);

        return new ViewHolder(v, checkBoxRecyclerViewInterface);
    }
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getCheckBox().setText(categories.get(position));
        viewHolder.getCheckBox().setChecked(false);
        viewHolder.getCheckBox().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked())
                {
                    compoundButton.setChecked(true);
                }
                else
                {
                    compoundButton.setChecked(false);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}