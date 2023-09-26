package com.example.markerlessproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.Objects;

public class DetailPanduanActivity extends AppCompatActivity {

    ImageView tahapPanduan, tahapPanduan2, tahapPanduan3, tahapPanduan4, tahapPanduan5;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_panduan);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_pilih_materi);
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        YouTubePlayerView youTubePlayerView = findViewById(R.id.videoPanduan);
        getLifecycle().addObserver(youTubePlayerView);

        tahapPanduan = findViewById(R.id.iv_tahap_panduan);
        Glide.with(this).asGif().load("https://cdn.discordapp.com/attachments/853143311577776135/1135820152512991232/ezgif-2-8b223b9918.gif").into(tahapPanduan);

        tahapPanduan2 = findViewById(R.id.iv_tahap_panduan2);
        Glide.with(this).asGif().load("https://cdn.discordapp.com/attachments/853143311577776135/1135821749477449728/ezgif-4-0336fee038.gif").into(tahapPanduan2);

        tahapPanduan3 = findViewById(R.id.iv_tahap_panduan3);
        Glide.with(this).asGif().load("https://cdn.discordapp.com/attachments/853143311577776135/1136155780752621649/ezgif-4-60f9b46854.gif").into(tahapPanduan3);

        tahapPanduan4 = findViewById(R.id.iv_tahap_panduan4);
        Glide.with(this).asGif().load("https://cdn.discordapp.com/attachments/853143311577776135/1136156294739415050/ezgif-5-4f8091554c.gif").into(tahapPanduan4);

        tahapPanduan5 = findViewById(R.id.iv_tahap_panduan5);
        Glide.with(this).asGif().load("https://cdn.discordapp.com/attachments/853143311577776135/1136156659299913869/ezgif-5-deed250d3d.gif").into(tahapPanduan5);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = "jfHVNWADD8c";
                youTubePlayer.loadVideo(videoId, 0);
            }
        });


    }
}