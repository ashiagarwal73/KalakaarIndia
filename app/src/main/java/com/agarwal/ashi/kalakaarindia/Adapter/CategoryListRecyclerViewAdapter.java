package com.agarwal.ashi.kalakaarindia.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.agarwal.ashi.kalakaarindia.Model.Product;
import com.agarwal.ashi.kalakaarindia.R;

import java.util.List;

public class CategoryListRecyclerViewAdapter extends RecyclerView.Adapter<CategoryListRecyclerViewAdapter.MyViewHolder> {
    private List<Product> trending_product;
    private CategoryListRecyclerViewListener categoryListRecyclerViewListener;
    CategoryListRecyclerViewAdapter(List<Product> trending_product,CategoryListRecyclerViewListener categoryListRecyclerViewListener) {
        this.trending_product=trending_product;
        this.categoryListRecyclerViewListener=categoryListRecyclerViewListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_list_recycler_view_item, viewGroup, false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder,final int i) {
        myViewHolder.product_name.setText(trending_product.get(i).getProduct_name());
        myViewHolder.product_price.setText("₹"+trending_product.get(i).getProduct_price());
        Glide.with(myViewHolder.product_image.getContext()).load(trending_product.get(i).getProduct_image()).into(myViewHolder.product_image);
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryListRecyclerViewListener.onProductClicked(trending_product.get(i));
            }
        });
    }

    @Override
    public int getItemCount() {
        return trending_product.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView product_name,product_price;
        ImageView product_image;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            product_name=itemView.findViewById(R.id.product_name);
            product_price=itemView.findViewById(R.id.product_price);
            product_image=itemView.findViewById(R.id.product_image);
        }
    }
    public interface CategoryListRecyclerViewListener{
        public void onProductClicked(Product product);
    }
}
