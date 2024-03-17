package com.example.appbao.fragment;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbao.AdminActivity;
import com.example.appbao.FingerLoginActivity;
import com.example.appbao.LanguageActivity;
import com.example.appbao.NotificationChanel;
import com.example.appbao.R;

import java.util.Date;

public class SettingFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
ImageView clickLoginFin;
    private String mParam1;
    private String mParam2;
    ImageView btnadmin,btn_Language;
    TextView txtLanguage;
    private static final int NOTIFICATION_ID=1;

    SwitchCompat switchMode;
    boolean initialNightMode; // Biến để lưu trữ trạng thái ban đầu

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private SwitchCompat switchNoti;
    private static final String PREFS_NAME = "NotificationPrefs";
    private static final String SWITCH_STATE = "SwitchState";

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
        switchNoti = view.findViewById(R.id.switchNoti);
        btn_Language = view.findViewById(R.id.btn_Language);

        addEventNofi();

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

    private void addEventNofi() {
        switchNoti.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (getActivity() != null) {
                    SharedPreferences preferences = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean(SWITCH_STATE, isChecked);
                    editor.apply();

                    if (isChecked) {
                        Toast.makeText(getActivity(), "Đã bật thông báo", Toast.LENGTH_SHORT).show();
                        sendNotification();
                    } else {
                        Toast.makeText(getActivity(), "Đã tắt thông báo", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Đặt trạng thái ban đầu của công tắc dựa trên giá trị đã lưu
        if (getActivity() != null) {
            SharedPreferences preferences = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            boolean isChecked = preferences.getBoolean(SWITCH_STATE, false); // Mặc định là false nếu không tìm thấy giá trị
            switchNoti.setChecked(isChecked);
        }
    }

    private void sendNotification(){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_logonew);
        if (getActivity() != null) {
            Notification notification = new NotificationCompat.Builder(getActivity(), NotificationChanel.CHANNEL_ID)
                    .setContentTitle("Anh 7 một đời liêm khiết chống lại FIFA") //Tiêu đề thông báo
                    .setContentText("Anh Liêm") //Nội dung thông báo

                    .setSmallIcon(R.drawable.logonew)
                    .setColor(getResources().getColor(R.color.grey))
                    .setLargeIcon(bitmap)
                    .build();
            // Các thao tác tiếp theo với đối tượng notification
            NotificationManager notificationManager =(NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
            if(notificationManager!=null){
                notificationManager.notify(NOTIFICATION_ID,notification);
            }


        }
    }
    private int getNotificationId(){
        return (int) new Date().getTime();
    }



}

