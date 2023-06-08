package com.example.cookrecipe.code;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookrecipe.R;

import java.util.ArrayList;

public abstract class AramAdapter extends RecyclerView.Adapter<AramAdapter.CustomViewHolder> {

    private ArrayList<AramDTO> arrayList;
    private Context context;


    public AramAdapter(ArrayList<AramDTO> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.aram_list, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

//    @Override
//    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
//        Glide.with(holder.itemView)
//                .load(arrayList.get(position).getProfile())
//                .into(holder.iv_profile);
//        holder.tv_id.setText(arrayList.get(position).getId());
//        holder.tv_pw.setText(String.valueOf(arrayList.get(position).getPw()));
//        holder.tv_userName.setText(arrayList.get(position).getUserName());
//    }

    @Override
    public int getItemCount() {
        // 삼항 연산자
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_profile;
        TextView tv_id;
        TextView tv_pw;
        TextView tv_userName;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_profile = itemView.findViewById(R.id.iv_img);
            this.tv_id = itemView.findViewById(R.id.tv_title1);
            this.tv_pw = itemView.findViewById(R.id.tv_title2);
            this.tv_userName = itemView.findViewById(R.id.tv_title3);
        }
    }
}
