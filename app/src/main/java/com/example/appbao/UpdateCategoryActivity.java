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

public class UpdateCategoryActivity extends AppCompatActivity {
    EditText edtmaCN,edtTenCN;
    Button btnUpdate,btnDelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_category);
        addcontrols();
        xulyCapnhat();
        xylyDelete();
    }

    private void xylyDelete() {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateCategoryActivity.this);
                builder.setTitle("Xóa");
                builder.setMessage("Bạn muốn xóa dữ liệu này?");
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int kq = CategoryCrudActivity.database.delete("Categories","CategoryID=?",
                                new String[]{edtmaCN.getText().toString()});
                        if(kq>0){finish();}

                    }
                });
                builder.setNegativeButton("Hủy bỏ", new DialogInterface.OnClickListener() {
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
                // Kiểm tra xem cả hai trường CategoryID và Name có được nhập không
                String categoryID = edtmaCN.getText().toString().trim();
                String name = edtTenCN.getText().toString().trim();

                if (categoryID.isEmpty() || name.isEmpty()) {
                    // Nếu một trong hai trường rỗng, hiển thị thông báo và không thực hiện cập nhật vào cơ sở dữ liệu
                    Toast.makeText(UpdateCategoryActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    // Nếu cả hai trường đều có giá trị, thực hiện cập nhật dữ liệu vào cơ sở dữ liệu
                    ContentValues values = new ContentValues();
                    values.put("CategoryID", categoryID);
                    values.put("Name", name);

                    int kq = CategoryCrudActivity.database.update("Categories", values, "CategoryID=?", new String[]{categoryID});
                    if (kq > 0) {
                        // Nếu cập nhật thành công, kết thúc activity
                        finish();
                    } else {
                        // Nếu cập nhật không thành công, hiển thị thông báo
                        Toast.makeText(UpdateCategoryActivity.this, "Cập nhật dữ liệu thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    private void addcontrols() {
        edtmaCN=findViewById(R.id.edtMaCN);
        edtTenCN=findViewById(R.id.edtNameCN);
        btnUpdate=findViewById(R.id.btnUpdate);
        btnDelete=findViewById(R.id.btnDelete);

        Intent intent = getIntent();
        Categories category = (Categories) intent.getSerializableExtra("category");

        if (category != null) {
            edtmaCN.setText(category.getMa());
            edtTenCN.setText(category.getTen());
            edtmaCN.setEnabled(false);
        } else {
            // Xử lý khi đối tượng Categories là nullmkk
            Toast.makeText(this, null, Toast.LENGTH_SHORT).show();
            // Ví dụ: finish();
        }
    }


}