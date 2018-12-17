package am.aua.com.dbproject;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import am.aua.com.dbproject.Fragments.AuthorsFragment;
import am.aua.com.dbproject.Fragments.BlackListFragment;
import am.aua.com.dbproject.Fragments.BorrowsFragment;
import am.aua.com.dbproject.Fragments.CategoriesFragment;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FrameLayout fragmentPlaceHolder;
    FragmentManager manager;
    CategoriesFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);

        navigationView.setNavigationItemSelectedListener(this);
        //New Code
        fragmentPlaceHolder = findViewById(R.id.fragmentPlaceHolder);
        manager = getSupportFragmentManager();

        fragment = new CategoriesFragment();
        manager.beginTransaction().replace(R.id.fragmentPlaceHolder, fragment).commit();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        int tag = -999;
        Fragment fragment = null;


        if (id == R.id.nav_books) {
            tag = CategoriesFragment.TAG;
            fragment = new CategoriesFragment();
            // Handle the camera action
        } else if (id == R.id.authors) {
            fragment = new AuthorsFragment();
            tag = AuthorsFragment.TAG;
        } else if (id == R.id.blackList) {
            fragment = new BlackListFragment();
            tag = BlackListFragment.TAG;
        } else if (id == R.id.borrows) {
            fragment = new BorrowsFragment();
            tag = BorrowsFragment.TAG;
        }
        manager.beginTransaction().replace(R.id.fragmentPlaceHolder, fragment, Integer.toString(tag)).commit();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        item.setChecked(true);
        return true;
    }
}
