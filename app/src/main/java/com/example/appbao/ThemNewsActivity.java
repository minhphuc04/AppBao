package com.example.appbao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ThemNewsActivity extends AppCompatActivity {
    EditText edtMa, edtTitle, edtContent, edtImage, edtCreatedDate,edtCateID,edtAuthorID;
    Button btnSaveNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_news);
        addControls();
        addEvent();

        // Tạo ngày giờ hiện tại và đặt giá trị cho EditText edtCreatedDate
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String currentDateAndTime = sdf.format(new Date());
        edtCreatedDate.setText(currentDateAndTime);
    }

    private void addEvent() {
        btnSaveNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ma = edtMa.getText().toString();
                String title = edtTitle.getText().toString();
                String content = edtContent.getText().toString();
                String cateID = edtCateID.getText().toString();
                String imageUrl = edtImage.getText().toString();
                String createdDate = edtCreatedDate.getText().toString(); // Lấy giá trị từ EditText edtCreatedDate
              String authorID = edtAuthorID.getText().toString();

                if (ma.isEmpty() || title.isEmpty() || content.isEmpty() || imageUrl.isEmpty()) {
                    Toast.makeText(ThemNewsActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Tiến hành insert dữ liệu
                    ContentValues values = new ContentValues();
                    values.put("NewsID", ma);
                    values.put("Title", title);
                    values.put("Content", content);
                    values.put("CategoryID",cateID);
                    values.put("ImageURL", imageUrl);
                    values.put("CreatedDate", createdDate); // Sử dụng giá trị từ EditText edtCreatedDate
                   values.put("AuthorID",authorID);

                    long kq = NewsCrudActivity.database.insert("News", null, values);
                    if (kq != -1) {
                        Toast.makeText(ThemNewsActivity.this, "Insert new record successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(ThemNewsActivity.this, "Insert new record failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void addControls() {
        edtMa = findViewById(R.id.edtMaNews);
        edtTitle = findViewById(R.id.edtTitle);
        edtContent = findViewById(R.id.edtContent);
        edtImage = findViewById(R.id.edtImage);
        edtCreatedDate = findViewById(R.id.edtCreatedDate);
       edtAuthorID = findViewById(R.id.edtAuthorIDnew);
        edtCateID = findViewById(R.id.edtCateIDNews);
        btnSaveNews = findViewById(R.id.btnSaveNews);

    }
}
