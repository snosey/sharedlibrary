package com.dinnova.sharedlibrary.utils;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.dinnova.sharedlibrary.R;
import com.dinnova.sharedlibrary.ui.CustomTextViewBold;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.viewHolder> {


    private List<MenuModel> menuModels;
    private int resourceRow;

    public MenuAdapter(List<MenuModel> menuModels, int resourceRow) {
        this.menuModels = menuModels;
        this.resourceRow = resourceRow;
    }

    public static class MenuModel {
        public String text;
        public int image;
        public View.OnClickListener onClickListener;

        public MenuModel(String text, int image, View.OnClickListener onClickListener) {
            this.text = text;
            this.image = image;
            this.onClickListener = onClickListener;
        }

    }


    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, final int position) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(resourceRow, parent, false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        MenuModel menuModel = menuModels.get(position);
        holder.image.setImageResource(menuModel.image);
        holder.text.setText(menuModel.text);
        holder.itemView.setOnClickListener(menuModel.onClickListener);
    }

    @Override
    public int getItemCount() {

        return menuModels.size();
    }


    class viewHolder extends RecyclerView.ViewHolder {
        CustomTextViewBold text;
        AppCompatImageView image;

        viewHolder(View v) {
            super(v);
            text = v.findViewById(R.id.text);
            image = v.findViewById(R.id.image);
        }
    }
}
