package com.example.appbao;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class HomeActivity extends Fragment {
    public static final String DATABASE_NAME = "SqlAppBao1";
    public static final String DB_SUFFIX_PATH = "/databases/";
    public static SQLiteDatabase database = null;
    ArrayList<BaiBao> mylist;
    MyArrayAdapter myAdapter;
    ListView lv;

    AdapterTest adapterTest;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_home, container, false);
        lv = v.findViewById(R.id.lv);
        mylist = new ArrayList<>();

        // Mở hoặc tạo cơ sở dữ liệu
        database = getActivity().openOrCreateDatabase(DATABASE_NAME, getActivity().MODE_PRIVATE, null);

        // Thực hiện truy vấn để lấy dữ liệu từ bảng News
        Cursor cursor = database.rawQuery("SELECT * FROM News", null);

        // Xóa dữ liệu cũ trước khi thêm mới
        mylist.clear();

        // Duyệt qua các dòng kết quả của truy vấn và thêm vào danh sách
        while (cursor.moveToNext()) {
            String ma = cursor.getString(0);
            String title = cursor.getString(1);
            String content = cursor.getString(2);
            String cateID = cursor.getString(3);
            byte[] image = cursor.getBlob(4);
            String publishedDate = cursor.getString(5);
            String authorID = cursor.getString(6);

            Cursor categoryCursor = database.rawQuery("SELECT Name FROM Categories WHERE CategoryID = ?", new String[]{cateID});
            String categoryName = ""; // Tên của danh mục

            if (categoryCursor.moveToFirst()) {
                categoryName = categoryCursor.getString(0); // Lấy tên của danh mục từ kết quả truy vấn
            }

            categoryCursor.close();

            BaiBao u = new BaiBao(ma, title, content, categoryName, image, publishedDate, authorID);
            mylist.add(u);
        }

        // Đóng con trỏ sau khi sử dụng
        cursor.close();

        // Khởi tạo adapter và gán cho ListView
        myAdapter = new MyArrayAdapter(getActivity(), R.layout.layout_items, mylist);
        lv.setAdapter(myAdapter);

        // Xử lý sự kiện khi một mục được chọn
        // Trong Activity hiển thị danh sách các bài báo
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Lấy bài báo được chọn từ danh sách
                BaiBao selectedArticle = mylist.get(position);
                // Tạo Intent để mở News1Activity
                Intent intent = new Intent(getActivity(), News1Activity.class);
                // Đưa dữ liệu tiêu đề và nội dung của bài báo vào Intent
                intent.putExtra("title", selectedArticle.getTitle());
                intent.putExtra("content", selectedArticle.getContent());
                intent.putExtra("image", selectedArticle.getHinh());
                intent.putExtra("time", selectedArticle.getCreatedDate());
                // Khởi chạy News1Activity với dữ liệu đã đính kèm
                startActivity(intent);
            }
        });


        setHasOptionsMenu(true); // Bật tính năng menu cho Fragment
        return v;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu); // Inflate menu_search layout
        MenuItem searchItem = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                myAdapter.getFilter().filter(newText); // Lọc dữ liệu khi người dùng nhập vào thanh tìm kiếm
                return true;
            }
        });
    }

}
