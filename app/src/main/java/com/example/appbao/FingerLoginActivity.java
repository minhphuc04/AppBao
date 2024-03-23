package com.example.appbao;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.Executor;

public class FingerLoginActivity extends AppCompatActivity {
    Button login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger_login);

        TextView msg_txt = findViewById(R.id.txt_msg);
        login_btn = findViewById(R.id.login_btn);

        BiometricManager biometricManager = BiometricManager.from(this);
        switch (biometricManager.canAuthenticate()) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                msg_txt.setText("You can use the fingerprint to Login");
                msg_txt.setTextColor(Color.parseColor("#Fafafa"));
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                msg_txt.setText("The device doesn't have a fingerprint sensor");
                login_btn.setVisibility(View.GONE);
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                msg_txt.setText("The biometric sensors are currently unavailable");
                login_btn.setVisibility(View.GONE);
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                msg_txt.setText("Your device doesn't have any fingerprint saved, please check your security settings");
                login_btn.setVisibility(View.GONE);
                break;
        }

        Executor executor = ContextCompat.getMainExecutor(this);
        BiometricPrompt biometricPrompt = new BiometricPrompt(FingerLoginActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }



            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(FingerLoginActivity.this, "Login Success!", Toast.LENGTH_SHORT).show();

                // Chuyển hướng đến MainActivity khi xác thực thành công
                Intent intent = new Intent(FingerLoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Kết thúc FingerLoginActivity để ngăn người dùng quay lại màn hình xác thực vân tay
            }




            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }

        });


        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Login")
                .setDescription("Use your fingerprint to login to your app")
                .setNegativeButtonText("Cancel")
                .build();

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                biometricPrompt.authenticate(promptInfo);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                // Xác thực vân tay thành công, tiếp tục với MainActivity
                Intent intent = new Intent(FingerLoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Kết thúc MainActivity để ngăn người dùng quay lại
            } else {
                // Xác thực vân tay không thành công, quay lại màn hình đăng nhập hoặc thoát ứng dụng
                finish();
            }
        }
    }



}
