package com.example.ashi.kalakaarindia.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ashi.kalakaarindia.Fragment.ProductsCategoryFragment;
import com.example.ashi.kalakaarindia.Model.CategoryPageItemModel;
import com.example.ashi.kalakaarindia.Model.Product;
import com.example.ashi.kalakaarindia.R;

import java.util.List;

public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder> {
    private int HEADER_VIEW_TYPE=1;
    private int LIST_VIEW_TYPE=2;
    CategoryRecyclerViewListener categoryRecyclerViewListener;
    List<CategoryPageItemModel> categoryPageItemModels;
    String top_image;
    CategoryListRecyclerViewAdapter.CategoryListRecyclerViewListener categoryListRecyclerViewListener;

    public CategoryRecyclerViewAdapter(CategoryRecyclerViewListener categoryRecyclerViewListener, List<CategoryPageItemModel> categoryPageItemModels, CategoryListRecyclerViewAdapter.CategoryListRecyclerViewListener categoryListRecyclerViewListener) {
        this.categoryRecyclerViewListener=categoryRecyclerViewListener;
        this.categoryPageItemModels=categoryPageItemModels;
        top_image=categoryPageItemModels.get(0).getTop_image();
        this.categoryListRecyclerViewListener= categoryListRecyclerViewListener;
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder{
        TextView see_all_button,subcategory_name;
        ImageView top_poster;
        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            top_poster=itemView.findViewById(R.id.top_poster);
            see_all_button=itemView.findViewById(R.id.see_all_button);
            subcategory_name=itemView.findViewById(R.id.subcategory_name);
        }
    }
    class ListViewHolder extends RecyclerView.ViewHolder{
        RecyclerView recyclerView;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView=itemView.findViewById(R.id.category_list_recyclervview);
            recyclerView.setLayoutManager(new GridLayoutManager(itemView.getContext(),3));

        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
       if(viewType==HEADER_VIEW_TYPE)
       {
           View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_header_item, viewGroup, false);
           HeaderViewHolder headerViewHolder=new HeaderViewHolder(view);
           return headerViewHolder;
       }
       else {
           View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_list_item, viewGroup, false);
           ListViewHolder listViewHolder=new ListViewHolder(view);
           return listViewHolder;
       }
    }

    @Override
    public void onBindViewHolder(@NonNull  RecyclerView.ViewHolder viewHolder,final int i) {
        if(viewHolder.getItemViewType()==HEADER_VIEW_TYPE)
        {
            HeaderViewHolder headerViewHolder=(HeaderViewHolder)viewHolder;
            if(i==0)
            {
                headerViewHolder.top_poster.setVisibility(View.VISIBLE);
                Glide.with(headerViewHolder.top_poster.getContext()).load(top_image).into(headerViewHolder.top_poster);
            }
            else {
                headerViewHolder.top_poster.setVisibility(View.GONE);

            }
            headerViewHolder.subcategory_name.setText(categoryPageItemModels.get(viewHolder.getAdapterPosition()).getName());
            headerViewHolder.see_all_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    categoryRecyclerViewListener.onSeeAllClicked(categoryPageItemModels.get(i+1).getProducts(),top_image);
                }
            });
        }
        else {
            ListViewHolder listViewHolder=(ListViewHolder)viewHolder;
            CategoryListRecyclerViewAdapter categoryListRecyclerViewAdapter =new CategoryListRecyclerViewAdapter(categoryPageItemModels.get(viewHolder.getAdapterPosition()).getTrending_product(),categoryListRecyclerViewListener);
            listViewHolder.recyclerView.setAdapter(categoryListRecyclerViewAdapter);
        }
    }

    @Override
    public int getItemCount() {
        return categoryPageItemModels.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(categoryPageItemModels.get(position).getType().equals("category_header"))
        {
            return HEADER_VIEW_TYPE;
        }
        else {
            return LIST_VIEW_TYPE;
        }
    }

    public interface CategoryRecyclerViewListener{
        void onSeeAllClicked(List<Product> products,String image);
        public void onProductClicked(Product product);
    }
}
