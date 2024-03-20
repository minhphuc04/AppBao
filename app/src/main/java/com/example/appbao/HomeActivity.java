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
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BaiBao baiBao = mylist.get(position);

                // Kiểm tra CategoryID và chuyển hướng dữ liệu tới Activity tương ứng
                if (baiBao.getCategoryID().equals("1")) {
                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    // Gửi dữ liệu đi với Intent nếu cần
                    startActivity(intent);
                } else if (baiBao.getCategoryID().equals("2")) {
                    Intent intent = new Intent(getActivity(), SportsActivity.class);
                    // Gửi dữ liệu đi với Intent nếu cần
                    startActivity(intent);
                } else if (baiBao.getCategoryID().equals("3")) {
                    Intent intent = new Intent(getActivity(), EntertainmentActivity.class);
                    // Gửi dữ liệu đi với Intent nếu cần
                    startActivity(intent);
                }
                else if (baiBao.getCategoryID().equals("4")) {
                    Intent intent = new Intent(getActivity(), HealthyActivity.class);
                    // Gửi dữ liệu đi với Intent nếu cần
                    startActivity(intent);
                }
                else if (baiBao.getCategoryID().equals("5")) {
                    Intent intent = new Intent(getActivity(), TechnologyActivity.class);
                    // Gửi dữ liệu đi với Intent nếu cần
                    startActivity(intent);
                }
                if(position==0)
                {
                    Intent new1 = new Intent();
                    new1.setClass(getActivity(),News1Activity.class);
                    startActivity(new1);
                }
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
