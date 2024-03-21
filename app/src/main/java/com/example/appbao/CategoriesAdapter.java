package com.example.appbao;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CategoriesAdapter extends ArrayAdapter<Categories> {
    public CategoriesAdapter(Context context, List<Categories> categoriesList) {
        super(context, 0, categoriesList);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_category, parent, false);
        }

        Categories category = getItem(position);
        if (category != null) {
            TextView textViewMaCate = convertView.findViewById(R.id.textViewMaCate);
            TextView textViewNameCate = convertView.findViewById(R.id.textViewNameCate);

            textViewMaCate.setText(category.getMa());
            textViewNameCate.setText(category.getTen());
        }

        return convertView;
    }
}
