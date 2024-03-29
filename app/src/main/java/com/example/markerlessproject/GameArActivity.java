package com.example.markerlessproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.markerlessproject.models.ContentMateriModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GameArActivity extends AppCompatActivity {

    MaterialCardView btnJawaban1, btnJawaban2, btnJawaban3, btnJawaban4, correctSection, unCorrectSection, nextSection, cardLoading, cardGame;
    MaterialButton nextButton;

    ImageView imageSoal;

    TextView textSoal, textJawaban1, textJawaban2, textJawaban3, textJawaban4, textScore, currentNumberQuiz, token, timer;
    int score, no, number, totalJawabanBenar;

    String urlSoal;

    private Boolean opened;

    String tokenString = "";

    int idToken;

    FirebaseAuth firebaseAuth;

    FirebaseUser firebaseUser;

    private int detik;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        btnJawaban1 = findViewById(R.id.btn_answer1);
        btnJawaban2 = findViewById(R.id.btn_answer2);
        btnJawaban3 = findViewById(R.id.btn_answer3);
        btnJawaban4 = findViewById(R.id.btn_answer4);

        imageSoal = findViewById(R.id.imageSoal);

        correctSection = findViewById(R.id.correctSection);
        unCorrectSection = findViewById(R.id.uncorrectSection);
        nextSection = findViewById(R.id.nextSection);

        correctSection.setVisibility(View.INVISIBLE);
        unCorrectSection.setVisibility(View.INVISIBLE);
        nextSection.setVisibility(View.INVISIBLE);

        token = findViewById(R.id.token);

        nextButton = findViewById(R.id.btn_next);

        textSoal = findViewById(R.id.tv_soal);
        textJawaban1 = findViewById(R.id.tv_answ1);
        textJawaban2 = findViewById(R.id.tv_answ2);
        textJawaban3 = findViewById(R.id.tv_answ3);
        textJawaban4 = findViewById(R.id.tv_answ4);
        textScore = findViewById(R.id.tv_score);
        currentNumberQuiz = findViewById(R.id.tv_number_quiz);

        cardGame = findViewById(R.id.cardGame);
        cardLoading = findViewById(R.id.loadingCard);

        score = getIntent().getExtras().getInt("score");
        no = getIntent().getExtras().getInt("no");
        number = getIntent().getExtras().getInt("number");
        totalJawabanBenar = getIntent().getExtras().getInt("jawabanBenar");
        detik = getIntent().getExtras().getInt("detik");

        timer = findViewById(R.id.tv_timer);

        number++;


        textScore.setText(Integer.toString(score));
        String textCurrentNumber = number + "/10";
        currentNumberQuiz.setText(textCurrentNumber);

        urlSoal = "https://tata-surya.skripsijoss.my.id/public/SoalGame";

        timer();

        getData();

    }

    private void setAnimationUp(MaterialCardView cardView) {
        TranslateAnimation animation = new TranslateAnimation(0, 0, cardView.getHeight(), 0);
        animation.setDuration(500);
        cardView.startAnimation(animation);
    }

    private void setAnimationDown(MaterialCardView cardView) {
        TranslateAnimation animation = new TranslateAnimation(0, 0, -cardView.getHeight(), 0);
        animation.setDuration(500);
        cardView.startAnimation(animation);
    }

    private void getData() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlSoal,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            String jawabanBenar;

                            JSONObject object = jsonArray.getJSONObject(no);
                            textSoal.setText(object.getString("soal"));
                            String namaImg = object.getString("gambar");
                            String urlGambarSoal = "https://tata-surya.skripsijoss.my.id/public/quiz/" + namaImg;
                            Glide.with(GameArActivity.this).load(urlGambarSoal).into(imageSoal);

                            jawabanBenar = object.getString("jawaban");

                            JSONArray jawaban = object.getJSONArray("pilihan");
                            JSONObject A = jawaban.getJSONObject(0);
                            JSONObject B = jawaban.getJSONObject(1);
                            JSONObject C = jawaban.getJSONObject(2);
                            JSONObject D = jawaban.getJSONObject(3);

                            getNamaJawaban(textJawaban1, A);
                            getNamaJawaban(textJawaban2, B);
                            getNamaJawaban(textJawaban3, C);
                            getNamaJawaban(textJawaban4, D);
                            getJawabanBenar(btnJawaban1, textJawaban1, jawabanBenar, btnJawaban2, btnJawaban3, btnJawaban4);
                            getJawabanBenar(btnJawaban2, textJawaban2, jawabanBenar, btnJawaban3, btnJawaban1, btnJawaban4);
                            getJawabanBenar(btnJawaban3, textJawaban3, jawabanBenar, btnJawaban2, btnJawaban1, btnJawaban4);
                            getJawabanBenar(btnJawaban4, textJawaban4, jawabanBenar, btnJawaban1, btnJawaban2, btnJawaban3);

                            cardLoading.setVisibility(View.GONE);
                            cardGame.setVisibility(View.VISIBLE);

                            if (number >= 10) {
                                nextButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        getIdToken();
                                        Intent intent = new Intent(GameArActivity.this, ResultScoreActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.putExtra("score", score);
                                        intent.putExtra("detik", detik);
                                        intent.putExtra("jawabanBenar", totalJawabanBenar);
                                        startActivity(intent);
                                    }
                                });
                            } else {
                                nextButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Random random = new Random();
                                        int randomNumber;
                                        randomNumber = random.nextInt(36);

                                        while (randomNumber == no) {
                                            randomNumber = random.nextInt(36);
                                        }
                                        Intent intent = new Intent(GameArActivity.this, GameArActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.putExtra("no", randomNumber);
                                        intent.putExtra("number", number);
                                        intent.putExtra("score", score);
                                        intent.putExtra("jawabanBenar", totalJawabanBenar);
                                        intent.putExtra("detik", detik);
                                        startActivity(intent);
                                    }
                                });
                            }

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
                }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        requestQueue.add(stringRequest);

    }

    private void getNamaJawaban(TextView textView, JSONObject jsonObject) throws JSONException {
        textView.setText(jsonObject.getString("value"));
    }

    private void getJawabanBenar(MaterialCardView btnJawaban1, TextView textJawaban1, String jawabanBenar, MaterialCardView btnJawaban2, MaterialCardView btnJawaban3, MaterialCardView btnJawaban4) {
        btnJawaban1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnJawaban1.setClickable(false);
                btnJawaban2.setClickable(false);
                btnJawaban3.setClickable(false);
                btnJawaban4.setClickable(false);

                if (jawabanBenar.equalsIgnoreCase(textJawaban1.getText().toString())) {
                    unCorrectSection.setVisibility(View.INVISIBLE);
                    correctSection.setVisibility(View.VISIBLE);
                    nextSection.setVisibility(View.VISIBLE);
                    setAnimationDown(correctSection);
                    setAnimationUp(nextSection);

                    totalJawabanBenar += 1;
                    score += 10;
                } else {
                    nextSection.setVisibility(View.VISIBLE);
                    unCorrectSection.setVisibility(View.VISIBLE);
                    correctSection.setVisibility(View.INVISIBLE);
                    setAnimationDown(unCorrectSection);
                    setAnimationUp(nextSection);

                    if (score != 0) {
                        score -= 5;
                        if (score < 0) {
                            score = 0;
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(GameArActivity.this, MenuGameActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void getIdToken() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        assert firebaseUser != null;

        String url = "https://tata-surya.skripsijoss.my.id/public/user/detail/" + firebaseUser.getUid();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject object = jsonObject.getJSONObject("data");
                            idToken = object.getInt("id");
                            tokenString = object.getString("api_token");
                            String jawabanBenar = totalJawabanBenar + "/10";
                            String totalScore = Integer.toString(score);

                            postPoint(Integer.toString(idToken), totalScore, jawabanBenar, tokenString);
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

    public void postPoint(String id, String totalPoint, String jawabanBenar, String token) {
        String url = "https://tata-surya.skripsijoss.my.id/public/SoalRank/insert";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(GameArActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_user", String.valueOf(id));
                params.put("total_point", totalPoint);
                params.put("jawaban_benar", jawabanBenar);
                params.put("durasi", String.valueOf(detik));
                return params;
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                String auth = "Bearer " + token;
                headers.put("Authorization", auth);
                return headers;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }


    public void timer() {
        final Handler handler
                = new Handler();

        handler.post(new Runnable() {
            @Override
            public void run()
            {
                timer.setText(detik +" Detik");
                detik++;
                handler.postDelayed(this, 1000);
            }
        });

    }
}
