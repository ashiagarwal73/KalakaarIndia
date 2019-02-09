package com.agarwal.ashi.kalakaarindia.Adapter;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.agarwal.ashi.kalakaarindia.Model.Product;
import com.agarwal.ashi.kalakaarindia.R;
import com.agarwal.ashi.kalakaarindia.Utility.UserDetails;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    Listener listener;
    List<Product> products;
    public CartAdapter(Listener listener){
        if(UserDetails.getAppUser()!=null)
       products=UserDetails.getAppUser().getCart_product();
        this.listener=listener;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_item, viewGroup, false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        myViewHolder.cart_item_name.setText(products.get(i).getProduct_name());
        if(products.get(i).getSelected_size().equals(""))
        {
            myViewHolder.cart_item_size.setVisibility(View.GONE);
        }
        myViewHolder.cart_item_size.setText("Size : "+products.get(i).getSelected_size());

        myViewHolder.cart_item_price.setText("Price : ₹"+products.get(i).getProduct_price());
        myViewHolder.cart_item_quantity.setText(""+products.get(i).getProduct_quantity());
        Glide.with(myViewHolder.itemView.getContext())
                .load(products.get(i).getProduct_image())
                .into(myViewHolder.cart_item_image);

        myViewHolder.cart_item_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                products.get(i).setProduct_quantity(products.get(i).getProduct_quantity()+1);
                myViewHolder.cart_item_quantity.setText(""+products.get(i).getProduct_quantity());
                listener.notifyFragmentProductChange();
            }
        });
        myViewHolder.cart_item_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                products.get(i).setProduct_quantity(products.get(i).getProduct_quantity()-1);
                myViewHolder.cart_item_quantity.setText(""+products.get(i).getProduct_quantity());
                listener.notifyFragmentProductChange();
            }
        });
        myViewHolder.cart_item_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                products.remove(products.get(i));
                notifyDataSetChanged();
                listener.notifyFragmentProductChange();
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView cart_item_name,cart_item_size,cart_item_price,cart_item_quantity,cart_item_remove;
        ImageView cart_item_image;
        FloatingActionButton cart_item_plus,cart_item_minus;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cart_item_name=itemView.findViewById(R.id.cart_item_name);
            cart_item_size=itemView.findViewById(R.id.cart_item_size);
            cart_item_price=itemView.findViewById(R.id.cart_item_price);
            cart_item_quantity=itemView.findViewById(R.id.cart_item_quantity);
            cart_item_remove=itemView.findViewById(R.id.cart_item_remove);
            cart_item_image=itemView.findViewById(R.id.cart_item_image);
            cart_item_plus=itemView.findViewById(R.id.cart_item_plus);
            cart_item_minus=itemView.findViewById(R.id.cart_item_minus);
        }
    }
    public interface Listener{
        void notifyFragmentProductChange();
    }
}
