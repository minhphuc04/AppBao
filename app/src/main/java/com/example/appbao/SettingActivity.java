package com.example.appbao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingActivity extends AppCompatActivity {
    ImageView imgFin;
    TextView txtLogFin;

    LinearLayout nav_view ;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        imgFin = findViewById(R.id.clickLoginFin);
        txtLogFin = findViewById(R.id.txtLogFin);


        nav_view = findViewById(R.id.nav_view);

        mAuth = FirebaseAuth.getInstance()  ;
        currentUser = mAuth.getCurrentUser();
        imgFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SettingActivity.this, FingerLoginActivity.class);
                startActivity(i);
            }
        });



        txtLogFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SettingActivity.this, FingerLoginActivity.class);
                startActivity(i);
            }
        });

    }
}
