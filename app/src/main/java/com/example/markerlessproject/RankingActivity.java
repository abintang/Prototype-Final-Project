package com.example.markerlessproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
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
import com.example.markerlessproject.adapter.ContentRankingAdapter;
import com.example.markerlessproject.models.ContentMateriModel;
import com.example.markerlessproject.models.ContentRankingModel;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class RankingActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ContentRankingAdapter contentRankingAdapter;
    ArrayList<ContentRankingModel> items;

    TextView namaRank1, pointRank1, namaRank2, pointRank2, namaRank3, pointRank3;

    CircleImageView fotoRank1, fotoRank2, fotoRank3;

    NestedScrollView scrollView;
    MaterialCardView loadingCard;

    String token;


    TabLayout tabLayout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_pilih_materi);
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        tabLayout = findViewById(R.id.tabLayout);

        namaRank1 = findViewById(R.id.tv_firstRankName);
        namaRank2 = findViewById(R.id.tv_secondRankName);
        namaRank3 = findViewById(R.id.tv_thirdRankName);
        pointRank1 = findViewById(R.id.tv_firstRankPoint);
        pointRank2 = findViewById(R.id.tv_secondRankPoint);
        pointRank3 = findViewById(R.id.tv_thirdRankPoint);

        fotoRank1 = findViewById(R.id.ava_first);
        fotoRank2 = findViewById(R.id.ava_second);
        fotoRank3 = findViewById(R.id.ava_third);

        scrollView = findViewById(R.id.scrollRanking);
        loadingCard = findViewById(R.id.loadingCard);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        recyclerView = findViewById(R.id.recycleViewListRanking);

        getData("Semua");

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        scrollView.setVisibility(View.INVISIBLE);
                        items.clear();
                        contentRankingAdapter.notifyDataSetChanged();
                        getData("Semua");
                        loadingCard.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        scrollView.setVisibility(View.INVISIBLE);
                        items.clear();
                        contentRankingAdapter.notifyDataSetChanged();
                        getData("Bulan");
                        loadingCard.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        scrollView.setVisibility(View.INVISIBLE);
                        items.clear();
                        contentRankingAdapter.notifyDataSetChanged();
                        getData("Tahun");
                        loadingCard.setVisibility(View.VISIBLE);
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



    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(RankingActivity.this, MenuGameActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void getData(String filter) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        assert firebaseUser != null;

        String url = "https://tata-surya.skripsijoss.my.id/public/user/detail/" + firebaseUser.getUid();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject object = jsonObject.getJSONObject("data");
                            token = object.getString("api_token");

                            if (filter.equalsIgnoreCase("Semua")) {
                                getDataSemua(token);
                            } else if (filter.equalsIgnoreCase("Bulan")) {
                                getDataBulan(token);
                            } else if (filter.equalsIgnoreCase("Tahun")) {
                                getDataTahun(token);
                            }

                            loadingCard.setVisibility(View.GONE);
                            scrollView.setVisibility(View.VISIBLE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("TEST2", "onErrorResponse: " + error.getMessage());
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        requestQueue.add(stringRequest);
    }


    private void getDataSemua(String token){

        String urlApi = "https://tata-surya.skripsijoss.my.id/public/SoalRank";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlApi,
                new Response.Listener<String>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            items = new ArrayList<>();
                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length() ; i++) {
                                ContentRankingModel contentRankingModel = new ContentRankingModel();
                                JSONObject object = jsonArray.getJSONObject(i);
                                JSONArray jsonArray1 = object.getJSONArray("user");
                                JSONObject objectUser = jsonArray1.getJSONObject(0);

                                contentRankingModel.setNamaUser(objectUser.getString("nama_lengkap"));
                                contentRankingModel.setDateRank(object.getString("durasi"));
                                contentRankingModel.setPhoto(objectUser.getString("photo"));
                                contentRankingModel.setPoint(object.getString("total_point"));


                                if (jsonArray.length() == 1) {
                                    JSONObject object1 = jsonArray.getJSONObject(0);
                                    JSONArray jsonArrayRank1 = object1.getJSONArray("user");
                                    JSONObject objectUser1 = jsonArrayRank1.getJSONObject(0);

                                    namaRank1.setText(objectUser1.getString("nama_lengkap"));
                                    pointRank1.setText(object1.getString("total_point") + " Points");
                                    String urlPhotoRank1 = objectUser1.getString("photo");
                                    Glide.with(RankingActivity.this).load(urlPhotoRank1).into(fotoRank1);

                                } else if (jsonArray.length() == 2) {
                                    JSONObject object1 = jsonArray.getJSONObject(0);
                                    JSONArray jsonArrayRank1 = object1.getJSONArray("user");
                                    JSONObject objectUser1 = jsonArrayRank1.getJSONObject(0);

                                    namaRank1.setText(objectUser1.getString("nama_lengkap"));
                                    pointRank1.setText(object1.getString("total_point") + " Points");
                                    String urlPhotoRank1 = objectUser1.getString("photo");
                                    Glide.with(RankingActivity.this).load(urlPhotoRank1).into(fotoRank1);

                                    JSONObject object2 = jsonArray.getJSONObject(1);
                                    JSONArray jsonArrayRank2 = object2.getJSONArray("user");
                                    JSONObject objectUser2 = jsonArrayRank2.getJSONObject(0);

                                    namaRank2.setText(objectUser2.getString("nama_lengkap"));
                                    pointRank2.setText(object2.getString("total_point") + " Points");
                                    String urlPhotoRank2 = objectUser2.getString("photo");
                                    Glide.with(RankingActivity.this).load(urlPhotoRank2).into(fotoRank2);
                                } else {
                                    JSONObject object1 = jsonArray.getJSONObject(0);
                                    JSONArray jsonArrayRank1 = object1.getJSONArray("user");
                                    JSONObject objectUser1 = jsonArrayRank1.getJSONObject(0);

                                    namaRank1.setText(objectUser1.getString("nama_lengkap"));
                                    pointRank1.setText(object1.getString("total_point") + " Points");
                                    String urlPhotoRank1 = objectUser1.getString("photo");
                                    Glide.with(RankingActivity.this).load(urlPhotoRank1).into(fotoRank1);

                                    JSONObject object2 = jsonArray.getJSONObject(1);
                                    JSONArray jsonArrayRank2 = object2.getJSONArray("user");
                                    JSONObject objectUser2 = jsonArrayRank2.getJSONObject(0);

                                    namaRank2.setText(objectUser2.getString("nama_lengkap"));
                                    pointRank2.setText(object2.getString("total_point") + " Points");
                                    String urlPhotoRank2 = objectUser2.getString("photo");
                                    Glide.with(RankingActivity.this).load(urlPhotoRank2).into(fotoRank2);

                                    JSONObject object3 =  jsonArray.getJSONObject(2);
                                    JSONArray jsonArrayRank3 = object3.getJSONArray("user");
                                    JSONObject objectUser3 = jsonArrayRank3.getJSONObject(0);

                                    namaRank3.setText(objectUser3.getString("nama_lengkap"));
                                    pointRank3.setText(object3.getString("total_point") + " Points");
                                    String urlPhotoRank3 = objectUser3.getString("photo");
                                    Glide.with(RankingActivity.this).load(urlPhotoRank3).into(fotoRank3);

                                }

                                items.add(contentRankingModel);
                                contentRankingAdapter = new ContentRankingAdapter(getApplicationContext(), items);
                                recyclerView.setLayoutManager(new LinearLayoutManager(RankingActivity.this));
                                recyclerView.setAdapter(contentRankingAdapter);
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
                }) {

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                String auth = "Bearer " + token;
                headers.put("Authorization", auth);
                return headers;
            }
        };

        requestQueue.add(stringRequest);

    }

    private void getDataBulan(String token) {
        String urlApi = "https://tata-surya.skripsijoss.my.id/public/SoalRank/mount";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlApi,
                new Response.Listener<String>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            items = new ArrayList<>();
                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            int totalData = jsonObject.getInt("total_data");

                            if (totalData == 0) {
                                namaRank1.setText("Kosong");
                                pointRank1.setText("-");
                                fotoRank1.setImageResource(R.drawable.avatar);

                                namaRank2.setText("Kosong");
                                pointRank2.setText("-");
                                fotoRank2.setImageResource(R.drawable.avatar);

                                namaRank3.setText("Kosong");
                                pointRank3.setText("-");
                                fotoRank3.setImageResource(R.drawable.avatar);
                            } else if (totalData == 1) {
                                namaRank2.setText("Kosong");
                                pointRank2.setText("-");
                                fotoRank2.setImageResource(R.drawable.avatar);

                                namaRank3.setText("Kosong");
                                pointRank3.setText("-");
                                fotoRank3.setImageResource(R.drawable.avatar);
                            } else if (totalData == 2) {
                                namaRank3.setText("Kosong");
                                pointRank3.setText("-");
                                fotoRank3.setImageResource(R.drawable.avatar);
                            }

                                for (int i = 0; i < jsonArray.length() ; i++) {
                                ContentRankingModel contentRankingModel = new ContentRankingModel();
                                JSONObject object = jsonArray.getJSONObject(i);
                                JSONArray jsonArray1 = object.getJSONArray("user");
                                JSONObject objectUser = jsonArray1.getJSONObject(0);

                                contentRankingModel.setNamaUser(objectUser.getString("nama_lengkap"));
                                contentRankingModel.setDateRank(object.getString("durasi"));
                                contentRankingModel.setPhoto(objectUser.getString("photo"));
                                contentRankingModel.setPoint(object.getString("total_point"));

                                if (jsonArray.length() == 1) {
                                    JSONObject object1 = jsonArray.getJSONObject(0);
                                    JSONArray jsonArrayRank1 = object1.getJSONArray("user");
                                    JSONObject objectUser1 = jsonArrayRank1.getJSONObject(0);

                                    namaRank1.setText(objectUser1.getString("nama_lengkap"));
                                    pointRank1.setText(object1.getString("total_point") + " Points");
                                    String urlPhotoRank1 = objectUser1.getString("photo");
                                    Glide.with(RankingActivity.this).load(urlPhotoRank1).into(fotoRank1);

                                } else if (jsonArray.length() == 2) {
                                    JSONObject object1 = jsonArray.getJSONObject(0);
                                    JSONArray jsonArrayRank1 = object1.getJSONArray("user");
                                    JSONObject objectUser1 = jsonArrayRank1.getJSONObject(0);

                                    namaRank1.setText(objectUser1.getString("nama_lengkap"));
                                    pointRank1.setText(object1.getString("total_point") + " Points");
                                    String urlPhotoRank1 = objectUser1.getString("photo");
                                    Glide.with(RankingActivity.this).load(urlPhotoRank1).into(fotoRank1);

                                    JSONObject object2 = jsonArray.getJSONObject(1);
                                    JSONArray jsonArrayRank2 = object2.getJSONArray("user");
                                    JSONObject objectUser2 = jsonArrayRank2.getJSONObject(0);

                                    namaRank2.setText(objectUser2.getString("nama_lengkap"));
                                    pointRank2.setText(object2.getString("total_point") + " Points");
                                    String urlPhotoRank2 = objectUser2.getString("photo");
                                    Glide.with(RankingActivity.this).load(urlPhotoRank2).into(fotoRank2);
                                } else {
                                    JSONObject object1 = jsonArray.getJSONObject(0);
                                    JSONArray jsonArrayRank1 = object1.getJSONArray("user");
                                    JSONObject objectUser1 = jsonArrayRank1.getJSONObject(0);

                                    namaRank1.setText(objectUser1.getString("nama_lengkap"));
                                    pointRank1.setText(object1.getString("total_point") + " Points");
                                    String urlPhotoRank1 = objectUser1.getString("photo");
                                    Glide.with(RankingActivity.this).load(urlPhotoRank1).into(fotoRank1);

                                    JSONObject object2 = jsonArray.getJSONObject(1);
                                    JSONArray jsonArrayRank2 = object2.getJSONArray("user");
                                    JSONObject objectUser2 = jsonArrayRank2.getJSONObject(0);

                                    namaRank2.setText(objectUser2.getString("nama_lengkap"));
                                    pointRank2.setText(object2.getString("total_point") + " Points");
                                    String urlPhotoRank2 = objectUser2.getString("photo");
                                    Glide.with(RankingActivity.this).load(urlPhotoRank2).into(fotoRank2);

                                    JSONObject object3 =  jsonArray.getJSONObject(2);
                                    JSONArray jsonArrayRank3 = object3.getJSONArray("user");
                                    JSONObject objectUser3 = jsonArrayRank3.getJSONObject(0);

                                    namaRank3.setText(objectUser3.getString("nama_lengkap"));
                                    pointRank3.setText(object3.getString("total_point") + " Points");
                                    String urlPhotoRank3 = objectUser3.getString("photo");
                                    Glide.with(RankingActivity.this).load(urlPhotoRank3).into(fotoRank3);

                                }

                                items.add(contentRankingModel);
                                contentRankingAdapter = new ContentRankingAdapter(getApplicationContext(), items);
                                recyclerView.setLayoutManager(new LinearLayoutManager(RankingActivity.this));
                                recyclerView.setAdapter(contentRankingAdapter);
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
                }) {

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                String auth = "Bearer " + token;
                headers.put("Authorization", auth);
                return headers;
            }
        };

        requestQueue.add(stringRequest);

    }

    private void getDataTahun(String token) {
        String urlApi = "https://tata-surya.skripsijoss.my.id/public/SoalRank/year";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlApi,
                new Response.Listener<String>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            items = new ArrayList<>();
                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length() ; i++) {
                                ContentRankingModel contentRankingModel = new ContentRankingModel();
                                JSONObject object = jsonArray.getJSONObject(i);
                                JSONArray jsonArray1 = object.getJSONArray("user");
                                JSONObject objectUser = jsonArray1.getJSONObject(0);

                                contentRankingModel.setNamaUser(objectUser.getString("nama_lengkap"));
                                contentRankingModel.setDateRank(object.getString("durasi"));
                                contentRankingModel.setPhoto(objectUser.getString("photo"));
                                contentRankingModel.setPoint(object.getString("total_point"));

                                if (jsonArray.length() == 1) {
                                    JSONObject object1 = jsonArray.getJSONObject(0);
                                    JSONArray jsonArrayRank1 = object1.getJSONArray("user");
                                    JSONObject objectUser1 = jsonArrayRank1.getJSONObject(0);

                                    namaRank1.setText(objectUser1.getString("nama_lengkap"));
                                    pointRank1.setText(object1.getString("total_point") + " Points");
                                    String urlPhotoRank1 = objectUser1.getString("photo");
                                    Glide.with(RankingActivity.this).load(urlPhotoRank1).into(fotoRank1);

                                } else if (jsonArray.length() == 2) {
                                    JSONObject object1 = jsonArray.getJSONObject(0);
                                    JSONArray jsonArrayRank1 = object1.getJSONArray("user");
                                    JSONObject objectUser1 = jsonArrayRank1.getJSONObject(0);

                                    namaRank1.setText(objectUser1.getString("nama_lengkap"));
                                    pointRank1.setText(object1.getString("total_point") + " Points");
                                    String urlPhotoRank1 = objectUser1.getString("photo");
                                    Glide.with(RankingActivity.this).load(urlPhotoRank1).into(fotoRank1);

                                    JSONObject object2 = jsonArray.getJSONObject(1);
                                    JSONArray jsonArrayRank2 = object2.getJSONArray("user");
                                    JSONObject objectUser2 = jsonArrayRank2.getJSONObject(0);

                                    namaRank2.setText(objectUser2.getString("nama_lengkap"));
                                    pointRank2.setText(object2.getString("total_point") + " Points");
                                    String urlPhotoRank2 = objectUser2.getString("photo");
                                    Glide.with(RankingActivity.this).load(urlPhotoRank2).into(fotoRank2);
                                } else {
                                    JSONObject object1 = jsonArray.getJSONObject(0);
                                    JSONArray jsonArrayRank1 = object1.getJSONArray("user");
                                    JSONObject objectUser1 = jsonArrayRank1.getJSONObject(0);

                                    namaRank1.setText(objectUser1.getString("nama_lengkap"));
                                    pointRank1.setText(object1.getString("total_point") + " Points");
                                    String urlPhotoRank1 = objectUser1.getString("photo");
                                    Glide.with(RankingActivity.this).load(urlPhotoRank1).into(fotoRank1);

                                    JSONObject object2 = jsonArray.getJSONObject(1);
                                    JSONArray jsonArrayRank2 = object2.getJSONArray("user");
                                    JSONObject objectUser2 = jsonArrayRank2.getJSONObject(0);

                                    namaRank2.setText(objectUser2.getString("nama_lengkap"));
                                    pointRank2.setText(object2.getString("total_point") + " Points");
                                    String urlPhotoRank2 = objectUser2.getString("photo");
                                    Glide.with(RankingActivity.this).load(urlPhotoRank2).into(fotoRank2);

                                    JSONObject object3 =  jsonArray.getJSONObject(2);
                                    JSONArray jsonArrayRank3 = object3.getJSONArray("user");
                                    JSONObject objectUser3 = jsonArrayRank3.getJSONObject(0);

                                    namaRank3.setText(objectUser3.getString("nama_lengkap"));
                                    pointRank3.setText(object3.getString("total_point") + " Points");
                                    String urlPhotoRank3 = objectUser3.getString("photo");
                                    Glide.with(RankingActivity.this).load(urlPhotoRank3).into(fotoRank3);

                                }

                                items.add(contentRankingModel);
                                contentRankingAdapter = new ContentRankingAdapter(getApplicationContext(), items);
                                recyclerView.setLayoutManager(new LinearLayoutManager(RankingActivity.this));
                                recyclerView.setAdapter(contentRankingAdapter);
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
                }) {

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                String auth = "Bearer " + token;
                headers.put("Authorization", auth);
                return headers;
            }
        };

        requestQueue.add(stringRequest);

    }
}