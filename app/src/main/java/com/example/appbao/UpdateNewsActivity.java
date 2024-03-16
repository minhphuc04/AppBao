package com.example.appbao;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateNewsActivity extends AppCompatActivity {
    EditText edtMaNewCN,edtTitleCN,edtContentCN,edtImageCN,edtCreatedDateCN,edtCateIDCN,edtAuthorIDCN;
    Button btnUpdate,btnDelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_news);
        addcontrols();
        xulyCapnhat();
        xylyDelete();
    }
    private void xylyDelete() {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateNewsActivity.this);
                builder.setTitle("Delete");
                builder.setMessage("Do you want to delete?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int kq = NewsCrudActivity.database.delete("News","NewsID=?",
                                new String[]{edtMaNewCN.getText().toString()});
                        if(kq>0){finish();}

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();

            }
        });
    }

    private void xulyCapnhat() {
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put("NewsID",edtMaNewCN.getText().toString());
                values.put("Title",edtTitleCN.getText().toString());
                values.put("Content",edtContentCN.getText().toString());
              values.put("CategoryID",edtCateIDCN.getText().toString());
                values.put("ImageURL",edtImageCN.getText().toString());
                values.put("CreatedDate",edtCreatedDateCN.getText().toString());
               values.put("AuthorID",edtAuthorIDCN.getText().toString());
                int kq = NewsCrudActivity.database.update("News",values,"NewsID=?",
                        new String[]{edtMaNewCN.getText().toString()});
                if(kq>0){
                    finish();
                }
                else
                {
                    Toast.makeText(UpdateNewsActivity.this, "Update new record Fail", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addcontrols() {
        edtMaNewCN=findViewById(R.id.edtMaNewCN);
        edtTitleCN=findViewById(R.id.edtTitleCN);
        edtContentCN=findViewById(R.id.edtContentCN);
        edtImageCN=findViewById(R.id.edtImageCN);
        edtCreatedDateCN=findViewById(R.id.edtCreatedDateCN);
     edtCateIDCN = findViewById(R.id.edtCateNewCN);
     edtAuthorIDCN = findViewById(R.id.edtAuthorCN);
        btnUpdate=findViewById(R.id.btnUpdateNews);
        btnDelete=findViewById(R.id.btnDeleteNews);
        Intent intent = getIntent();
        News u = (News) intent.getSerializableExtra("uu");
        edtMaNewCN.setText(u.getMa());
        edtTitleCN.setText(u.getTitle());
        edtContentCN.setText(u.getContent());
        edtImageCN.setText(u.getImage());
        edtCateIDCN.setText(u.getCategoryID());
        edtAuthorIDCN.setText(u.getAuthorID());
        edtCreatedDateCN.setText(u.getCreatedDate());
        edtMaNewCN.setEnabled(false);

    }
}
