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
                buttonAction("Bulan");
            }
        });

        cardSatelit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonAction("Satelit");
            }
        });

        cardMatahari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonAction("Matahari");
            }
        });


        cardMerkurius.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonAction("Merkurius");
            }
        });

        cardVenus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               buttonAction("Venus");
            }
        });

        cardBumi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              buttonAction("Bumi");
            }
        });

        cardMars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonAction("Mars");
            }
        });

        cardJupiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonAction("Jupiter");
            }
        });

        cardSaturnus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonAction("Saturnus");
            }
        });

        cardUranus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonAction("Uranus");
            }
        });

        cardNeptunus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonAction("Neptunus");
            }
        });
    }

    private void buttonAction(String value) {
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
        Intent intent = new Intent(SelectionMenu.this, MainMenu.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}