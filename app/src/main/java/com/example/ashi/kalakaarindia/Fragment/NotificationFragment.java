package com.example.ashi.kalakaarindia.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ashi.kalakaarindia.Adapter.NotificationAdapter;
import com.example.ashi.kalakaarindia.R;
import com.example.ashi.kalakaarindia.Utility.UserDetails;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {


    public NotificationFragment() {
        // Required empty public constructor
    }
    ImageView emptyNotification;
    RecyclerView notificationRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_notification, container, false);
        emptyNotification=view.findViewById(R.id.empty_notification);
        emptyNotification.setVisibility(View.GONE);
        notificationRecyclerView=view.findViewById(R.id.notification_recycler_view);
        notificationRecyclerView.setVisibility(View.VISIBLE);
        notificationRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        NotificationAdapter notificationAdapter=new NotificationAdapter();
        notificationRecyclerView.setAdapter(notificationAdapter);
        return view;
    }

}
