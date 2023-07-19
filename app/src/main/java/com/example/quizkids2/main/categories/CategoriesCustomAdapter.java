package com.example.quizkids2.main.categories;

import com.example.quizkids2.R;
import com.example.quizkids2.objects.Category;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CategoriesCustomAdapter extends RecyclerView.Adapter<CategoriesCustomAdapter.ViewHolder> {
    private static final String TAG = "CategoriesCustomAdapter";

    private List<Category> categories;

    public void updateCategories(List<Category> list) {
        this.categories = list;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final CheckBox checkBox;

        public ViewHolder(View v) {
            super(v);
            checkBox = v.findViewById(R.id.categoryCB);
        }

        public CheckBox getCheckBox() {
            return checkBox;
        }

    }

    public CategoriesCustomAdapter(ArrayList<Category> categories) {
        this.categories = categories;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_list_categories, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getCheckBox().setText(categories.get(position).getTitle());
        viewHolder.getCheckBox().setChecked(categories.get(position).isChecked());
        viewHolder.getCheckBox().setOnCheckedChangeListener((compoundButton, b) -> {
            if (compoundButton.isChecked()) {
                compoundButton.setChecked(true);
                categories.get(position).setChecked(true);
            } else {
                compoundButton.setChecked(false);
                categories.get(position).setChecked(false);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}