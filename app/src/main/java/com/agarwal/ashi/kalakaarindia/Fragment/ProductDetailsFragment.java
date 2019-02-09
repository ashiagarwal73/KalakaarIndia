package com.agarwal.ashi.kalakaarindia.Fragment;


import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.agarwal.ashi.kalakaarindia.Adapter.ViewPagerAdapter;
import com.agarwal.ashi.kalakaarindia.Model.Product;
import com.agarwal.ashi.kalakaarindia.Model.User;
import com.agarwal.ashi.kalakaarindia.R;
import com.agarwal.ashi.kalakaarindia.Utility.UserDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailsFragment extends Fragment {


    public ProductDetailsFragment() {
        // Required empty public constructor
    }

    ViewPager viewPager;
    TabLayout tabLayout;
    RadioGroup size_radiogroup;
    RadioButton lastRadioButton = null;
    Product product;
    String selectedSize="";
    LinearLayout add_to_wishlist,add_to_cart;
    ImageView nextArrow;
    TextView product_name,product_price,product_details,product_size_text;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null)
        {
             product= (Product) getArguments().getSerializable("product");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_product_details, container, false);
        viewPager=view.findViewById(R.id.viewPager);
        tabLayout=view.findViewById(R.id.dot_layout);
        size_radiogroup=view.findViewById(R.id.size_radiogroup);
        product_name=view.findViewById(R.id.product_name);
        product_price=view.findViewById(R.id.product_price);
        product_details=view.findViewById(R.id.product_details);
        product_size_text=view.findViewById(R.id.product_size_text);
        add_to_wishlist=view.findViewById(R.id.add_to_wishlist);
        add_to_cart=view.findViewById(R.id.add_to_cart);
        nextArrow=view.findViewById(R.id.next_arrow);
        tabLayout.setupWithViewPager(viewPager);
        List<String> images=new ArrayList<>();
        images.add(product.getProduct_image());
        images.add(product.getProduct_image_2());
        images.add(product.getProduct_image_3());
        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getChildFragmentManager(),images);
        viewPager.setAdapter(viewPagerAdapter);
        size_radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton=view.findViewById(checkedId);
                radioButton.setTextColor(getResources().getColor(R.color.colorWhite));
                if(lastRadioButton!=null)
                {
                    lastRadioButton.setTextColor(Color.BLACK);

                }
                selectedSize=radioButton.getText().toString();
                lastRadioButton=radioButton;
            }
        });
        nextArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewPager.setCurrentItem(tabLayout.getSelectedTabPosition()+1);
            }
        });
        product_name.setText(product.getProduct_name());
        product_price.setText("₹"+product.getProduct_price());
        product_details.setText(Html.fromHtml(product.getProduct_details()));
        product_size_text.setText(product.getProduct_size_text());
        if(product.getProduct_size_text()==null||product.getProduct_size_text().equals("")){
            product_size_text.setVisibility(View.GONE);
            size_radiogroup.setVisibility(View.GONE);
        }
        for (int i=0;i<product.getSize().size();i++)
        {
            RadioButton radioButton= (RadioButton) size_radiogroup.getChildAt(i);
            radioButton.setText(product.getSize().get(i));
        }
        add_to_wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedSize.equals("")&&product.getProduct_size_text()!=null)
                {
                    Toast.makeText(getContext(), "Please Select a Size", Toast.LENGTH_SHORT).show();
                    return;
                }
                final DialogFragment dialogFragment=new DialogFragment();
               final Bundle bundle=new Bundle();
                if(UserDetails.getAppUser()==null)
                {
                    bundle.putInt("image",R.drawable.pleaselogin);
                    dialogFragment.setArguments(bundle);
                    dialogFragment.show(getActivity().getSupportFragmentManager(),null);
                }
               else {
                    User user=UserDetails.getAppUser();
                    boolean containsProductAlready=false;
                    for(Product existingProduct:user.getFav_product()) {
                        if (existingProduct.getProduct_name().equals(product.getProduct_name())) {
                            containsProductAlready = true;
                        }
                    }
                        if (!containsProductAlready) {
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(UserDetails.getUser().getUid());
                            UserDetails.getAppUser().addFavProduct(product,selectedSize,1);
                            final ProgressDialog progressDialog = new ProgressDialog(getContext());
                            progressDialog.setMessage("Adding...");
                            progressDialog.show();
                            databaseReference.setValue(UserDetails.getAppUser()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        bundle.putInt("image", R.drawable.addedtowishlist);
                                        dialogFragment.setArguments(bundle);
                                        dialogFragment.show(getActivity().getSupportFragmentManager(), null);
                                        progressDialog.dismiss();
                                    }
                                }
                            });
                        } else {
                            bundle.putInt("image", R.drawable.addedtowishlist);
                            dialogFragment.setArguments(bundle);
                            dialogFragment.show(getActivity().getSupportFragmentManager(), null);
                        }
                    }
            }
        });
        add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedSize.equals("")&&product.getProduct_size_text()!=null) {
                    Toast.makeText(getContext(), "Please Select a Size", Toast.LENGTH_SHORT).show();
                    return;
                }
                final DialogFragment dialogFragment=new DialogFragment();
                final Bundle bundle=new Bundle();
                if(UserDetails.getAppUser()==null) {
                    bundle.putInt("image",R.drawable.pleaselogin);
                    dialogFragment.setArguments(bundle);
                    dialogFragment.show(getActivity().getSupportFragmentManager(),null);
                }
                else {
                    User user=UserDetails.getAppUser();
                    boolean containsProductAlready=false;
                    for(Product existingProduct:user.getCart_product()) {
                        if (existingProduct.getProduct_name().equals(product.getProduct_name())) {
                            containsProductAlready = true;
                        }
                    }
                    if(!containsProductAlready)
                    {
                        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("users").child(UserDetails.getUser().getUid());
                        UserDetails.getAppUser().addCartProduct(product,selectedSize,1);
                        final ProgressDialog progressDialog=new ProgressDialog(getContext());
                        progressDialog.setMessage("Adding...");
                        progressDialog.show();
                        databaseReference.setValue(UserDetails.getAppUser()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    bundle.putInt("image",R.drawable.addedtocart);
                                    dialogFragment.setArguments(bundle);
                                    dialogFragment.show(getActivity().getSupportFragmentManager(),null);
                                    progressDialog.dismiss();
                                    }
                            }
                            });
                        } else {
                            bundle.putInt("image",R.drawable.addedtocart);
                            dialogFragment.setArguments(bundle);
                            dialogFragment.show(getActivity().getSupportFragmentManager(),null);
                    }
                }
            }
        });
        return view;
    }

}
