package com.agarwal.ashi.kalakaarindia.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.agarwal.ashi.kalakaarindia.Model.Order;
import com.agarwal.ashi.kalakaarindia.Model.User;
import com.agarwal.ashi.kalakaarindia.R;
import com.agarwal.ashi.kalakaarindia.Utility.UserDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

public class CompleteOrderActivity extends Activity implements PaymentResultListener {
    Order order=null;
    LinearLayout orderPlacedLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_order);
        orderPlacedLayout=findViewById(R.id.orderplaced);
        Checkout.preload(getApplicationContext());
        order= (Order) getIntent().getSerializableExtra("order");
        startPayment(order.getAmount(),"Order #123");
    }
    private void startPayment(String amount,String orderdesc) {
        Checkout checkout = new Checkout();
        checkout.setImage(R.drawable.log_out);
        JSONObject options = new JSONObject();
        try {
            options.put("description", orderdesc);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            options.put("Currency", "INR");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            options.put("amount", amount+"00");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        checkout.open(this, options);
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "Payment Successfull", Toast.LENGTH_SHORT).show();
        if(order!=null)
        {
            order.setOrder_id(s);
            order.setStatus("Confirmed");
            final ProgressDialog progressDialog=new ProgressDialog(this);
            progressDialog.setTitle("Placing Order...");
            progressDialog.show();
            DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();
            databaseReference.child("orders").child(s).setValue(order).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        progressDialog.setTitle("Waiting for Confirmation");
                        final User user=order.getUser();
                        order.setUser(null);
                        user.getOrders().add(order);
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(UserDetails.getUser().getUid());
                        databaseReference.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    progressDialog.dismiss();
                                    orderPlacedLayout.setVisibility(View.VISIBLE);
                                    UserDetails.setAppUser(user);
                                }
                            }
                        });

                    }
                }
            });
        }
    }
    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Payment Unsuccessfull", Toast.LENGTH_SHORT).show();

    }

    public void onKeepPuchaseClick(View view) {
        Intent intent=new Intent(CompleteOrderActivity.this,MainActivity.class);
        startActivity(intent);
    }
}
