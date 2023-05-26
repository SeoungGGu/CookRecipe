package com.example.cookrecipe.code;

import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.cookrecipe.Fragment.AramFragment;
import com.example.cookrecipe.Fragment.OptionFragment;
import com.example.cookrecipe.Fragment.ScrapRecipeFragment;
import com.example.cookrecipe.R;
import com.google.android.material.navigation.NavigationView;

public class NavigationItemSelectedListener implements NavigationView.OnNavigationItemSelectedListener {
    private final AppCompatActivity main;

    public NavigationItemSelectedListener(AppCompatActivity main) {
        this.main = main;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_settings:
                main.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new OptionFragment()).commit();
                break;
            case R.id.nav_aram:
                main.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AramFragment()).commit();
                break;
            case R.id.nav_scrap:
                main.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ScrapRecipeFragment()).commit();
                break;
            case R.id.nav_logout:
                Toast.makeText(main, "Logout!", Toast.LENGTH_SHORT).show();
                break;
        }
        DrawerLayout drawerLayout = main.findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}