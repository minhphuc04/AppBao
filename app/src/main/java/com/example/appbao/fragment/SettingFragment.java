package com.example.appbao.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appbao.AdminActivity;
import com.example.appbao.FingerLoginActivity;
import com.example.appbao.LanguageActivity;
import com.example.appbao.R;

public class SettingFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
ImageView clickLoginFin;
    private String mParam1;
    private String mParam2;
    ImageView btnadmin,btn_Language;
    TextView txtLanguage;

    SwitchCompat switchMode;
    boolean initialNightMode; // Biến để lưu trữ trạng thái ban đầu
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public SettingFragment() {
        // Required empty public constructor
    }

    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_setting, container, false);
        btnadmin = view.findViewById(R.id.btnadmin);
        clickLoginFin = view.findViewById(R.id.clickLoginFin);
        btn_Language = view.findViewById(R.id.btn_Language);
        btn_Language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent();
                myintent.setClass(getActivity(), LanguageActivity.class);
                startActivity(myintent);
            }
        });
        clickLoginFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent();
                myintent.setClass(getActivity(), FingerLoginActivity.class);
                startActivity(myintent);
            }
        });
        btnadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AdminActivity.class);
                startActivity(intent);
            }
        });

        // Ánh xạ SwitchCompat từ layout
        switchMode = view.findViewById(R.id.switchMode);

        // Khởi tạo SharedPreferences
        sharedPreferences = getActivity().getSharedPreferences("MODE", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // Đọc trạng thái đêm từ SharedPreferences, mặc định là false
        initialNightMode = sharedPreferences.getBoolean("nightMode", false); // Lưu trạng thái ban đầu
        switchMode.setChecked(initialNightMode);

        // Xử lý sự kiện khi SwitchCompat thay đổi trạng thái
        switchMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Đảo ngược trạng thái
                initialNightMode = !initialNightMode;
                if (initialNightMode) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                // Lưu trạng thái đêm vào SharedPreferences
                editor.putBoolean("nightMode", initialNightMode);
                editor.apply();
            }
        });

        return view;
    }



}

