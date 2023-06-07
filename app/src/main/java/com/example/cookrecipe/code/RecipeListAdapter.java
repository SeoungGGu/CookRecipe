package com.example.cookrecipe.code;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cookrecipe.R;

import java.util.ArrayList;
import java.util.List;

public class RecipeListAdapter extends BaseAdapter {

    private Context context;
    private List<Recipe> recipeList;


    public RecipeListAdapter(Context context, List<Recipe> recipeList) {
        this.context = context;
        this.recipeList = recipeList;
    }

    @Override
    public int getCount() {
        return recipeList.size();
    }

    @Override
    public Object getItem(int i) {
        return recipeList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.recipe, null);

        ImageView recipeImage = (ImageView) v.findViewById(R.id.recipeImage);
        TextView recipeTitle = (TextView) v.findViewById(R.id.recipeTitle);
        TextView nameText = (TextView) v.findViewById(R.id.nameText);
        TextView dateText = (TextView) v.findViewById(R.id.dateText);

        recipeImage.setImageDrawable(recipeList.get(i).getRecipeImage());
        recipeTitle.setText(recipeList.get(i).getRecipetitle());
        nameText.setText(recipeList.get(i).getRecipename());
        dateText.setText(recipeList.get(i).getRecipedate());

        v.setTag(recipeList.get(i).getRecipetitle());

        return v;
    }
}
