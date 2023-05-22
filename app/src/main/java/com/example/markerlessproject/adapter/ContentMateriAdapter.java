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
import com.example.markerlessproject.models.ContentMateriModel;

import java.util.ArrayList;
import java.util.List;

public class ContentMateriAdapter extends RecyclerView.Adapter<ContentMateriAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<ContentMateriModel> data;

    /* adapter ini adalah adapter recyclerview yang ada di page list inovasi
    , bisa diubah/diganti sesuai dengan adapter yang di mau. lalu untuk id
    masing-masing isi/content nya itu ada dibawah (ViewHolder) */

    public ContentMateriAdapter(Context context, ArrayList<ContentMateriModel> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_list_materi,viewGroup,false);
        return new ContentMateriAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, @SuppressLint("RecyclerView") int i) {
        // bind the textview with data received
        viewHolder.namaMateri.setText(data.get(i).getNama_materi());
        viewHolder.miniDesc.setText(data.get(i).getMini_desc_materi());
        String iconMateri = data.get(i).getIconMateri();
        String urlIconKategori = "https://tata-surya.skripsijoss.my.id/public/icon/" + iconMateri;
        Glide.with(context).load(urlIconKategori).into(viewHolder.iconMateri);


        viewHolder.materi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailMateri.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("id", data.get(i).getId_materi());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView namaMateri, miniDesc;
        ImageView iconMateri;
        CardView materi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // variable yang udah di declare sesuai dengan id nya masing-masing
            namaMateri = itemView.findViewById(R.id.rv_title_materi);
            miniDesc = itemView.findViewById(R.id.rv_materi_desc_singkat);
            iconMateri = itemView.findViewById(R.id.rv_icon_materi);
            materi = itemView.findViewById(R.id.cardMateri);

        }
    }

}
