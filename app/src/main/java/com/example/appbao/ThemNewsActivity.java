package com.example.appbao;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ThemNewsActivity extends AppCompatActivity {
    EditText edtMa, edtTitle, edtContent, edtCreatedDate, edtCateID, edtAuthorID;
    Button btnSaveNews;
    ImageButton btnCamera;
    ImageView ivHinhNew;
    int REQUESTCODE_CAMERA = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_news);
        addControls();
        addEvents();
        addEventCamera();

        // Tạo ngày giờ hiện tại và đặt giá trị cho EditText edtCreatedDate
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String currentDateAndTime = sdf.format(new Date());
        edtCreatedDate.setText(currentDateAndTime);
    }

    private void saveImageToDatabase(Bitmap bitmap) {
        // Xu ly hinh Bitmap --> byte[]
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
          byte[] hinh = byteArrayOutputStream.toByteArray();
btnSaveNews.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        ContentValues values = new ContentValues();
        values.put("NewsID", edtMa.getText().toString());
        values.put("Title", edtTitle.getText().toString());
        values.put("Content", edtContent.getText().toString());
        values.put("CategoryID", edtCateID.getText().toString());
        values.put("ImageURL", hinh); // Lưu dữ liệu ảnh dưới dạng byte[]
        values.put("CreatedDate", edtCreatedDate.getText().toString());
        values.put("AuthorID", edtAuthorID.getText().toString());

        long kq = NewsCrudActivity.database.insert("News", null, values);
        if (kq != -1) {
            Toast.makeText(ThemNewsActivity.this, "Insert new record successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(ThemNewsActivity.this, "Insert new record failed", Toast.LENGTH_SHORT).show();
        }

    }

});
        // Lưu ảnh vào cơ sở dữ liệu ở đây


    }

    private void addEventCamera() {
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mở thư viện ảnh để chọn ảnh
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*"); // Chỉ lựa chọn các tập tin hình ảnh
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
                    ivHinhNew.setImageBitmap(bitmap);
                    saveImageToDatabase(bitmap); // Lưu ảnh vào cơ sở dữ liệu khi chọn xong
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void addEvents() {
        btnSaveNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThemNewsActivity.this,NewsCrudActivity.class);
                startActivity(intent);

                // Bạn có thể thêm logic xử lý khi nhấn nút lưu ở đây nếu cần
            }
        });
    }

    private void addControls() {
        edtMa = findViewById(R.id.edtMaNews);
        edtTitle = findViewById(R.id.edtTitle);
        edtContent = findViewById(R.id.edtContent);
        btnCamera = findViewById(R.id.btnCamera);
        ivHinhNew=findViewById(R.id.ivHinhNew);
        edtCreatedDate = findViewById(R.id.edtCreatedDate);
        edtAuthorID = findViewById(R.id.edtAuthorIDnew);
        edtCateID = findViewById(R.id.edtCateIDNews);
        btnSaveNews = findViewById(R.id.btnSaveNews);

    }
}
