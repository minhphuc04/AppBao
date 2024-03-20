package com.example.appbao;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class UpdateNewsActivity extends AppCompatActivity {
    EditText edtMaNewCN, edtTitleCN, edtContentCN, edtCreatedDateCN, edtCateIDCN, edtAuthorIDCN;
    Button btnUpdate, btnDelete;
    ImageButton btnCameraCN;
    ImageView ivHinhNewCN;
    int REQUESTCODE_CAMERA = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_news);
        addControls();

        xylyDelete();
        addEventCamera();
    }

    private void addEventCamera() {
        btnCameraCN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open gallery to choose image
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, REQUESTCODE_CAMERA);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUESTCODE_CAMERA && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                if (bitmap != null) {
                    ivHinhNewCN.setImageBitmap(bitmap);
                    // Here, we don't need to update immediately after selecting image
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
                        int kq = NewsCrudActivity.database.delete("News", "NewsID=?",
                                new String[]{edtMaNewCN.getText().toString()});
                        if (kq > 0) {
                            finish();
                        }
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

    private void addControls() {
        edtMaNewCN = findViewById(R.id.edtMaNewCN);
        edtTitleCN = findViewById(R.id.edtTitleCN);
        edtContentCN = findViewById(R.id.edtContentCN);
        ivHinhNewCN = findViewById(R.id.ivHinhNewCN);
        edtCreatedDateCN = findViewById(R.id.edtCreatedDateCN);
        edtCateIDCN = findViewById(R.id.edtCateNewCN);
        edtAuthorIDCN = findViewById(R.id.edtAuthorCN);
        btnUpdate = findViewById(R.id.btnUpdateNews);
        btnDelete = findViewById(R.id.btnDeleteNews);
        btnCameraCN = findViewById(R.id.btnCameraCN);
        Intent intent = getIntent();
        News u = (News) intent.getSerializableExtra("uu");
        edtMaNewCN.setText(u.getMa());
        edtTitleCN.setText(u.getTitle());
        edtContentCN.setText(u.getContent());
        edtCateIDCN.setText(u.getCategoryID());
        edtAuthorIDCN.setText(u.getAuthorID());
        byte[] hinh = u.getHinh();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinh,0,hinh.length);
        ivHinhNewCN.setImageBitmap(bitmap);
        edtCreatedDateCN.setText(u.getCreatedDate());
        edtMaNewCN.setEnabled(false);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xulyCapnhat();
            }
        });
    }

    private Bitmap hinh; // Declare the Bitmap variable globally in the class

    private void xulyCapnhat() {
        // Check if an image has been selected
        if (ivHinhNewCN.getDrawable() != null) {
            // Convert ImageView to Bitmap
            ivHinhNewCN.setDrawingCacheEnabled(true);
            ivHinhNewCN.buildDrawingCache();
            Bitmap bitmap = ivHinhNewCN.getDrawingCache();
            hinh = bitmap;
        }

        ContentValues values = new ContentValues();
        values.put("Title", edtTitleCN.getText().toString());
        values.put("Content", edtContentCN.getText().toString());
        values.put("CategoryID", edtCateIDCN.getText().toString());
        // Check if hinh is not null before putting it into ContentValues
        if (hinh != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            hinh.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] hinhBytes = byteArrayOutputStream.toByteArray();
            values.put("ImageURL", hinhBytes);
        }
        values.put("CreatedDate", edtCreatedDateCN.getText().toString());
        values.put("AuthorID", edtAuthorIDCN.getText().toString());

        long kq = NewsCrudActivity.database.update("News", values, "NewsID=?",
                new String[]{edtMaNewCN.getText().toString()});
        if (kq > 0) {
            Toast.makeText(UpdateNewsActivity.this, "Update record successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(UpdateNewsActivity.this, "Update record failed", Toast.LENGTH_SHORT).show();
        }
    }

}
