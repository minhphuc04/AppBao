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
                // Kiểm tra xem các trường CategoryID và Name có được nhập không
                String categoryID = edtMaCate.getText().toString().trim();
                String name = edtnNameCate.getText().toString().trim();

                if (categoryID.isEmpty() || name.isEmpty()) {
                    // Nếu một trong hai trường rỗng, hiển thị thông báo và không thực hiện thêm vào cơ sở dữ liệu
                    Toast.makeText(ThemCategoryActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    // Nếu cả hai trường đều có giá trị, thêm dữ liệu vào cơ sở dữ liệu
                    ContentValues values = new ContentValues();
                    values.put("CategoryID", categoryID);
                    values.put("Name", name);

                    long kq = CategoryCrudActivity.database.insert("Categories", null, values);
                    if (kq > 0) {
                        // Nếu thêm thành công, kết thúc activity
                        finish();
                    } else {
                        // Nếu thêm không thành công, hiển thị thông báo
                        Toast.makeText(ThemCategoryActivity.this, "Thêm dữ liệu thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    private void addControls() {
        edtMaCate = findViewById(R.id.edtMaNews);
        edtnNameCate = findViewById(R.id.edtName);
        btnSave = findViewById(R.id.btnSave);

    }
}