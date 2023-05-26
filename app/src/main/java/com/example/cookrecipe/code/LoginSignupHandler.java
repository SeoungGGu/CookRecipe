package com.example.cookrecipe.code;

import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.example.cookrecipe.Fragment.MyNoticeFragment;
import com.example.cookrecipe.R;

public class LoginSignupHandler {
    private FragmentManager fragmentManager;
    private DrawerLayout drawerLayout;

    public LoginSignupHandler(FragmentManager fragmentManager, DrawerLayout drawerLayout) {
        this.fragmentManager = fragmentManager;
        this.drawerLayout = drawerLayout;
    }

    public void onMyNoticeButtonClick(View view) {
        fragmentManager.beginTransaction().replace(R.id.fragment_container, new MyNoticeFragment()).commit();
        drawerLayout.closeDrawer(GravityCompat.START);
    }
}
