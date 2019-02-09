package com.agarwal.ashi.kalakaarindia.Fragment;


import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agarwal.ashi.kalakaarindia.Model.Product;
import com.agarwal.ashi.kalakaarindia.R;
import com.agarwal.ashi.kalakaarindia.Utility.UserDetails;

import java.util.List;


public class PaymentFragment extends Fragment {

    TextView price,payment_text_view,address_saved;
    LinearLayout cart_payment,address_layout;
    Button button_add_address,address_button_cancel,address_button_save;
    TextInputEditText address_pincode,address_locality,address_city,address_state;
    private String address="";

    public PaymentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_payment, container, false);
        initialiseViews(view);
        addOnClickListeners();
        List<Product> products =UserDetails.getAppUser().getCart_product();
        int sum=0;
        for(int i=0;i<products.size();i++)
        {
            sum=sum+(products.get(i).getProduct_price()*products.get(i).getProduct_quantity());
        }
        price.setText(""+sum);
        return view;
    }
    void initialiseViews(View view)
    {
        price=view.findViewById(R.id.cart_total_price);
        payment_text_view=view.findViewById(R.id.payment_text_view);
        cart_payment=view.findViewById(R.id.cart_payment);
        address_layout=view.findViewById(R.id.address_layout);
        button_add_address=view.findViewById(R.id.button_add_address);
        address_button_cancel=view.findViewById(R.id.address_button_cancel);
        address_button_save=view.findViewById(R.id.address_button_save);
        address_pincode=view.findViewById(R.id.address_pincode);
        address_locality=view.findViewById(R.id.address_locality);
        address_city=view.findViewById(R.id.address_city);
        address_state=view.findViewById(R.id.address_state);
        address_saved=view.findViewById(R.id.address_saved);
    }
    void addOnClickListeners()
    {
        address_button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_add_address.setVisibility(View.VISIBLE);
                address_layout.setVisibility(View.GONE);
            }
        });
        address_button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pincode=address_pincode.getText().toString().trim();
                String city=address_city.getText().toString().trim();
                String locality=address_locality.getText().toString().trim();
                String state=address_state.getText().toString().trim();
                if(pincode.equals("")||pincode.length()!=6) {
                    address_pincode.setError("Invalid Pincode");
                    return;
                }
                else if(city.equals("")) {
                    address_city.setError("Invalid City");
                    return;
                }
                else if(locality.equals("")) {
                    address_locality.setError("Invalid Locality");
                    return;
                }
                else if(state.equals("")) {
                    address_state.setError("Invalid State");
                    return;
                }
                address=pincode+"\n"+"\n"+locality+"\n"+"\n"+city+"\n"+"\n"+state+"\n"+"\nIndia";
                address_saved.setText(address);
                address_saved.setVisibility(View.VISIBLE);
                address_layout.setVisibility(View.GONE);
                payment_text_view.setTextColor(getResources().getColor(R.color.nice_blue));
                payment_text_view.setAlpha(1.0f);
            }
        });
        button_add_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_add_address.setVisibility(View.GONE);
                address_layout.setVisibility(View.VISIBLE);
            }
        });
        cart_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(address.equals("")) {

                }else {

                }
            }
        });

    }
}
