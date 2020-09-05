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

public class LearningRecyclerViewAdapter extends RecyclerView.Adapter<LearningRecyclerViewAdapter.viewHolder> {
    Context mContext;
    List<LearningModel> mData;

    public LearningRecyclerViewAdapter(Context mContext, List<LearningModel> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(mContext).inflate(R.layout.item_learning,parent,false);
              viewHolder hold = new viewHolder(view);
                      return hold;
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

            name= (TextView)itemView.findViewById(R.id.name_learning);
            info= (TextView)itemView.findViewById(R.id.info_learning);
            img= (ImageView)itemView.findViewById(R.id.learning_img);
        }
    }
}
