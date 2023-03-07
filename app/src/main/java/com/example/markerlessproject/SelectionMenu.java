package com.example.markerlessproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.card.MaterialCardView;

import java.util.Objects;

public class SelectionMenu extends AppCompatActivity {

    private MaterialCardView cardMerkurius, cardVenus, cardBumi, cardMars, cardJupiter
            , cardSaturnus, cardUranus, cardNeptunus;

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

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_pilih_3d);
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);




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

}