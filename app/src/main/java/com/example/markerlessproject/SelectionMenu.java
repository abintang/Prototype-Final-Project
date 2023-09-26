package com.example.markerlessproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class SelectionMenu extends AppCompatActivity {

    private MaterialCardView cardMerkurius, cardVenus, cardBumi, cardMars, cardJupiter
            , cardSaturnus, cardUranus, cardNeptunus, cardBulan, cardSatelit, kategoriPlanet, kateriBintang, kategoriSatelit, cardMatahari;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_menu);

        cardMerkurius = findViewById(R.id.cardMerkurius);
        cardVenus = findViewById(R.id.cardVenus);
        cardBumi = findViewById(R.id.cardBumi);
        cardMars = findViewById(R.id.cardMars);
        cardJupiter = findViewById(R.id.cardJupiter);
        cardSaturnus = findViewById(R.id.cardSaturn);
        cardUranus = findViewById(R.id.cardUranus);
        cardNeptunus = findViewById(R.id.cardNeptunus);

        cardBulan = findViewById(R.id.cardMoon);
        cardSatelit = findViewById(R.id.cardSatelit);

        cardMatahari = findViewById(R.id.cardSun);

        kategoriPlanet = findViewById(R.id.kategoriPlanet);
        kategoriSatelit = findViewById(R.id.kategoriSatelit);
        kateriBintang = findViewById(R.id.kategoriBintang);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_pilih_3d);
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        kategoriPlanet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisible(cardMerkurius);
                setVisible(cardVenus);
                setVisible(cardBumi);
                setVisible(cardMars);
                setVisible(cardJupiter);
                setVisible(cardSaturnus);
                setVisible(cardUranus);
                setVisible(cardNeptunus);

                setGone(cardMatahari);

                setGone(cardBulan);
                setGone(cardSatelit);

                kategoriPlanet.setCardBackgroundColor(getResources().getColor(R.color.colorButtonCategory));
                kategoriSatelit.setCardBackgroundColor(getResources().getColor(R.color.darkbg2));
                kateriBintang.setCardBackgroundColor(getResources().getColor(R.color.darkbg2));
            }
        });

        kategoriSatelit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGone(cardMerkurius);
                setGone(cardVenus);
                setGone(cardBumi);
                setGone(cardMars);
                setGone(cardJupiter);
                setGone(cardSaturnus);
                setGone(cardUranus);
                setGone(cardNeptunus);

                setGone(cardMatahari);

                setVisible(cardBulan);
                setVisible(cardSatelit);

                kategoriSatelit.setCardBackgroundColor(getResources().getColor(R.color.colorButtonCategory));
                kategoriPlanet.setCardBackgroundColor(getResources().getColor(R.color.darkbg2));
                kateriBintang.setCardBackgroundColor(getResources().getColor(R.color.darkbg2));
            }
        });

        kateriBintang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGone(cardMerkurius);
                setGone(cardVenus);
                setGone(cardBumi);
                setGone(cardMars);
                setGone(cardJupiter);
                setGone(cardSaturnus);
                setGone(cardUranus);
                setGone(cardNeptunus);

                setVisible(cardMatahari);

                setGone(cardBulan);
                setGone(cardSatelit);

                kateriBintang.setCardBackgroundColor(getResources().getColor(R.color.colorButtonCategory));
                kategoriSatelit.setCardBackgroundColor(getResources().getColor(R.color.darkbg2));
                kategoriPlanet.setCardBackgroundColor(getResources().getColor(R.color.darkbg2));
            }
        });

        cardBulan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonObjek3D("Bulan");
            }
        });

        cardSatelit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonObjek3D("Satelit");
            }
        });

        cardMatahari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonObjek3D("Matahari");
            }
        });


        cardMerkurius.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonObjek3D("Merkurius");
            }
        });

        cardVenus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               buttonObjek3D("Venus");
            }
        });

        cardBumi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              buttonObjek3D("Bumi");
            }
        });

        cardMars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonObjek3D("Mars");
            }
        });

        cardJupiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonObjek3D("Jupiter");
            }
        });

        cardSaturnus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonObjek3D("Saturnus");
            }
        });

        cardUranus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonObjek3D("Uranus");
            }
        });

        cardNeptunus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonObjek3D("Neptunus");
            }
        });
    }

    private void buttonObjek3D(String value) {
        Intent intent = new Intent(SelectionMenu.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("id", value);
        startActivity(intent);
    }

    private void setVisible(MaterialCardView cardview) {
        cardview.setVisibility(View.VISIBLE);
    }

    private void setGone(MaterialCardView cardView) {
        cardView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        // Initialize firebase user
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        // Check condition
        Intent intent;
        if (firebaseUser != null) {
            intent = new Intent(SelectionMenu.this, MainMenu.class);
        } else {
            intent = new Intent(SelectionMenu.this, MainMenuNonAkun.class);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}