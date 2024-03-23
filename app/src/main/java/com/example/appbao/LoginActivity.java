package com.example.appbao;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText userMail, userPassword;
    Button btnLogin;
    ProgressBar loginProgress;
    TextView createText;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userMail = findViewById(R.id.Login_mail);
        userPassword = findViewById(R.id.Login_password);
        loginProgress = findViewById(R.id.progressBar);
        btnLogin = findViewById(R.id.loginbtn);
        createText = findViewById(R.id.createtext);

        mAuth = FirebaseAuth.getInstance();

        createText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        String email = userMail.getText().toString();
        String password = userPassword.getText().toString();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            showMessage("Vui lòng không bỏ trống");
            return;
        }

        loginProgress.setVisibility(View.VISIBLE);
        btnLogin.setVisibility(View.INVISIBLE);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        loginProgress.setVisibility(View.INVISIBLE);
                        btnLogin.setVisibility(View.VISIBLE);

                        if (task.isSuccessful()) {


                                SessionManager sessionManager = new SessionManager(getApplicationContext());
                                sessionManager.saveLoginDetails(email, password);

                                // Chuyển đến activity Home
                                updateUI();
                            

                        } else {
                            showMessage(task.getException().getMessage());
                        }
                    }
                });
    }

    private void updateUI() {
        Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(homeIntent);
        finish();
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}
