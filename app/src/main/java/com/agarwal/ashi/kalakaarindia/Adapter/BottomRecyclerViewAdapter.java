package com.agarwal.ashi.kalakaarindia.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.agarwal.ashi.kalakaarindia.Model.Product;
import com.agarwal.ashi.kalakaarindia.R;

import java.util.List;

public class BottomRecyclerViewAdapter extends RecyclerView.Adapter<BottomRecyclerViewAdapter.MyViewHolder>
{
    List<Product> trending_products;
    BottomRecyclerViewListener bottomRecyclerViewListener;
    public BottomRecyclerViewAdapter(List<Product> trending_products,BottomRecyclerViewListener bottomRecyclerViewListener) {
        this.trending_products=trending_products;
        this.bottomRecyclerViewListener=bottomRecyclerViewListener;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageview_treding_product;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageview_treding_product=itemView.findViewById(R.id.imageview_treding_product);
        }
    }

    @NonNull
    @Override
    public BottomRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bottom_recycler_view_item, viewGroup, false);
        BottomRecyclerViewAdapter.MyViewHolder myViewHolder=new BottomRecyclerViewAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        Glide.with(myViewHolder.itemView.getContext()).load(trending_products.get(i).getProduct_image()).into(myViewHolder.imageview_treding_product);
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomRecyclerViewListener.onProductClicked(trending_products.get(i));
            }
        });
    }


    @Override
    public int getItemCount() {
        return trending_products.size();
    }

    public interface BottomRecyclerViewListener{
        void onProductClicked(Product product);
    }
}
