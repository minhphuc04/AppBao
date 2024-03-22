package com.example.appbao;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class RegisterActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1001;
    private static final int PICK_IMAGE_REQUEST_CODE = 1002;

    private ImageView imgUserPhoto;
    private EditText userEmail, userPassword, userPassword2, userName;
    private ProgressBar loadingProgress;
    private Button registerBtn;
    private Uri pickedImageUri;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initializeViews();
        setClickListeners();
    }

    private void setClickListeners() {
        registerBtn.setOnClickListener(v -> registerUser());

        imgUserPhoto.setOnClickListener(v -> pickImageFromGallery());
    }

    private void initializeViews() {
        imgUserPhoto = findViewById(R.id.regUserPhoto);
        userEmail = findViewById(R.id.Remail);
        userPassword = findViewById(R.id.Rpassword);
        userPassword2 = findViewById(R.id.regisAgain);
        userName = findViewById(R.id.fullname);
        loadingProgress = findViewById(R.id.RprogressBar);
        registerBtn = findViewById(R.id.registerBtn);
        mAuth = FirebaseAuth.getInstance();
    }

    private void registerUser() {
        loadingProgress.setVisibility(View.VISIBLE);
        registerBtn.setEnabled(false);

        final String email = userEmail.getText().toString();
        final String password = userPassword.getText().toString();
        final String password2 = userPassword2.getText().toString();
        final String name = userName.getText().toString();

        if (email.isEmpty() || name.isEmpty() || password.isEmpty() || !password.equals(password2)) {
            showMessage("Please verify all fields");
            loadingProgress.setVisibility(View.INVISIBLE);
            registerBtn.setEnabled(true);
        } else {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (pickedImageUri != null) {
                                updateUserInfo(name, pickedImageUri, user);
                            } else {
                                updateUserInfo(name, null, user); // Update user info without image
                            }
                        } else {
                            showMessage("Failed to create account: " + task.getException().getMessage());
                            loadingProgress.setVisibility(View.INVISIBLE);
                            registerBtn.setEnabled(true);
                        }
                    });
        }
    }

    private void updateUserInfo(String name, Uri pickedImageUri, FirebaseUser currentUser) {
        UserProfileChangeRequest.Builder profileUpdatesBuilder = new UserProfileChangeRequest.Builder();
        profileUpdatesBuilder.setDisplayName(name);

        if (pickedImageUri != null) {
            StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("users_photos/" + currentUser.getUid() + "_profile.jpg");
            storageRef.putFile(pickedImageUri)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                                profileUpdatesBuilder.setPhotoUri(uri);
                                updateUserProfile(currentUser, profileUpdatesBuilder);
                            });
                        } else {
                            showMessage("Failed to upload image: " + task.getException().getMessage());
                            updateUserProfile(currentUser, profileUpdatesBuilder); // Continue updating user profile without image
                        }
                    });
        } else {
            // No image picked, update user profile directly
            updateUserProfile(currentUser, profileUpdatesBuilder);
        }
    }

    private void updateUserProfile(FirebaseUser currentUser, UserProfileChangeRequest.Builder profileUpdatesBuilder) {
        currentUser.updateProfile(profileUpdatesBuilder.build())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        showMessage("Register Complete");
                        redirectToLogin();
                    } else {
                        showMessage("Failed to update user profile: " + task.getException().getMessage());
                    }
                    loadingProgress.setVisibility(View.INVISIBLE);
                    registerBtn.setEnabled(true);
                });
    }


    private void pickImageFromGallery() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        } else {
            Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickImageFromGallery();
            } else {
                showMessage("Permission Denied!");
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            pickedImageUri = data.getData();
            imgUserPhoto.setImageURI(pickedImageUri);
        }
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void redirectToLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
