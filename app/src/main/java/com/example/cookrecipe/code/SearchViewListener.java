package com.example.cookrecipe.code;

import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.cookrecipe.Fragment.BoardserveFragmant;
import com.example.cookrecipe.R;
import com.example.cookrecipe.main.MainActivity;

public class SearchViewListener implements SearchView.OnQueryTextListener {
    private final MainActivity activity;


    public SearchViewListener(MainActivity activity) {
        this.activity = activity;
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        BoardserveFragmant boardserveFragmant = new BoardserveFragmant();
        Bundle bundle = new Bundle();
        bundle.putString("searchQuery", query);
        boardserveFragmant.setArguments(bundle);

        fragmentTransaction.replace(R.id.fragment_container, boardserveFragmant);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    public void onCreateOptionsMenu(Menu menu) {
        activity.getMenuInflater().inflate(R.menu.search_menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(this);
    }
}
