package com.example.ashi.kalakaarindia.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ashi.kalakaarindia.Adapter.CategoryRecyclerViewAdapter;
import com.example.ashi.kalakaarindia.R;


public class CategoryFragment extends Fragment {
    RecyclerView categoryRecyclerView;
    private static final String CATEGORY = "category";
    private String category;

    public CategoryFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = getArguments().getString(CATEGORY);
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_category, container, false);
        categoryRecyclerView=view.findViewById(R.id.recyclerview_category);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        CategoryRecyclerViewAdapter categoryRecyclerViewAdapter=new CategoryRecyclerViewAdapter((CategoryRecyclerViewAdapter.CategoryRecyclerViewListener) getActivity());
        categoryRecyclerView.setAdapter(categoryRecyclerViewAdapter);
        return view;
    }

}
