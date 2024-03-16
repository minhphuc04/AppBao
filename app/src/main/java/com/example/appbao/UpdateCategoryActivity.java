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
                builder.setTitle("Delete");
                builder.setMessage("Do you want to delete?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int kq = CategoryCrudActivity.database.delete("Categories","CategoryID=?",
                                new String[]{edtmaCN.getText().toString()});
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
                values.put("CategoryID",edtmaCN.getText().toString());
                values.put("Name",edtTenCN.getText().toString());
                int kq = CategoryCrudActivity.database.update("Categories",values,"CategoryID=?",
                        new String[]{edtmaCN.getText().toString()});
                if(kq>0){
                    finish();
                }
                else
                {
                    Toast.makeText(UpdateCategoryActivity.this, "Update new record Fail", Toast.LENGTH_SHORT).show();
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
        Categories u = (Categories) intent.getSerializableExtra("uu");
        edtmaCN.setText(u.getMa().toString());
        edtTenCN.setText(u.getTen().toString());
        edtmaCN.setEnabled(false);

    }
}