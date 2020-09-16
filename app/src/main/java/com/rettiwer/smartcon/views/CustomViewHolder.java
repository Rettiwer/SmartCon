package com.rettiwer.smartcon.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class CustomViewHolder extends RecyclerView.ViewHolder {

    public CustomViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bind(Object item);
}
