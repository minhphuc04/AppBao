package com.example.appbao;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class News1Activity extends AppCompatActivity {
    Button back,bottomSheetButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_baibao);
        back = findViewById(R.id.back);

        //Bottom Sheet
        bottomSheetButton=findViewById(R.id.bottom_sheet);
        bottomSheetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
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
    private void showDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet_layout);

        Button btnFontSF = dialog.findViewById(R.id.fontSF);
        Button btnFontBookerly = dialog.findViewById(R.id.fontBookerly);
        final TextView txtChoose = dialog.findViewById(R.id.txtChoose);

        btnFontSF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set font of txtChoose to SF
                txtChoose.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
                dialog.dismiss(); // Dismiss the dialog after font change
            }
        });

        btnFontBookerly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set font of txtChoose to Bookerly
                Typeface fontBookerly = Typeface.createFromAsset(getAssets(), "@fonts/bookerly"); // Replace "bookerly.ttf" with your font file
                txtChoose.setTypeface(fontBookerly);
                dialog.dismiss(); // Dismiss the dialog after font change
            }
        });


        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations=R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    public void Back(View view) {
        finish();

    }
}
