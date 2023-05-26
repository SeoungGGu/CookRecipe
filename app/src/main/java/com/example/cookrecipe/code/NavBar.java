package com.example.cookrecipe.code;

import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.example.cookrecipe.R;
import com.example.cookrecipe.main.MainActivity;
import com.google.android.material.navigation.NavigationBarView;

public class NavBar {
    public NavBar(MainActivity main){

        main.getNavigationBarView().setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        main.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, main.homeFragment).commit();
                        return true;
                    case R.id.notice:
                        main.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, main.noticeFragment).commit();
                        return true;
                    case R.id.bottom_Myice:
                        main.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, main.bottomMyiceFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }
}
