package com.example.ashi.kalakaarindia.Fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ashi.kalakaarindia.Adapter.CategoryListRecyclerViewAdapter;
import com.example.ashi.kalakaarindia.Adapter.CategoryRecyclerViewAdapter;
import com.example.ashi.kalakaarindia.Model.CategoryPageItemModel;
import com.example.ashi.kalakaarindia.Model.Product;
import com.example.ashi.kalakaarindia.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


public class CategoryFragment extends Fragment implements CategoryListRecyclerViewAdapter.CategoryListRecyclerViewListener {
    RecyclerView categoryRecyclerView;
    private static final String CATEGORY = "category";
    private String category;
    private DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("category_page");
    CategoryFragmentListerner categoryFragmentListerner;
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
    public void onAttach(Context context) {
        super.onAttach(context);
        categoryFragmentListerner=(CategoryFragmentListerner)getActivity();

    }

    List<CategoryPageItemModel> categoryPageItemModels;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_category, container, false);
        categoryRecyclerView=view.findViewById(R.id.recyclerview_category);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        final CategoryListRecyclerViewAdapter.CategoryListRecyclerViewListener categoryListRecyclerViewListener=this;
        final ProgressDialog progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        if(!category.equals("")&&categoryPageItemModels==null)
        {
            databaseReference.child(category).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    progressDialog.dismiss();
                    GenericTypeIndicator<List<CategoryPageItemModel>> indicator = new GenericTypeIndicator<List<CategoryPageItemModel>>() {};
                    categoryPageItemModels=dataSnapshot.getValue(indicator);
                    CategoryRecyclerViewAdapter categoryRecyclerViewAdapter=new CategoryRecyclerViewAdapter((CategoryRecyclerViewAdapter.CategoryRecyclerViewListener) getActivity(),categoryPageItemModels, categoryListRecyclerViewListener);
                    categoryRecyclerView.setAdapter(categoryRecyclerViewAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else if(categoryPageItemModels!=null){
            progressDialog.dismiss();
            CategoryRecyclerViewAdapter categoryRecyclerViewAdapter=new CategoryRecyclerViewAdapter((CategoryRecyclerViewAdapter.CategoryRecyclerViewListener) getActivity(),categoryPageItemModels, categoryListRecyclerViewListener);
            categoryRecyclerView.setAdapter(categoryRecyclerViewAdapter);
        }

        return view;
    }



    @Override
    public void onProductClicked(Product product) {
        categoryFragmentListerner.onProductClicked(product);
    }

    public interface CategoryFragmentListerner{
        void onProductClicked(Product product);
    }
}
