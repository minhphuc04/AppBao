package com.example.appbao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class NewsCrudActivity extends AppCompatActivity {
    public static final String DATABASE_NAME = "SqlAppBao1";
    public static final String DB_SUFFIX_PATH = "/databases/";
    public static SQLiteDatabase database = null;
    ArrayList<News> adapterNewsList;
    Button openNewButton;
    ListView lvNews;
    NewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_crud);
        addControls();
        addEvents();
        xulycapnhat();
        processCopy();
    }

    private void processCopy() {
        try {
            File file = getDatabasePath(DATABASE_NAME);

            if (!file.exists()) {
                copyDatabaseFromAssets();
                Toast.makeText(this, "Copy Database Successful", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception ex) {
            Toast.makeText(this, "Copy Database Fail", Toast.LENGTH_SHORT).show();
        }
    }

    private void xulycapnhat() {
        lvNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News news = adapterNewsList.get(position);
                Intent intent = new Intent(NewsCrudActivity.this, UpdateNewsActivity.class);
                intent.putExtra("uu", news);
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
        database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        adapterNewsList.clear();
        Cursor cursor = database.rawQuery("SELECT * FROM News", null);

        while (cursor.moveToNext()) {
            String ma = cursor.getString(0);
            String title = cursor.getString(1);
            String content = cursor.getString(2);
            String cateID = cursor.getString(3);
            byte[] image = cursor.getBlob(4);
            String publishedDate = cursor.getString(5);
            String authorID = cursor.getString(6);

            News u = new News(ma, title, content, cateID, image, publishedDate, authorID);
            adapterNewsList.add(u);
        }
        cursor.close();
        newsAdapter.notifyDataSetChanged();
    }

    private void addEvents() {
        openNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewsCrudActivity.this, ThemNewsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addControls() {
        openNewButton = findViewById(R.id.btnOpenNewNews);
        lvNews = findViewById(R.id.lvNews);
        adapterNewsList = new ArrayList<>();
        newsAdapter = new NewsAdapter(this, R.layout.layout_item_news, adapterNewsList);
        lvNews.setAdapter(newsAdapter);
    }

    private void copyDatabaseFromAssets() {
        try {
            InputStream inputFile = getAssets().open(DATABASE_NAME);
            String outputFileName = getDatabasePath();
            File file = new File(getApplicationInfo().dataDir + DB_SUFFIX_PATH);
            if (!file.exists())
                file.mkdir();
            OutputStream outFile = new FileOutputStream(outputFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputFile.read(buffer)) > 0) outFile.write(buffer, 0, length);
            outFile.flush();
            outFile.close();
            inputFile.close();
        } catch (Exception ex) {
            Log.e("Error", ex.toString());
        }
    }

    public String getDatabasePath() {
        return getApplicationInfo().dataDir + DB_SUFFIX_PATH + DATABASE_NAME;
    }
}
