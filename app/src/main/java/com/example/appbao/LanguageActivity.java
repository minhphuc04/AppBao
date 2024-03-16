package com.example.appbao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Button;

import java.util.Locale;

public class LanguageActivity extends AppCompatActivity {
    Button btnViet,btnKorea,btnEng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        addControls();
        addEvents();
    }
    private void setLanguage(String language){
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        Locale locale = new Locale(language);
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration,resources.getDisplayMetrics());
    }
    private void addEvents() {
        btnViet.setOnClickListener(view ->{
            setLanguage("vi");
            Intent intent = new Intent(LanguageActivity.this, MainActivity2.class);
            startActivity(intent);

        });
        btnEng.setOnClickListener(view ->{
            setLanguage("enm");
            Intent intent = new Intent(LanguageActivity.this, MainActivity2.class);
            startActivity(intent);
        });
        btnKorea.setOnClickListener(view ->{
            setLanguage("kr");
            Intent intent = new Intent(LanguageActivity.this, MainActivity2.class);
            startActivity(intent);
        });
    }

    private void addControls() {
        btnEng=findViewById(R.id.btnEng);
        btnViet=findViewById(R.id.btnViet);
        btnKorea=findViewById(R.id.btnKorea);
    }
}