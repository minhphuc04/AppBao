package com.example.appbao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;



import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class CategoryCrudActivity extends AppCompatActivity {

    public String DATABASE_NAME="SqlAppBao1";
    public String DB_SUFFIX_PATH="/databases/";
    public static SQLiteDatabase database=null;
    ArrayAdapter<Categories> adapterCate;
    ImageButton opennew;
    ListView lvCate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_crud);
        addControls();
        addEvent();
        xulycapnhat();
        processCopy();

    }

    private void processCopy() {
        try {
            File file = getDatabasePath(DATABASE_NAME);

            if(!file.exists()){
                copyDatabaseFromAssest();
                Toast.makeText(this, "Copy Database Successful", Toast.LENGTH_SHORT).show();
            }

        }
        catch (Exception ex)
        {
            Toast.makeText(this, "Copy Database Fail", Toast.LENGTH_SHORT).show();
        }
    }

    private void xulycapnhat() {
        lvCate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Categories Categories = adapterCate.getItem(position);
                Intent intent = new Intent(CategoryCrudActivity.this,UpdateCategoryActivity.class);
                intent.putExtra("uu",Categories);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }
    private void loadData() {
        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        Cursor cursor = database.rawQuery("SELECT * FROM Categories",  null);

        adapterCate.clear();
        while (cursor.moveToNext())
        {
            String ma = cursor.getString(0);
            String ten = cursor.getString(1);

            Categories u = new Categories(ma,ten);
            adapterCate.add(u);
        }
        cursor.close();
    }
    private void addControls() {
        opennew = findViewById(R.id.BtnNew); // Make sure R.id.BtnNew matches the ID in your layout XML file
        lvCate = findViewById(R.id.lvCate);
        adapterCate = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        lvCate.setAdapter(adapterCate);
    }

    private void addEvent() {
        opennew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryCrudActivity.this, ThemCategoryActivity.class);
                startActivity(intent);
            }
        });
    }

    private void copyDatabaseFromAssest() {
        try {
            InputStream inputFile = getAssets().open(DATABASE_NAME);
            String outputFileName=getDatabasePath();
            File file = new File(getApplicationInfo().dataDir+DB_SUFFIX_PATH);
            if(!file.exists())

                file.mkdir();
            OutputStream outFile = new FileOutputStream(outputFileName);
            byte[] buffer= new byte[1024];
            int length;
            while ((length=inputFile.read(buffer))>0) outFile.write(buffer,0,length);
            outFile.flush();
            outFile.close();
            inputFile.close();

        }
        catch (Exception ex)
        {
            Log.e("Error",ex.toString());
        }

    }
    public String getDatabasePath(){
        return  getApplicationInfo().dataDir+DB_SUFFIX_PATH+DATABASE_NAME;
    }
}