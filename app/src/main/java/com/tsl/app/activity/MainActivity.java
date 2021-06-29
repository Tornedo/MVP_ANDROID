package com.tsl.app.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.tsl.app.R;
import com.tsl.app.databinding.ActivityMainBinding;
import com.tsl.app.fragment.HomeFragment;
import com.tsl.app.presenter.LoginPresenter;


public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ActivityMainBinding binding;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private FragmentManager fragmentManager;
    MenuItem messageView;
    public static Boolean inBackground = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContext(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        messageView = binding.navView.getMenu().findItem(R.id.nav_main_home);

        toolbar = binding.toolbar;
        drawer = binding.drawerLayout;
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        binding.navView.setNavigationItemSelectedListener(this);
        fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.content_main, HomeFragment.newInstance());
        transaction.disallowAddToBackStack();
        transaction.commit();
    }


    public void onStop() {
        super.onStop();
        inBackground = true;
    }


    public void onResume() {
        super.onResume();
        inBackground = false;
    }




    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO: Hide Creditpoint menu icon : 30.03.2016
        getMenuInflater().inflate(R.menu.main, menu);
        //setCreditPointMenuItem(menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:

                if (!drawer.isDrawerOpen(binding.drawerLayout))
                    drawer.openDrawer(GravityCompat.START);
                else
                    drawer.closeDrawers();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        keypadOffonStart();
        Fragment fragment = null;

        int id = item.getItemId();

        if (id == R.id.nav_main_home) {
            drawer.closeDrawers();
            fragment = HomeFragment.newInstance();
        } else if (id == R.id.nav_all_message) {
            startActivity(ViewManager.getActiveItemsActivity(this));
        }

        if (fragment != null) {
            showFragment(fragment);
        }
        item.setChecked(true);
        // toolbar.setTitle(item.getTitle());
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showFragment(Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content_main, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
