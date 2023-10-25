package com.example.communityexample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {

    private ArrayList<CommunityListModel> mCommunityItems;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(mCommunityItems.get(position));
    }
    public void setFriendList(ArrayList<CommunityListModel> list){
        this.mCommunityItems = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mCommunityItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView id;
        TextView title;
        TextView regDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.id);
            title = (TextView) itemView.findViewById(R.id.title);
            regDate = (TextView) itemView.findViewById(R.id.regDate);
        }

        void onBind(CommunityListModel item){
            id.setText(String.valueOf(item.getId()));
            title.setText(item.getTitle());
            regDate.setText(item.getRegDate());
        }
    }
}