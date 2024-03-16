package com.example.appbao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ThemCategoryActivity extends AppCompatActivity {
    EditText edtMaCate, edtnNameCate;
    Button btnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_category);
        addControls();
        addEvent();
    }

    private void addEvent() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put("CategoryID",edtMaCate.getText().toString());
                values.put("Name",edtnNameCate.getText().toString());

                long kq = CategoryCrudActivity.database.insert("Categories",null,values);
                if(kq>0)
                {
                    finish();

                }
                else
                    Toast.makeText(ThemCategoryActivity.this, "Insert new record Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addControls() {
        edtMaCate = findViewById(R.id.edtMaNews);
        edtnNameCate = findViewById(R.id.edtName);
        btnSave = findViewById(R.id.btnSave);

    }
}