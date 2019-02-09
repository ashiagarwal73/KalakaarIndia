package com.agarwal.ashi.kalakaarindia.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.agarwal.ashi.kalakaarindia.Adapter.NotificationAdapter;
import com.agarwal.ashi.kalakaarindia.Model.NotificationModel;
import com.agarwal.ashi.kalakaarindia.R;
import com.agarwal.ashi.kalakaarindia.Utility.UserDetails;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {


    public NotificationFragment() {
        // Required empty public constructor
    }
    ImageView emptyNotification;
    RecyclerView notificationRecyclerView;
    List <NotificationModel> notificationModels;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_notification, container, false);
        notificationModels=new ArrayList<>();
        emptyNotification=view.findViewById(R.id.empty_notification);
        notificationRecyclerView=view.findViewById(R.id.notification_recycler_view);
        notificationRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        updateUI();

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.child("Notifications").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        NotificationModel temp = data.getValue(NotificationModel.class);
                        notificationModels.add(temp);
                    }
                    NotificationAdapter notificationAdapter = new NotificationAdapter(notificationModels);
                    notificationRecyclerView.setAdapter(notificationAdapter);
                    updateUI();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        return view;
    }
    void updateUI(){
        if(notificationModels==null||notificationModels.isEmpty())
        {
            emptyNotification.setVisibility(View.VISIBLE);
            notificationRecyclerView.setVisibility(View.GONE);
        }
        else {
            emptyNotification.setVisibility(View.GONE);
            notificationRecyclerView.setVisibility(View.VISIBLE);
        }
    }
}
