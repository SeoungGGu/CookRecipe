package com.example.cookrecipe.adapter;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cookrecipe.L;
import com.example.cookrecipe.R;
import com.example.cookrecipe.base.BaseViewHolder;
import com.example.cookrecipe.data.Ingredent;
import com.example.cookrecipe.databinding.ItemIngredientHeaderBinding;
import com.example.cookrecipe.databinding.ItemIngredientRowBinding;

import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;

public class IngredentSection extends Section {

    private String title;
    private List<Ingredent> list;

    private IngredinetDelegate delegate;

    public IngredentSection(String title, List<Ingredent> list , IngredinetDelegate delegate) {
        super(SectionParameters.builder()
                .itemResourceId(R.layout.item_ingredient_row)
                .headerResourceId(R.layout.item_ingredient_header)
                .build());


        this.title = title;
        this.list = list;
        this.delegate = delegate;
    }

    @Override
    public int getContentItemsTotal() {
        return list.size();
    }


    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderViewHolder(view);
    }



    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).bind(list.get(position));
        }
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).bind(title);
        }
    }

    public class ItemViewHolder extends BaseViewHolder<ItemIngredientRowBinding, Ingredent> {
        public ItemViewHolder(View parent) {
            super(parent);
        }

        @Override
        protected void bind(Ingredent data) {
            binding.tvTitle.setText(data.getName());
            binding.ivBanner.setImageResource(data.getIcon());


            itemView.setOnClickListener(view -> {
                if(delegate != null){
                    delegate.onClickItem(data);
                }

            });




        }
    }


    public static class HeaderViewHolder extends BaseViewHolder<ItemIngredientHeaderBinding, String> {
        public HeaderViewHolder(View parent) {
            super(parent);
        }

        @Override
        protected void bind(String data) {
            binding.tvTitle.setText(data);
        }
    }


}
