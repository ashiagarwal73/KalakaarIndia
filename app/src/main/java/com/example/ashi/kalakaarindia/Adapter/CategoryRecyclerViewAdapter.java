package com.example.ashi.kalakaarindia.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ashi.kalakaarindia.Fragment.ProductsCategoryFragment;
import com.example.ashi.kalakaarindia.R;

public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder>{
    private int HEADER_VIEW_TYPE=1;
    private int LIST_VIEW_TYPE=2;
    CategoryRecyclerViewListener categoryRecyclerViewListener;
    public CategoryRecyclerViewAdapter(CategoryRecyclerViewListener categoryRecyclerViewListener){
        this.categoryRecyclerViewListener=categoryRecyclerViewListener;
    }
    class HeaderViewHolder extends RecyclerView.ViewHolder{
        TextView see_all_button;
        ImageView top_poster;
        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            top_poster=itemView.findViewById(R.id.top_poster);
            see_all_button=itemView.findViewById(R.id.see_all_button);
        }
    }
    class ListViewHolder extends RecyclerView.ViewHolder{
        RecyclerView recyclerView;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView=itemView.findViewById(R.id.category_list_recyclervview);
            recyclerView.setLayoutManager(new GridLayoutManager(itemView.getContext(),3));
            CategoryListRecyclerViewAdapter categoryListRecyclerViewAdapter =new CategoryListRecyclerViewAdapter();
            recyclerView.setAdapter(categoryListRecyclerViewAdapter);
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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if(viewHolder.getItemViewType()==HEADER_VIEW_TYPE)
        {
            HeaderViewHolder headerViewHolder=(HeaderViewHolder)viewHolder;
            if(i==0)
            {
                headerViewHolder.top_poster.setVisibility(View.VISIBLE);
            }
            else {
                headerViewHolder.top_poster.setVisibility(View.GONE);

            }
            headerViewHolder.see_all_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    categoryRecyclerViewListener.onSeeAllClicked("","");
                }
            });
        }
        else {

        }
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0||position==2||position==4)
        {
            return HEADER_VIEW_TYPE;
        }
        else {
            return LIST_VIEW_TYPE;
        }
    }

    public interface CategoryRecyclerViewListener{
        void onSeeAllClicked(String category,String subCategory);
    }
}
