package com.example.cookrecipe.main;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.cookrecipe.Fragment.MyNoticeFragment;
import com.example.cookrecipe.code.BackPressHandler;
import com.example.cookrecipe.Fragment.BottomMyiceFragment;
import com.example.cookrecipe.Fragment.NoticeFragment;
import com.example.cookrecipe.Fragment.HomeFragment;
import com.example.cookrecipe.code.LoginSignupHandler;
import com.example.cookrecipe.code.NavBar;
import com.example.cookrecipe.code.NavigationItemSelectedListener;
import com.example.cookrecipe.R;
import com.example.cookrecipe.code.SearchViewListener;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main_Activity";
    private SearchViewListener searchViewListener = new SearchViewListener(this);

    private DrawerLayout drawerLayout;
    private NavigationBarView navigationBarView;
    public HomeFragment homeFragment = new HomeFragment();
    public NoticeFragment noticeFragment = new NoticeFragment();
    public MyNoticeFragment myNoticeFragment = new MyNoticeFragment();
    public BottomMyiceFragment bottomMyiceFragment = new BottomMyiceFragment();


    public Toolbar toolbar;
    public NavigationView navigationView;
    public BackPressHandler backPressHandler;
    public ActionBarDrawerToggle toggle;
    public LoginSignupHandler loginSignupHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationBarView = findViewById(R.id.bottom_navigationview);
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_view);
        backPressHandler = new BackPressHandler(drawerLayout);
        navigationView.setNavigationItemSelectedListener(new NavigationItemSelectedListener(this));
        NavBar navBar = new NavBar(this);

        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);

        loginSignupHandler = new LoginSignupHandler(getSupportFragmentManager(), drawerLayout);

        instanceStateSetting(savedInstanceState);
    }

    public void onMyNoticeButtonClick(View view) {
        loginSignupHandler.onMyNoticeButtonClick(view);
    }

    public NavigationBarView getNavigationBarView() {
        return navigationBarView;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        searchViewListener.onCreateOptionsMenu(menu);
        return true;
    }

    public void instanceStateSetting(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFragment).commit();
            navigationView.setCheckedItem(R.id.nav_view);
            navigationBarView.setVisibility(View.VISIBLE); // Show bottom nav when HomeFragment is first loaded
        } else {
            // Restore the previous fragment
            String fragmentTag = savedInstanceState.getString("CURRENT_FRAGMENT_TAG");
            Fragment currentFragment = getSupportFragmentManager().findFragmentByTag(fragmentTag);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, currentFragment, fragmentTag).commit();

            // Set the visibility of bottom nav based on the restored fragment
            switch (fragmentTag) {
                case "HomeFragment":
                    navigationBarView.setVisibility(View.VISIBLE);
                    break;
                case "SignupFragment":
                    navigationBarView.setVisibility(View.GONE);
                    break;
                // Add other cases for other fragments if necessary
            }
        }
        toggle.syncState();
    }

}