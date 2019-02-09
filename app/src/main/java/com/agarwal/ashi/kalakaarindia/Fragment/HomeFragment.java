package com.agarwal.ashi.kalakaarindia.Fragment;


import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.agarwal.ashi.kalakaarindia.Adapter.CustomSpinner;
import com.agarwal.ashi.kalakaarindia.Adapter.BottomRecyclerViewAdapter;
import com.agarwal.ashi.kalakaarindia.Adapter.TopRecyclerViewAdapter;
import com.agarwal.ashi.kalakaarindia.Model.HomePageModel;
import com.agarwal.ashi.kalakaarindia.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Objects;


public class HomeFragment extends Fragment {

    RecyclerView recyclerViewTop,recyclerViewBottom;
   // String states[]={"Filter by States","Uttarakhand","Uttar Pradesh","Karnataka","Madya Pradesh"};
    CustomSpinner spinner;
    private DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("home_page");
    private HomePageModel homePageModel;
    ArrayList<String> states;
    private ImageView top_poster,bottom_poster;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_home, container, false);
        initialiseViews(view);
        final ProgressDialog progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        recyclerViewTop.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        recyclerViewBottom.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        spinner.setPopupBackgroundDrawable(getResources().getDrawable(R.drawable.spinner_item_background));

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
        spinner.setDropDownVerticalOffset(100);
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(spinner);

            // Set popupWindow height to 500px
            popupWindow.setHeight(800);
        }
        catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0)
                {
                    ParticularStateFragment particularStateFragment=new ParticularStateFragment();
                    Bundle bundle=new Bundle();
                    bundle.putInt("position",position);
                    bundle.putStringArrayList("states",states);
                    particularStateFragment.setArguments(bundle);
                    Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.fragment,particularStateFragment).addToBackStack(null).commit();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if(homePageModel==null) {
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    homePageModel = dataSnapshot.getValue(HomePageModel.class);
                    Glide.with(getContext()).load(homePageModel.getTop_poster()).into(top_poster);
                    Glide.with(getContext()).load(homePageModel.getBottom_poster()).into(bottom_poster);
                    states = new ArrayList<String>(homePageModel.getStates());
                    states.add(0, "Filter by States");
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), R.layout.spinner_item, states);
                    spinner.setAdapter(arrayAdapter);
                    TopRecyclerViewAdapter topRecyclerViewAdapter = new TopRecyclerViewAdapter((TopRecyclerViewAdapter.Listener) getActivity(), homePageModel.getCategories());
                    recyclerViewTop.setAdapter(topRecyclerViewAdapter);
                    BottomRecyclerViewAdapter bottomRecyclerViewAdapter = new BottomRecyclerViewAdapter(homePageModel.getTrending_products(), (BottomRecyclerViewAdapter.BottomRecyclerViewListener) getActivity());
                    recyclerViewBottom.setAdapter(bottomRecyclerViewAdapter);
                    progressDialog.dismiss();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    System.out.print(databaseError);
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "No Internet !", Toast.LENGTH_LONG).show();

                }
            });
        }
        else {
            Glide.with(getContext()).load(homePageModel.getTop_poster()).into(top_poster);
            Glide.with(getContext()).load(homePageModel.getBottom_poster()).into(bottom_poster);
            states = new ArrayList<String>(homePageModel.getStates());
            states.add(0, "Filter by States");
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), R.layout.spinner_item, states);
            spinner.setAdapter(arrayAdapter);
            TopRecyclerViewAdapter topRecyclerViewAdapter = new TopRecyclerViewAdapter((TopRecyclerViewAdapter.Listener) getActivity(), homePageModel.getCategories());
            recyclerViewTop.setAdapter(topRecyclerViewAdapter);
            BottomRecyclerViewAdapter bottomRecyclerViewAdapter = new BottomRecyclerViewAdapter(homePageModel.getTrending_products(), (BottomRecyclerViewAdapter.BottomRecyclerViewListener) getActivity());
            recyclerViewBottom.setAdapter(bottomRecyclerViewAdapter);
            progressDialog.dismiss();
        }
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        spinner.setSelection(0,false);
    }

   void initialiseViews(View view){
       recyclerViewTop =view.findViewById(R.id.recyclerview);
       spinner=view.findViewById(R.id.spinner);
       recyclerViewBottom=view.findViewById(R.id.recyclerview_bottom);
       top_poster=view.findViewById(R.id.top_poster);
       bottom_poster=view.findViewById(R.id.bottom_poster);

    }
}
