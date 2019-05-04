package com.agarwal.ashi.kalakaarindia.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.agarwal.ashi.kalakaarindia.Adapter.BottomRecyclerViewAdapter;
import com.agarwal.ashi.kalakaarindia.Adapter.CategoryRecyclerViewAdapter;
import com.agarwal.ashi.kalakaarindia.Adapter.GridRecyclerViewAdapter;
import com.agarwal.ashi.kalakaarindia.Adapter.ProductRecyclerViewAdapter;
import com.agarwal.ashi.kalakaarindia.Adapter.TopRecyclerViewAdapter;
import com.agarwal.ashi.kalakaarindia.Fragment.CartFragment;
import com.agarwal.ashi.kalakaarindia.Fragment.CategoryFragment;
import com.agarwal.ashi.kalakaarindia.Fragment.HomeFragment;
import com.agarwal.ashi.kalakaarindia.Fragment.NotificationFragment;
import com.agarwal.ashi.kalakaarindia.Fragment.PaymentFragment;
import com.agarwal.ashi.kalakaarindia.Fragment.ProductDetailsFragment;
import com.agarwal.ashi.kalakaarindia.Fragment.ProductsCategoryFragment;
import com.agarwal.ashi.kalakaarindia.Fragment.TeamFragment;
import com.agarwal.ashi.kalakaarindia.Model.Categories;
import com.agarwal.ashi.kalakaarindia.Model.CategoryPageItemModel;
import com.agarwal.ashi.kalakaarindia.Model.HomePageModel;
import com.agarwal.ashi.kalakaarindia.Model.Product;
import com.agarwal.ashi.kalakaarindia.Model.State;
import com.agarwal.ashi.kalakaarindia.Model.StatePageModel;
import com.agarwal.ashi.kalakaarindia.Model.User;
import com.agarwal.ashi.kalakaarindia.R;
import com.agarwal.ashi.kalakaarindia.Utility.UserDetails;
import com.agarwal.ashi.kalakaarindia.Fragment.WishlistFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.razorpay.PaymentResultListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        TopRecyclerViewAdapter.Listener,
        GridRecyclerViewAdapter.Listener,
        CategoryRecyclerViewAdapter.CategoryRecyclerViewListener,
        ProductRecyclerViewAdapter.ProductRecyclerViewListener,
        BottomRecyclerViewAdapter.BottomRecyclerViewListener,
        CategoryFragment.CategoryFragmentListerner {

    private HomeFragment homeFragment;
    TextView button,textView;
    Menu navigationMenu;
    private String TAG="FirebaseToken";

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
        String fragment = ""+getIntent().getStringExtra("fragment");
        if(fragment.equals("Notification"))
        {
            getSupportFragmentManager().popBackStackImmediate();
            NotificationFragment notificationFragment=new NotificationFragment();
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment,notificationFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
        else {
            homeFragment=new HomeFragment();
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment,homeFragment);
            fragmentTransaction.commit();
        }
        handlingNotifications();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
         button=navigationView.getHeaderView(0).findViewById(R.id.login_button);
         textView=navigationView.getHeaderView(0).findViewById(R.id.login_text);
         navigationMenu=navigationView.getMenu();
         setNavigationBarHeaderDetails();
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


    private void handlingNotifications() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }
                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        Log.d(TAG, token);
                        //Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    void setNavigationBarHeaderDetails()
    {

        final FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        if(UserDetails.getUser()!=null&&UserDetails.getAppUser()!=null)
        {
            button.setVisibility(View.GONE);
            textView.setText(String.format("Hey,%s", UserDetails.getAppUser().getName()));
            MenuItem item=navigationMenu.findItem(R.id.signout);
            item.setVisible(true);
        }

        else if(firebaseUser!=null)
        {
            DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("users").child(firebaseUser.getUid());
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    User user=dataSnapshot.getValue(User.class);
                    button.setVisibility(View.GONE);
                    textView.setText(String.format("Hey,%s", user.getName()));
                    UserDetails.setUser(firebaseUser);
                    UserDetails.setAppUser(user);
                    MenuItem item=navigationMenu.findItem(R.id.signout);
                    item.setVisible(true);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        else {
            button.setVisibility(View.VISIBLE);
            textView.setText(String.format("Hey,%s", "there!"));
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.search) {
//            getSupportFragmentManager().popBackStackImmediate();
//            SearchFragment searchFragment=new SearchFragment();
//            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.fragment,searchFragment);
//            fragmentTransaction.addToBackStack(null);
//            fragmentTransaction.commit();
            Intent intent=new Intent(MainActivity.this,SearchActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.notification) {
            getSupportFragmentManager().popBackStackImmediate();
            NotificationFragment notificationFragment=new NotificationFragment();
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment,notificationFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            return true;
        }if (id == R.id.wishlist) {
            getSupportFragmentManager().popBackStackImmediate();
            WishlistFragment wishlistFragment=new WishlistFragment();
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment,wishlistFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            return true;
        }if (id == R.id.shopping_bag) {
            getSupportFragmentManager().popBackStackImmediate();
            CartFragment cartFragment=new CartFragment();
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment,cartFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getGroupId();

        if (id == R.id.group_divider) {
            getSupportFragmentManager().popBackStackImmediate();
            CategoryFragment categoryFragment =new CategoryFragment();
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            Bundle bundle=new Bundle();
            bundle.putString("category",item.getTitle().toString());
            categoryFragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.fragment, categoryFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } else if (id == R.id.group_divider2) {
            int itemId=item.getItemId();
            if(itemId==R.id.signout)
            {
                FirebaseAuth.getInstance().signOut();
                UserDetails.setAppUser(null);
                UserDetails.setUser(null);
                setNavigationBarHeaderDetails();
                item.setVisible(false);
                homeFragment=new HomeFragment();
                FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment,homeFragment);
                fragmentTransaction.commit();

            }
            if(itemId==R.id.wishlist)
            {
                getSupportFragmentManager().popBackStackImmediate();
                WishlistFragment wishlistFragment=new WishlistFragment();
                FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment,wishlistFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
            if(itemId==R.id.team)
            {
                getSupportFragmentManager().popBackStackImmediate();
                TeamFragment teamFragment=new TeamFragment();
                FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment,teamFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onTopRecyclerViewItemClicked(String category) {
        CategoryFragment categoryFragment =new CategoryFragment();
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        Bundle bundle=new Bundle();
        bundle.putString("category",category);
        categoryFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragment, categoryFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onGridRecyclerViewItemClicked(String top_image, List<Product> productList) {
        ProductsCategoryFragment productsCategoryFragment =new ProductsCategoryFragment();
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        Bundle bundle=new Bundle();
        bundle.putString("top_image",top_image);
        bundle.putSerializable("products", (Serializable) productList);
        productsCategoryFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragment, productsCategoryFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onProductClicked(Product product) {
        ProductDetailsFragment productDetailsFragment=new ProductDetailsFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable("product", product);
        productDetailsFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment,productDetailsFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        System.out.print("ProductClicked");
    }
    @Override
    public void onSeeAllClicked(List<Product> productList,String top_image) {

        ProductsCategoryFragment productsCategoryFragment =new ProductsCategoryFragment();
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        Bundle bundle=new Bundle();
        bundle.putString("top_image",top_image);
        bundle.putSerializable("products", (Serializable) productList);
        productsCategoryFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragment, productsCategoryFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
