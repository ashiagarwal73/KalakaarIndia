package com.example.ashi.kalakaarindia.Activity;

import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.ashi.kalakaarindia.Adapter.CategoryRecyclerViewAdapter;
import com.example.ashi.kalakaarindia.Fragment.CategoryFragment;
import com.example.ashi.kalakaarindia.Fragment.ProductsCategoryFragment;
import com.example.ashi.kalakaarindia.R;

import java.util.Objects;

public class ProductCategoryActivity extends AppCompatActivity implements CategoryRecyclerViewAdapter.CategoryRecyclerViewListener {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_product_category);
        CategoryFragment categoryFragment =new CategoryFragment();
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        Bundle bundle=new Bundle();
        bundle.putString("category",getIntent().getStringExtra("category"));
        categoryFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragment, categoryFragment);
        fragmentTransaction.commit();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onSeeAllClicked(String category, String subCategory) {
        ProductsCategoryFragment productsCategoryFragment =new ProductsCategoryFragment();
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        Bundle bundle=new Bundle();
        bundle.putString("category",category);
        bundle.putString("subCategory",subCategory);
        productsCategoryFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragment, productsCategoryFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
