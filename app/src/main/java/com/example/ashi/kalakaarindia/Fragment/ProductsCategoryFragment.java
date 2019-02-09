package com.example.ashi.kalakaarindia.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.ashi.kalakaarindia.Adapter.ProductRecyclerViewAdapter;
import com.example.ashi.kalakaarindia.Model.Product;
import com.example.ashi.kalakaarindia.R;

import java.util.List;
import java.util.Objects;

public class ProductsCategoryFragment extends Fragment{
    private static final String PRODUCTS = "products";
    private static final String TOP_IMAGE = "top_image";

    private List<Product> products;
    String top_image;

    RecyclerView productRecyclerView;

    public ProductsCategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            products = (List<Product>) getArguments().getSerializable(PRODUCTS);
            top_image=getArguments().getString(TOP_IMAGE);
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_products_category, container, false);
        ImageView top_image_view=view.findViewById(R.id.top_image);
        Glide.with(Objects.requireNonNull(getContext())).load(top_image).into(top_image_view);
        productRecyclerView=view.findViewById(R.id.product_recycler_view);
        productRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        ProductRecyclerViewAdapter productRecyclerViewAdapter=new ProductRecyclerViewAdapter(products, (ProductRecyclerViewAdapter.ProductRecyclerViewListener) getActivity());
        productRecyclerView.setAdapter(productRecyclerViewAdapter);
        return view;
    }



}
