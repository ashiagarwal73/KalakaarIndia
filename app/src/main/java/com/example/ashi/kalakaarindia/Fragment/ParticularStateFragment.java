package com.example.ashi.kalakaarindia.Fragment;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ashi.kalakaarindia.Adapter.CustomSpinner;
import com.example.ashi.kalakaarindia.Adapter.GridRecyclerViewAdapter;
import com.example.ashi.kalakaarindia.Adapter.TopRecyclerViewAdapter;
import com.example.ashi.kalakaarindia.Model.State;
import com.example.ashi.kalakaarindia.Model.StatePageModel;
import com.example.ashi.kalakaarindia.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class ParticularStateFragment extends Fragment {
    DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("state_page");
    RecyclerView grid_recyclerview;
    //String states[]={"No Filter","Uttarakhand","Uttar Pradesh","Karnataka","Madya Pradesh"};
    CardView clothes,food,merchandise,paintings;
    CustomSpinner spinner;
    ImageView top_poster,bottom_poster;
    TextView hello_msg;
    private int position;
    ArrayList<String> states;
    public ParticularStateFragment() {
        // Required empty public constructor
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null)
        {
            position=getArguments().getInt("position");
            states=getArguments().getStringArrayList("states");

            states.remove(0);
            states.add(0,"No Filter");

        }
    }
    State state;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_particular_state, container, false);
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(Objects.requireNonNull(getContext()),R.layout.spinner_item,states);

        initialiseViews(view);
        grid_recyclerview.setLayoutManager(new GridLayoutManager(getContext(), 2));
        grid_recyclerview.setNestedScrollingEnabled(false);

        spinner.setPopupBackgroundDrawable(getResources().getDrawable(R.drawable.spinner_item_background));
        spinner.setAdapter(arrayAdapter);
        spinner.setDropDownVerticalOffset(100);
        spinner.setSelection(position);
        spinner.setSpinnerEventsListener(new CustomSpinner.OnSpinnerEventsListener() {
            @Override
            public void onSpinnerOpened(Spinner spin) {
                spinner.setBackground(getResources().getDrawable(R.drawable.spinner_design_open));
            }

            @Override
            public void onSpinnerClosed(Spinner spin) {

                spinner.setBackground(getResources().getDrawable(R.drawable.spinner_design));
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              if(position==0)
                getActivity().getSupportFragmentManager().popBackStackImmediate();
              else {
                  databaseReference.addValueEventListener(new ValueEventListener() {
                      @Override
                      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                          state=dataSnapshot.child(spinner.getSelectedItem().toString()).getValue(State.class);
                          if(state!=null){
                          Glide.with(getContext()).load(state.getTop_poster()).into(top_poster);
                          Glide.with(getContext()).load(state.getBottom_poster()).into(bottom_poster);
                          hello_msg.setText(state.getHello_msg());
                              GridRecyclerViewAdapter gridRecyclerViewAdapter=new GridRecyclerViewAdapter((GridRecyclerViewAdapter.Listener) getActivity(),state.getCategories());
                              grid_recyclerview.setAdapter(gridRecyclerViewAdapter);
                          }
                      }

                      @Override
                      public void onCancelled(@NonNull DatabaseError databaseError) {

                      }
                  });

              }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });









        return view;
    }

    private void initialiseViews(View view) {
        spinner=view.findViewById(R.id.spinner);
        top_poster=view.findViewById(R.id.top_poster);
        bottom_poster=view.findViewById(R.id.bottom_poster);
        hello_msg=view.findViewById(R.id.hello_msg);
        grid_recyclerview=view.findViewById(R.id.grid_recyclerview);
    }


}
