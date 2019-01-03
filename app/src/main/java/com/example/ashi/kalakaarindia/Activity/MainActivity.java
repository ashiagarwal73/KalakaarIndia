package com.example.ashi.kalakaarindia.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.ashi.kalakaarindia.Adapter.GridRecyclerViewAdapter;
import com.example.ashi.kalakaarindia.Adapter.TopRecyclerViewAdapter;
import com.example.ashi.kalakaarindia.Fragment.CategoryFragment;
import com.example.ashi.kalakaarindia.Fragment.HomeFragment;
import com.example.ashi.kalakaarindia.Fragment.ProductsCategoryFragment;
import com.example.ashi.kalakaarindia.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,TopRecyclerViewAdapter.Listener,GridRecyclerViewAdapter.Listener {

    private HomeFragment homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        homeFragment=new HomeFragment();
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment,homeFragment);
        fragmentTransaction.commit();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {
                if(v>0.5){
                    TextView textView=findViewById(R.id.blur);
                    textView.setVisibility(View.VISIBLE);
                }
                else {
                    TextView textView=findViewById(R.id.blur);
                    textView.setVisibility(View.GONE);
                }

            }

            @Override
            public void onDrawerOpened(@NonNull View view) {
                TextView textView=findViewById(R.id.blur);
                textView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onDrawerClosed(@NonNull View view) {
                TextView textView=findViewById(R.id.blur);
                textView.setVisibility(View.GONE);            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
//        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment);
//        if(currentFragment instanceof ProductsCategoryFragment)
//        {
//            homeFragment=new HomeFragment();
//            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.fragment,homeFragment);
//            fragmentTransaction.commit();
//        }
        else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.search) {
            return true;
        }
        if (id == R.id.notification) {
            return true;
        }if (id == R.id.wishlist) {
            return true;
        }if (id == R.id.shopping_bag) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getGroupId();

        if (id == R.id.group_divider) {
            Intent intent=new Intent(MainActivity.this,ProductCategoryActivity.class);
            intent.putExtra("category",item.getTitle());
            startActivity(intent);
        } else if (id == R.id.group_divider2) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onTopRecyclerViewItemClicked(String category) {
        Intent intent=new Intent(MainActivity.this,ProductCategoryActivity.class);
        intent.putExtra("category",category);
        startActivity(intent);
    }

    @Override
    public void onGridRecyclerViewItemClicked(String category) {
        Intent intent=new Intent(MainActivity.this,ProductCategoryActivity.class);
        intent.putExtra("category",category);
        startActivity(intent);
    }
}
