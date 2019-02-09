package com.example.ashi.kalakaarindia.Fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ashi.kalakaarindia.Adapter.CartAdapter;
import com.example.ashi.kalakaarindia.Model.Product;
import com.example.ashi.kalakaarindia.R;
import com.example.ashi.kalakaarindia.Utility.UserDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment implements CartAdapter.Listener {


    public CartFragment() {
        // Required empty public constructor
    }
    ImageView emptyCart;
    RecyclerView cartRecyclerView;
    LinearLayout price_layout,cart_checkout;
    TextView cart_total_price;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_cart, container, false);
        emptyCart=view.findViewById(R.id.empty_cart);
        cartRecyclerView=view.findViewById(R.id.cart_recycler_view);
        price_layout=view.findViewById(R.id.price_layout);
        cart_total_price=view.findViewById(R.id.cart_total_price);
        cart_checkout=view.findViewById(R.id.cart_checkout);
        cart_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaymentFragment paymentFragment=new PaymentFragment();
                FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment,paymentFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        if(UserDetails.getAppUser()!=null)
            notifyFragmentProductChange();
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        CartAdapter cartAdapter=new CartAdapter(this);
        cartRecyclerView.setAdapter(cartAdapter);
        return view;
    }

    @Override
    public void notifyFragmentProductChange () {
        List<Product> products=UserDetails.getAppUser().getCart_product();
        if(!products.isEmpty())
        {
            int sum=0;
            for(int i=0;i<products.size();i++)
            {
                sum=sum+(products.get(i).getProduct_price()*products.get(i).getProduct_quantity());
            }
            cart_total_price.setText("₹"+sum);
            cartRecyclerView.setVisibility(View.VISIBLE);
            emptyCart.setVisibility(View.GONE);
            price_layout.setVisibility(View.VISIBLE);
        }
        else {
            cartRecyclerView.setVisibility(View.GONE);
            emptyCart.setVisibility(View.VISIBLE);
            price_layout.setVisibility(View.GONE);
        }
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(UserDetails.getUser().getUid());
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Updating Cart....");
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
