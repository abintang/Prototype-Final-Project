package com.example.markerlessproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class MenuGameActivity extends AppCompatActivity {

    TextView namaPlayer;
    CircleImageView fotoPlayer;

    FirebaseAuth firebaseAuth;
    GoogleSignInClient googleSignInClient;

    MaterialButton buttonAR, buttonRanking;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_game);

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

        namaPlayer = findViewById(R.id.tv_nama);
        fotoPlayer = findViewById(R.id.iv_profile_picture);
        buttonRanking = findViewById(R.id.btn_ranking);

        firebaseAuth = FirebaseAuth.getInstance();

        // Initialize firebase user
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        // Check condition
        if (firebaseUser != null) {
            // When firebase user is not equal to null set image on image view
            Glide.with(MenuGameActivity.this).load(firebaseUser.getPhotoUrl()).into(fotoPlayer);
            namaPlayer.setText(firebaseUser.getDisplayName());
        }

        // Initialize sign in client
        googleSignInClient = GoogleSignIn.getClient(MenuGameActivity.this, GoogleSignInOptions.DEFAULT_SIGN_IN);

        buttonAR = findViewById(R.id.btn_mulai);

        buttonAR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuGameActivity.this, GameArActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("no", 0);
                intent.putExtra("score", 0);
                intent.putExtra("number", 0);
                intent.putExtra("jawabanBenar", 0);
                intent.putExtra("detik", 0);
                startActivity(intent);
            }
        });

        buttonRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuGameActivity.this, RankingActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

        });


    }

    @Override
    public void onBackPressed() {
        firebaseAuth = FirebaseAuth.getInstance();
        // Initialize firebase user
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        // Check condition
        Intent intent;
        if (firebaseUser != null) {
            intent = new Intent(MenuGameActivity.this, MainMenu.class);
        } else {
            intent = new Intent(MenuGameActivity.this, MainMenuNonAkun.class);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}