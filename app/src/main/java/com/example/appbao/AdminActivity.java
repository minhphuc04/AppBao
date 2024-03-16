package com.example.appbao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class AdminActivity extends AppCompatActivity {
    TextView Cate,User,News;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        addControls();
        addEvents();

    }

    private void addEvents() {
        Cate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this,CategoryCrudActivity.class );
                startActivity(intent);
            }
        });
//        User.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(AdminActivity.this,UserActivity.class );
//                startActivity(intent);
//            }
//        });
        News.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this,NewsCrudActivity.class );
                startActivity(intent);

            }
        });
    }

    private void addControls() {
        Cate = findViewById(R.id.Cate);
        User = findViewById(R.id.User);
        News = findViewById(R.id.News);
    }


}