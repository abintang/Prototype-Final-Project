package com.example.markerlessproject.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.markerlessproject.DetailMateri;
import com.example.markerlessproject.MenuMateriActivity;
import com.example.markerlessproject.R;
import com.example.markerlessproject.models.ContentRankingModel;

import java.util.ArrayList;
import java.util.List;

public class ContentRankingAdapter extends RecyclerView.Adapter<ContentRankingAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<ContentRankingModel> data;


    public ContentRankingAdapter(Context context, ArrayList<ContentRankingModel> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_list_ranking,viewGroup,false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, @SuppressLint("RecyclerView") int i) {
        // bind the textview with data received
        int j = i+1;
        viewHolder.number.setText(Integer.toString(j));
        viewHolder.namaUser.setText(data.get(i).getNamaUser());
        viewHolder.dateRanking.setText("Durasi Penyelesaian: " + data.get(i).getDateRank() + " Detik");
        viewHolder.point.setText(data.get(i).getPoint() + " Points");
        String urlIconKategori = data.get(i).getPhoto();
        Glide.with(context).load(urlIconKategori).into(viewHolder.photo);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView namaUser, dateRanking, point, number;
        ImageView photo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // variable yang udah di declare sesuai dengan id nya masing-masing
            namaUser = itemView.findViewById(R.id.tv_user);
            dateRanking = itemView.findViewById(R.id.tv_date);
            point = itemView.findViewById(R.id.tv_user_point);
            photo = itemView.findViewById(R.id.iv_ava);
            number = itemView.findViewById(R.id.tv_number);
        }
    }

}
