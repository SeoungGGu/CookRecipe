package com.example.cookrecipe.base;

import android.content.Context;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseViewHolder<B extends ViewDataBinding, D> extends RecyclerView.ViewHolder {
    protected B binding;
    private Context context;

    public BaseViewHolder(View view) {
        super(view);
        this.binding = DataBindingUtil.bind(itemView);
        context = itemView.getContext();
    }

    protected abstract void bind(D data);

    protected void recycled() {
        // no-op
    }

    protected Context getContext() {
        return context;
    }
}
