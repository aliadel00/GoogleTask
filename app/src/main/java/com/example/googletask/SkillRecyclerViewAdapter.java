package com.example.googletask;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class SkillRecyclerViewAdapter extends RecyclerView.Adapter<SkillRecyclerViewAdapter.viewHolder> {
    Context mContext;
    List<SkillModel> mData;

    public SkillRecyclerViewAdapter(Context mContext, List<SkillModel> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(mContext).inflate(R.layout.item_skill,parent,false);
              viewHolder holder = new viewHolder(view);
                      return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
    holder.name.setText(mData.get(position).getName());
    holder.info.setText(mData.get(position).getInfo());
    Picasso.get().load(mData.get(position).getBadgeUrl()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private TextView info;
        private ImageView img;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            name= (TextView)itemView.findViewById(R.id.name_skill);
            info= (TextView)itemView.findViewById(R.id.info_skill);
            img= (ImageView)itemView.findViewById(R.id.skill_img);
        }
    }
}
