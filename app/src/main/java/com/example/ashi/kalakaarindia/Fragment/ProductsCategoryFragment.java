package com.example.ashi.kalakaarindia.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ashi.kalakaarindia.Adapter.ProductRecyclerViewAdapter;
import com.example.ashi.kalakaarindia.R;

public class ProductsCategoryFragment extends Fragment {
    private static final String CATEGORY = "category";

    private String category;

    RecyclerView productRecyclerView;

    public ProductsCategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = getArguments().getString(CATEGORY);
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_products_category, container, false);

        productRecyclerView=view.findViewById(R.id.product_recycler_view);
        productRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        ProductRecyclerViewAdapter productRecyclerViewAdapter=new ProductRecyclerViewAdapter();
        productRecyclerView.setAdapter(productRecyclerViewAdapter);
        return view;
    }


}
