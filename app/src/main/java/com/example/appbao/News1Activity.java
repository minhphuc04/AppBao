package com.example.appbao;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class News1Activity extends AppCompatActivity {
Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_baibao);
        back = findViewById(R.id.back);
        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        if (intent != null) {
            // Lấy dữ liệu từ Intent
            String title = intent.getStringExtra("title");
            String content = intent.getStringExtra("content");
            String time = intent.getStringExtra("time");
            byte[] imageByteArray = intent.getByteArrayExtra("image");
            // Nếu dữ liệu không rỗng, hiển thị lên layout
            if (title != null && content != null && imageByteArray != null && time!=null) {
                TextView titleTextView = findViewById(R.id.txtTitlee);
                TextView contentTextView = findViewById(R.id.Content11);
                TextView txtTimeDetail = findViewById(R.id.txtTimeDetail);
                ImageView ImageDetail = findViewById(R.id.ImageDetail);
                titleTextView.setText(title);
                txtTimeDetail.setText(time);
                contentTextView.setText(content);
                // Chuyển đổi mảng byte thành đối tượng Bitmap
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
                // Hiển thị hình ảnh trên ImageView
                ImageDetail.setImageBitmap(bitmap);
            }
        }
    }

    public void Back(View view) {
        finish();

    }
}
