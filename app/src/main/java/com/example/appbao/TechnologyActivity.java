package com.example.appbao;

import android.content.Intent;
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

public class TechnologyActivity extends Fragment {
    ArrayList<BaiBao> mylist;
    MyArrayAdapter myAdapter;
    ListView lv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_home, container, false);
        lv = v.findViewById(R.id.lv);
        mylist = new ArrayList<>();

        mylist.add(new BaiBao(R.drawable.img1, "Kỷ lục gia marathon thế giới tử nạn ở tuổi 24","10 phút","Sport"));
        mylist.add(new BaiBao(R.drawable.img2, "Năm lãi chưa từng có của người trồng sầu riêng, cà phê","2 ngày","Technology"));
        mylist.add(new BaiBao(R.drawable.img3, "Chè kho Đại Đồng, đặc sản trong tiệc trà của Tổng bí thư","2 tiếng","Science"));
        mylist.add(new BaiBao(R.drawable.img4, "Những đồ xa xỉ ăn theo năm Rồng","7 ngày","Health"));
        mylist.add(new BaiBao(R.drawable.img5, "Man Utd thắng Aston Villa nhờ bàn muộn","1 tháng","Health"));

        myAdapter = new MyArrayAdapter(getActivity(), R.layout.layout_items, mylist);
        lv.setAdapter(myAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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
