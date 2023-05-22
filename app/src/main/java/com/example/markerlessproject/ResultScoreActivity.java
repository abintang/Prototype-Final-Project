package com.example.markerlessproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;

public class ResultScoreActivity extends AppCompatActivity {

    MaterialButton buttonKembali, buttonRanking;
    TextView nama, totalTerjawab, miniDeskripsiPoint, deskripsiPoint, totalPoint;

    ImageView avatar;

    FirebaseAuth firebaseAuth;
    GoogleSignInClient googleSignInClient;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_score);

        nama = findViewById(R.id.tv_nama);
        totalTerjawab = findViewById(R.id.tv_totalBenar);
        miniDeskripsiPoint = findViewById(R.id.tv_mini_desc_total);
        deskripsiPoint = findViewById(R.id.tv_desc_total);
        totalPoint = findViewById(R.id.tv_point);

        buttonKembali = findViewById(R.id.btn_kembali);
        buttonRanking = findViewById(R.id.btn_rank);

        avatar = findViewById(R.id.iv_ava);

        int score = getIntent().getExtras().getInt("score");
        int totalJawabanBenar = getIntent().getExtras().getInt("jawabanBenar");


        totalPoint.setText(Integer.toString(score));

        totalTerjawab.setText(totalJawabanBenar + "/10");

        if (totalJawabanBenar >= 10) {
            miniDeskripsiPoint.setText("Sempurna !");
        } else if (totalJawabanBenar >= 6) {
            miniDeskripsiPoint.setText("Bagus !");
        } else {
            miniDeskripsiPoint.setText("Belajar Lagi Ya !");
        }

        deskripsiPoint.setText("Kamu menjawab "+ totalJawabanBenar + " dari 10 pertanyaan dengan benar");

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("266247651691-j1pa7rv057iqaf1sj7ldisinf94j0hha.apps.googleusercontent.com")
                .requestEmail()
                .build();

        // Initialize sign in client
        googleSignInClient = GoogleSignIn.getClient(ResultScoreActivity.this, googleSignInOptions);
        firebaseAuth = FirebaseAuth.getInstance();

        // Initialize firebase user
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        // Check condition
        if (firebaseUser != null) {
            // When firebase user is not equal to null set image on image view
            Glide.with(ResultScoreActivity.this).load(firebaseUser.getPhotoUrl()).into(avatar);
            nama.setText(firebaseUser.getDisplayName());
        }

        buttonKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ResultScoreActivity.this, MenuGameActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}