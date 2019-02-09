package com.agarwal.ashi.kalakaarindia.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.agarwal.ashi.kalakaarindia.Model.Categories;
import com.agarwal.ashi.kalakaarindia.Model.Product;
import com.agarwal.ashi.kalakaarindia.R;

import java.util.List;

public class GridRecyclerViewAdapter extends RecyclerView.Adapter<GridRecyclerViewAdapter.MyViewHolder> {
    Listener listener;
    List<Categories> categories;
    public GridRecyclerViewAdapter(Listener listener, List<Categories> categories){
        this.listener=listener;
        this.categories=categories;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.grid_recycler_view_item, viewGroup, false);
        MyViewHolder myViewHolder=new MyViewHolder(view,listener);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder,final int i) {
        myViewHolder.category_text_view.setText(categories.get(i).getName());
        Glide.with(myViewHolder.itemView.getContext()).load(categories.get(i).getImage()).into(myViewHolder.category_image_view);
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onGridRecyclerViewItemClicked(categories.get(i).getImage(),categories.get(i).getProducts());
            }
        });
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView category_text_view;
        ImageView category_image_view;
        public MyViewHolder(@NonNull View itemView,Listener listener) {
            super(itemView);
            category_image_view=itemView.findViewById(R.id.category_image_view);
            category_text_view=itemView.findViewById(R.id.category_text_view);
        }
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public interface Listener{
        public void onGridRecyclerViewItemClicked(String top_image, List<Product> products);
    }
}
