package com.example.appbao;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
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

public class EntertainmentActivity extends Fragment {
    public static final String DATABASE_NAME = "SqlAppBao1";
    ArrayList<BaiBao> mylist;
    MyArrayAdapter myAdapter;
    ListView lv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_sports, container, false);
        setHasOptionsMenu(true); // Bật chức năng hiển thị menu của fragment
        lv = v.findViewById(R.id.lv);
        mylist = new ArrayList<>();

        // Khởi tạo Adapter và gán cho ListView
        myAdapter = new MyArrayAdapter(getActivity(), R.layout.layout_items, mylist);
        lv.setAdapter(myAdapter);

        // Xử lý sự kiện khi một mục được chọn
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BaiBao baiBao = mylist.get(position);
                // Kiểm tra nếu CategoryID là "2" (Thể thao)
                if (baiBao.getCategoryID().equals("3")) {
                    // Chuyển hướng tới Activity của Sports
                    Intent intent = new Intent(getActivity(), SportsActivity.class);
                    startActivity(intent);
                } else {
                    // Nếu không phải CategoryID là "2", có thể thực hiện hành động khác
                    // Ví dụ: mở Activity khác
                    Intent new1 = new Intent(getActivity(), News1Activity.class);
                    startActivity(new1);
                }
            }
        });

        // Lấy dữ liệu từ cơ sở dữ liệu chỉ khi CategoryID là 2
        loadDataForCategory2();

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

    // Phương thức để tải dữ liệu từ cơ sở dữ liệu chỉ khi CategoryID là 2
    private void loadDataForCategory2() {
        try {
            // Mở hoặc tạo cơ sở dữ liệu
            SQLiteDatabase database = getActivity().openOrCreateDatabase(DATABASE_NAME, getActivity().MODE_PRIVATE, null);

            // Thực hiện truy vấn để lấy dữ liệu từ bảng News với điều kiện CategoryID là 2
            Cursor cursor = database.rawQuery("SELECT * FROM News WHERE CategoryID = ?", new String[]{"3"});

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

            // Thông báo cho adapter rằng dữ liệu đã thay đổi
            myAdapter.notifyDataSetChanged();
        } catch (Exception ex) {
            Log.e("Error", ex.toString());
        }
    }
}
