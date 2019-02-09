package com.agarwal.ashi.kalakaarindia.Activity;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.agarwal.ashi.kalakaarindia.Adapter.ProductRecyclerViewAdapter;
import com.agarwal.ashi.kalakaarindia.Fragment.ProductDetailsFragment;
import com.agarwal.ashi.kalakaarindia.Model.Product;
import com.agarwal.ashi.kalakaarindia.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class SearchActivity extends AppCompatActivity implements ProductRecyclerViewAdapter.ProductRecyclerViewListener {
List<Product> products=new ArrayList<>();
ImageView empty_search;
RecyclerView search_recyclerview;
FrameLayout frameLayout;
ConstraintLayout constraintLayout;
ProductRecyclerViewAdapter productRecyclerViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        frameLayout=findViewById(R.id.fragment);
        constraintLayout=findViewById(R.id.constraintLayout);
        SearchView searchView=findViewById(R.id.searchView);
        searchView.setFocusable(true);
        searchView.setIconified(false);
        ImageView back_button=findViewById(R.id.back_button);
        empty_search=findViewById(R.id.empty_search);
        search_recyclerview=findViewById(R.id.search_recyclerview);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        productRecyclerViewAdapter=new ProductRecyclerViewAdapter(products,this);
        productRecyclerViewAdapter.setProducts(products);
        search_recyclerview.setLayoutManager(new GridLayoutManager(this,3));
        search_recyclerview.setAdapter(productRecyclerViewAdapter);
        updateUI(products);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                if(s.equals(""))
                {
                    productRecyclerViewAdapter.setProducts(products);
                    productRecyclerViewAdapter.notifyDataSetChanged();
                    updateUI(products);
                    return true;
                }
                final List<Product> filteredModelList = filter(products, s);
                productRecyclerViewAdapter.setProducts(filteredModelList);
                productRecyclerViewAdapter.notifyDataSetChanged();
                updateUI(filteredModelList);
                return true;
            }
        });
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.show();
        if(products==null||products.isEmpty())
        {
            DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("category_page");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for( DataSnapshot categories:dataSnapshot.getChildren())
                    {
                        for(DataSnapshot lists:categories.getChildren())
                        {
                            if(lists.child("products").exists())
                            {
                                for (DataSnapshot dataSnapshot1:lists.child("products").getChildren())
                                {
                                    products.add(dataSnapshot1.getValue(Product.class));
                                }
                            }
                        }
                    }
                    updateUI(products);
                    productRecyclerViewAdapter.setProducts(products);
                    productRecyclerViewAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
    private static List<Product> filter(List<Product> models, String query) {
        final List<Product> filteredModelList = new ArrayList<>();
        for (Product model : models) {

            if (model.getProduct_name().toLowerCase().contains(query.toLowerCase())) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }
    void updateUI(List<Product> filteredModelList){
        frameLayout.setVisibility(View.GONE);
        constraintLayout.setVisibility(View.VISIBLE);
        if(filteredModelList==null||filteredModelList.isEmpty()) {
            empty_search.setVisibility(View.VISIBLE);
            search_recyclerview.setVisibility(View.GONE);
        }
        else {
            empty_search.setVisibility(View.GONE);
            search_recyclerview.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onProductClicked(Product product) {
        frameLayout.setVisibility(View.VISIBLE);
        constraintLayout.setVisibility(View.GONE);
        search_recyclerview.setVisibility(View.GONE);
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
    public void onBackPressed() {
        updateUI(products);
        super.onBackPressed();

    }
}
