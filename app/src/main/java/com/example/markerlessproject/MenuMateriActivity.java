package com.example.markerlessproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.markerlessproject.adapter.ContentMateriAdapter;
import com.example.markerlessproject.models.ContentMateriModel;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class MenuMateriActivity extends AppCompatActivity {

    MaterialCardView cardBumi, cardLoading;

    String urlKategori, urlMateri;

    NestedScrollView scrollView;

    TextView titleKategori, descKategori, kategori, kategoriListDesc;
    ImageView iconKategori;

    RecyclerView recyclerView;
    ContentMateriAdapter contentMateriAdapter;
    ArrayList<ContentMateriModel> items;

    int idKategori;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_materi);

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


        idKategori = getIntent().getExtras().getInt("id");
        urlKategori = "https://tata-surya.skripsijoss.my.id/public/kategori/detail/" + idKategori;

        cardLoading = findViewById(R.id.loadingCard);
        scrollView = findViewById(R.id.scrollMateri);
        titleKategori = findViewById(R.id.tv_kategori);
        descKategori = findViewById(R.id.tv_desc_kategori);
        iconKategori = findViewById(R.id.illust_kategori);

        kategori = findViewById(R.id.tv_title_materi);
        kategoriListDesc = findViewById(R.id.tv_title_materi_desc);

        MaterialCardView kategoriPlanet = findViewById(R.id.kategoriPlanet);
        MaterialCardView kategoriSatelit = findViewById(R.id.kategoriSatelit);
        MaterialCardView kateriBintang = findViewById(R.id.kategoriBintang);

        recyclerView = findViewById(R.id.recycleViewListMateri);


        if (idKategori == 1) {
            kategoriPlanet.setCardBackgroundColor(getResources().getColor(R.color.colorButtonCategory));
            kategoriSatelit.setCardBackgroundColor(getResources().getColor(R.color.darkbg2));
            kateriBintang.setCardBackgroundColor(getResources().getColor(R.color.darkbg2));
        } else if (idKategori == 2) {
            kateriBintang.setCardBackgroundColor(getResources().getColor(R.color.colorButtonCategory));
            kategoriSatelit.setCardBackgroundColor(getResources().getColor(R.color.darkbg2));
            kategoriPlanet.setCardBackgroundColor(getResources().getColor(R.color.darkbg2));
        } else {
            kategoriSatelit.setCardBackgroundColor(getResources().getColor(R.color.colorButtonCategory));
            kategoriPlanet.setCardBackgroundColor(getResources().getColor(R.color.darkbg2));
            kateriBintang.setCardBackgroundColor(getResources().getColor(R.color.darkbg2));
        }

        kategoriPlanet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonAction(1);
            }
        });

        kategoriSatelit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonAction(3);
            }
        });

        kateriBintang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonAction(2);
            }
        });

        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlKategori,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject object = jsonObject.getJSONObject("data");
                            titleKategori.setText(object.getString("judul"));
                            descKategori.setText(object.getString("deskripsi"));
                            String kategoriSaatIni = object.getString("nama");

                            kategori.setText("Materi - " + kategoriSaatIni);
                            kategoriListDesc.setText("Dibawah ini adalah list materi kategori "+ kategoriSaatIni + ". Klik materi dibawah untuk melihat detail materi !");

                            String iconKategoriText = object.getString("icon");
                            String urlIconKategori = "https://tata-surya.skripsijoss.my.id/public/icon/" + iconKategoriText;
                            Glide.with(MenuMateriActivity.this).load(urlIconKategori).into(iconKategori);

                            getData();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("TEST", "onErrorResponse: " + error.getMessage());
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        requestQueue.add(stringRequest);


    }

    private void buttonAction(int value) {
        Intent intent = new Intent(MenuMateriActivity.this, MenuMateriActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("id", value);
        startActivity(intent);
    }

    private void getData(){

        urlMateri = "https://tata-surya.skripsijoss.my.id/public/Materi/kategori/" + idKategori;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlMateri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            items = new ArrayList<>();
                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length() ; i++) {
                                ContentMateriModel contentMateriModel = new ContentMateriModel();
                                JSONObject object = jsonArray.getJSONObject(i);

                                contentMateriModel.setId_materi(object.getInt("id"));
                                contentMateriModel.setNama_materi(object.getString("nama"));
                                contentMateriModel.setMini_desc_materi(object.getString("mini_deskripsi"));
                                contentMateriModel.setIconMateri(object.getString("icon"));

                                items.add(contentMateriModel);
                                contentMateriAdapter = new ContentMateriAdapter(getApplicationContext(), items);
                                recyclerView.setLayoutManager(new LinearLayoutManager(MenuMateriActivity.this));
                                recyclerView.setAdapter(contentMateriAdapter);

                                cardLoading.setVisibility(View.GONE);
                                scrollView.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        Log.i("TEST", "onErrorResponse: " + error.getMessage());
                    }
                });


        requestQueue.add(stringRequest);

    }

    @Override
    public void onBackPressed() {
        firebaseAuth = FirebaseAuth.getInstance();
        // Initialize firebase user
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        // Check condition
        Intent intent;
        if (firebaseUser != null) {
            intent = new Intent(MenuMateriActivity.this, MainMenu.class);
        } else {
            intent = new Intent(MenuMateriActivity.this, MainMenuNonAkun.class);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}