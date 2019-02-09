package com.agarwal.ashi.kalakaarindia.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agarwal.ashi.kalakaarindia.Model.NotificationModel;
import com.agarwal.ashi.kalakaarindia.R;

import java.util.Collections;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {
    List<NotificationModel> notificationModels;

    public NotificationAdapter(List<NotificationModel> notificationModels) {
        Collections.reverse(notificationModels);
        this.notificationModels=notificationModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notification_item, viewGroup, false);
       MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.notification_title.setText(notificationModels.get(i).getTitle());
        myViewHolder.notification_content.setText(notificationModels.get(i).getContent());
            if(notificationModels.get(i).isSeen())
            {
                myViewHolder.notification_layout.setAlpha(0.5f);
            }
    }

    @Override
    public int getItemCount() {
        return notificationModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout notification_layout;
        TextView notification_title,notification_content;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            notification_layout=itemView.findViewById(R.id.notification_layout);
            notification_title=itemView.findViewById(R.id.notification_title);
            notification_content=itemView.findViewById(R.id.notification_content);
        }
    }
}
