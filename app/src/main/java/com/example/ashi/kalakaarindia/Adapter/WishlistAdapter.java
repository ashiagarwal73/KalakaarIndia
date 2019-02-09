package com.example.ashi.kalakaarindia.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ashi.kalakaarindia.Model.Product;
import com.example.ashi.kalakaarindia.Model.User;
import com.example.ashi.kalakaarindia.R;
import com.example.ashi.kalakaarindia.Utility.UserDetails;

import java.util.List;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.MyViewHolder> {
    List<Product> products;
    private Listener listener;

    public WishlistAdapter(Listener listener)
    {
        this.listener=listener;
        if(UserDetails.getAppUser()!=null)
            products=UserDetails.getAppUser().getFav_product();
    }
    
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.wishlist_item, viewGroup, false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }
    
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.wishlist_item_name.setText(products.get(i).getProduct_name());
        if(products.get(i).getSelected_size().equals(""))
        {
            myViewHolder.wishlist_item_size.setVisibility(View.GONE);
        }
        myViewHolder.wishlist_item_size.setText("Size : "+products.get(i).getSelected_size());
        myViewHolder.wishlist_item_price.setText("Price : ₹"+products.get(i).getProduct_price());
        Glide.with(myViewHolder.itemView.getContext())
                .load(products.get(i).getProduct_image())
                .into(myViewHolder.wishlist_item_image);
        myViewHolder.wishlist_item_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                products.remove(products.get(i));
                notifyDataSetChanged();
                listener.notifyFragmentProductChange();
            }
        });
        myViewHolder.wishlist_item_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user=UserDetails.getAppUser();
                boolean containsProductAlready=false;
                for(Product existingProduct:user.getCart_product()) {
                    if (existingProduct.getProduct_name().equals(products.get(i).getProduct_name())) {
                        containsProductAlready = true;
                    }
                }
                if(!containsProductAlready)
                {
                    UserDetails.getAppUser().addCartProduct(products.get(i),products.get(i).getSelected_size(),1);
                }
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

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView wishlist_item_name,wishlist_item_size,wishlist_item_price,wishlist_item_move,wishlist_item_remove;
        ImageView wishlist_item_image;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            wishlist_item_name=itemView.findViewById(R.id.wishlist_item_name);
            wishlist_item_size=itemView.findViewById(R.id.wishlist_item_size);
            wishlist_item_price=itemView.findViewById(R.id.wishlist_item_price);
            wishlist_item_move=itemView.findViewById(R.id.wishlist_item_move);
            wishlist_item_remove=itemView.findViewById(R.id.wishlist_item_remove);
            wishlist_item_image=itemView.findViewById(R.id.wishlist_item_image);
        }
    }
    public interface Listener{
       void notifyFragmentProductChange();
    }
}
