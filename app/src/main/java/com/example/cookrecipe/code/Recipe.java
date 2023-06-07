package com.example.cookrecipe.code;

import android.graphics.drawable.Drawable;

public class Recipe {

    private int id;
    Drawable recipeImage;
    String recipetitle;
    String recipename;
    String recipedate;


    public Recipe(int id, Drawable recipeImage, String recipetitle, String recipename, String recipedate) {
        this.id = id;
        this.recipeImage = recipeImage;
        this.recipetitle = recipetitle;
        this.recipename = recipename;
        this.recipedate = recipedate;
    }
    public void setRecipeImage(Drawable recipeImage){
        this.recipeImage = recipeImage;
    }

    public void setRecipetitle(String recipetitle) {
        this.recipetitle = recipetitle;
    }

    public void setRecipename(String recipename) {
        this.recipename = recipename;
    }

    public void setRecipedate(String recipedate) {
        this.recipedate = recipedate;
    }

    public int getId() {
        return id;
    }

    public Drawable getRecipeImage() {
        return recipeImage;
    }

    public String getRecipetitle() {
        return recipetitle;
    }

    public String getRecipename() {
        return recipename;
    }

    public String getRecipedate() {
        return recipedate;
    }
}
