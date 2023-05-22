package com.example.markerlessproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.codesgood.views.JustifiedTextView;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailMateri extends AppCompatActivity {

    FloatingActionButton backButton, homeButton;

    TabLayout tabLayout;

    JustifiedTextView deskripsi, miniDeskripsi, fakta1, fakta2, fakta3;

    CircleImageView fotoMateri;

    TextView materi, titleDeskripsi, titleJarak, jarak, titlePeriode, periode, titlediameter, diameter, titlemassa, massa, titleFakta;

    View divider, gapView, gapView2;

    MaterialCardView cardFakta, cardLoading;

    NestedScrollView scrollView;

    ImageView infoPeriode, infoDiameter, infoMassa;

    int idMateri;
    String urlMateri;
    ConstraintLayout detailMateri;

    String idKategori;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_materi);

        idMateri = getIntent().getExtras().getInt("id");
        urlMateri = "https://tata-surya.skripsijoss.my.id/public/Materi/detail/" + idMateri;


        backButton = findViewById(R.id.backButton);
        homeButton = findViewById(R.id.homeButton);

        tabLayout = findViewById(R.id.tabLayout);

        // Justified
        deskripsi = findViewById(R.id.tv_desc);
        miniDeskripsi = findViewById(R.id.tv_mini_desc);

        // TextView
        materi = findViewById(R.id.tv_title_materi);
        titleDeskripsi = findViewById(R.id.desc);
        titleJarak = findViewById(R.id.title_jarak);
        jarak = findViewById(R.id.tv_jarak);
        titlePeriode = findViewById(R.id.title_periode);
        periode = findViewById(R.id.tv_periode);
        titlediameter = findViewById(R.id.title_diameter);
        diameter = findViewById(R.id.tv_diameter);
        titlemassa = findViewById(R.id.title_massa);
        massa = findViewById(R.id.tv_massa);

        // View
        divider = findViewById(R.id.divider);
        gapView = findViewById(R.id.gapView);
        gapView2 = findViewById(R.id.viewGap2);

        // Imageview
        infoDiameter = findViewById(R.id.iv_diameter);
        infoMassa = findViewById(R.id.iv_massa);
        infoPeriode = findViewById(R.id.iv_periode);

        titleFakta = findViewById(R.id.tv_title_fakta);
        cardFakta = findViewById(R.id.cardFakta);

        cardLoading = findViewById(R.id.loadingCard);
        scrollView = findViewById(R.id.scrollView);
        detailMateri = findViewById(R.id.layoutDetailMateri);

        fotoMateri = findViewById(R.id.iv_materi_image);
        fakta1 = findViewById(R.id.tv_fact1);
        fakta2 = findViewById(R.id.tv_fact2);
        fakta3 = findViewById(R.id.tv_fact3);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlMateri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject object = jsonObject.getJSONObject("data");
                            materi.setText(object.getString("nama"));
                            miniDeskripsi.setText(object.getString("mini_deskripsi"));
                            String dataDeskripsi = object.getString("deskripsi");
                            dataDeskripsi = dataDeskripsi.replace("\\n", System.getProperty("line.separator"));
                            deskripsi.setText(dataDeskripsi);
                            jarak.setText(object.getString("jarak_matahari"));
                            periode.setText(object.getString("periode_orbit"));
                            massa.setText(object.getString("massa"));
                            diameter.setText(object.getString("diameter"));

                            titleFakta.setText("Fakta Lainnya Terkait " + object.getString("nama"));
                            fakta1.setText(object.getString("fakta_1"));
                            fakta2.setText(object.getString("fakta_2"));
                            fakta3.setText(object.getString("fakta_3"));

                            idKategori = object.getString("id_kategori");

                            if(idKategori.equals("3")) {
                                titleJarak.setText("Jarak dari Bumi");
                                createSnackbar(infoPeriode, "Periode Orbit adalah waktu (waktu bumi) yang dibutuhkan satelit untuk mengelilingi objek yang mereka orbit");
                            } else {
                                createSnackbar(infoPeriode, "Periode Orbit adalah waktu (waktu bumi) yang dibutuhkan planet untuk mengelilingi matahari");

                            }

                            String namaImage = object.getString("gambar");
                            String urlFotoMateri = "https://tata-surya.skripsijoss.my.id/public/imageMateri/" + namaImage;


                            Glide.with(DetailMateri.this).load(urlFotoMateri).into(fotoMateri);


                            cardLoading.setVisibility(View.GONE);
                            scrollView.setVisibility(View.VISIBLE);
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


        // snackbar
        createSnackbar(infoDiameter, "Dalam hal astronomi, diameter dapat digunakan untuk mengukur ukuran suatu objek tata surya");
        createSnackbar(infoMassa, "Dalam astronomi, massa ditulis dalam notasi ilmiah untuk menyatakan bilangan yang besar");

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        setVisibilityJustified(deskripsi, false);
                        setVisibilityJustified(miniDeskripsi, false);

                        setVisibilityText(titlemassa, false);
                        setVisibilityText(titlediameter, false);
                        setVisibilityText(titleDeskripsi, false);
                        setVisibilityText(titleJarak, false);
                        setVisibilityText(titlePeriode, false);
                        setVisibilityText(materi, false);
                        setVisibilityText(jarak, false);
                        setVisibilityText(periode, false);
                        setVisibilityText(diameter, false);
                        setVisibilityText(massa, false);

                        setVisibilityImage(infoDiameter, false);
                        setVisibilityImage(infoMassa, false);
                        setVisibilityImage(infoPeriode, false);

                        setVisibilityView(divider, false);
                        setVisibilityView(gapView, false);

                        setVisibilityView(gapView2, true);
                        setVisibilityText(titleFakta, true);
                        setVisibilityCard(cardFakta, true);
                        break;
                    case 1:
                        setVisibilityJustified(deskripsi, true);
                        setVisibilityJustified(miniDeskripsi, true);

                        setVisibilityText(titlemassa, true);
                        setVisibilityText(titlediameter, true);
                        setVisibilityText(titleDeskripsi, true);
                        setVisibilityText(titleJarak, true);
                        setVisibilityText(titlePeriode, true);
                        setVisibilityText(materi, true);
                        setVisibilityText(jarak, true);
                        setVisibilityText(periode, true);
                        setVisibilityText(diameter, true);
                        setVisibilityText(massa, true);

                        setVisibilityImage(infoDiameter, true);
                        setVisibilityImage(infoMassa, true);
                        setVisibilityImage(infoPeriode, true);

                        setVisibilityView(divider, true);
                        setVisibilityView(gapView, true);

                        setVisibilityView(gapView2, false);
                        setVisibilityText(titleFakta, false);
                        setVisibilityCard(cardFakta, false);

                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailMateri.this, MainMenu.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    private void setVisibilityJustified(JustifiedTextView textView, Boolean isGone) {
        if (isGone) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.VISIBLE);
        }
    }

    private void setVisibilityText(TextView textView, Boolean isGone) {
        if (isGone) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.VISIBLE);
        }
    }

    private void setVisibilityView(View view, Boolean isGone) {
        if (isGone) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }

    private void setVisibilityCard(MaterialCardView view, Boolean isGone) {
        if (isGone) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }

    private void setVisibilityImage(ImageView view, Boolean isGone) {
        if (isGone) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }

    private void createSnackbar(ImageView imageView, String text) {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar = Snackbar.make(detailMateri, text, Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });

    }
}