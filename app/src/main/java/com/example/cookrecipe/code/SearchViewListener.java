package com.example.cookrecipe.code;

import android.view.Menu;

import androidx.appcompat.widget.SearchView;

import com.example.cookrecipe.Fragment.BoardserveFragmant;
import com.example.cookrecipe.R;
import com.example.cookrecipe.main.MainActivity;

public class SearchViewListener implements SearchView.OnQueryTextListener {
    private final MainActivity activity;
    private final BoardserveFragmant fragment;


    public SearchViewListener(MainActivity activity, BoardserveFragmant fragment) {
        this.activity = activity;
        this.fragment = fragment;
    }


    @Override
    public boolean onQueryTextSubmit(String query) {



        return true; // Return true to indicate that the query has been handled
    }


    @Override
    public boolean onQueryTextChange(String newText) {
        // Do something when query text is changed
        return false;
    }

    public void onCreateOptionsMenu(Menu menu) {
        activity.getMenuInflater().inflate(R.menu.search_menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(this);
    }
}
