package com.agarwal.ashi.kalakaarindia.Fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.agarwal.ashi.kalakaarindia.Adapter.WishlistAdapter;
import com.agarwal.ashi.kalakaarindia.Model.Product;
import com.agarwal.ashi.kalakaarindia.R;
import com.agarwal.ashi.kalakaarindia.Utility.UserDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class WishlistFragment extends Fragment implements WishlistAdapter.Listener {


    public WishlistFragment() {
        // Required empty public constructor
    }
    ImageView emptyWishlist;
    RecyclerView wishlistRecyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_wishlist, container, false);
        emptyWishlist=view.findViewById(R.id.empty_wishlist);
        wishlistRecyclerView=view.findViewById(R.id.wishlist_recycler_view);
        if(UserDetails.getAppUser()!=null)
            notifyFragmentProductChange();
        wishlistRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        WishlistAdapter wishlistAdapter=new WishlistAdapter(this);
        wishlistRecyclerView.setAdapter(wishlistAdapter);

        return view;
    }

    @Override
    public void notifyFragmentProductChange() {
        List<Product> products=UserDetails.getAppUser().getFav_product();
        List<Product> cart_products=UserDetails.getAppUser().getCart_product();
        if(!products.isEmpty())
        {
            wishlistRecyclerView.setVisibility(View.VISIBLE);
            emptyWishlist.setVisibility(View.GONE);
        }
        else if(!cart_products.isEmpty())
        {
            wishlistRecyclerView.setVisibility(View.GONE);
            emptyWishlist.setVisibility(View.VISIBLE);
        }
        else {
            wishlistRecyclerView.setVisibility(View.GONE);
            emptyWishlist.setVisibility(View.VISIBLE);
        }
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(UserDetails.getUser().getUid());
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Updating Wishlist....");
        progressDialog.show();
        databaseReference.setValue(UserDetails.getAppUser()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                }
            }
        });
    }
}
