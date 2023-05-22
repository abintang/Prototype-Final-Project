package com.example.markerlessproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.google.android.material.button.MaterialButton;

public class InfoSplash extends AppCompatActivity {

    CheckBox checkBox;
    MaterialButton btnLanjutkan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_splash);

        checkBox = findViewById(R.id.checkbox_id);
        btnLanjutkan = findViewById(R.id.btn_lanjutkan);

        SharedPreferences sharedPreferences = getSharedPreferences("skipInfo", MODE_PRIVATE);
        boolean infoChecked = sharedPreferences.getBoolean("Checked", false);

        if (!infoChecked) {
            btnLanjutkan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(InfoSplash.this, SelectionMenu.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                    if (checkBox.isChecked()) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("Checked", true);
                        editor.apply();
                    }
                }
            });

        } else {
            Intent intent = new Intent(InfoSplash.this, SelectionMenu.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }



    }
}




