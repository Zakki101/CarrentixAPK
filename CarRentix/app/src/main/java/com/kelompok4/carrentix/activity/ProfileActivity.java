package com.kelompok4.carrentix.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.kelompok4.carrentix.R;
import com.kelompok4.carrentix.model.User;
import com.kelompok4.carrentix.retrofit.ApiService;
import com.kelompok4.carrentix.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private EditText usernameEditText, emailEditText, addressEditText, phoneEditText, passwordEditText;
    private TextView emailTitle, nameTitle;
    private Button saveButton, logoutButton, deleteButton;
    private ImageView togglePassword;
    private boolean isPasswordVisible = false;
    private String Id;

    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        usernameEditText = findViewById(R.id.username);
        emailEditText = findViewById(R.id.email);
        addressEditText = findViewById(R.id.address);
        phoneEditText = findViewById(R.id.phone);
        passwordEditText = findViewById(R.id.password);
        nameTitle = findViewById(R.id.nameTitle);
        emailTitle = findViewById(R.id.emailTitle);

        togglePassword = findViewById(R.id.togglePasswordVisibility);
        saveButton = findViewById(R.id.save);
        logoutButton = findViewById(R.id.logout);
        deleteButton = findViewById(R.id.delete);

        SharedPreferences sharedPref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        userId = sharedPref.getInt("user_id", -1);

        if (userId != -1) {
            loadUserProfile();
            Id = Integer.toString(userId);
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            finish();
        }

        togglePassword.setOnClickListener(v -> {
            if (isPasswordVisible) {
                passwordEditText.setInputType(
                        InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
                );
                togglePassword.setImageResource(R.drawable.eye_off);
            } else {
                passwordEditText.setInputType(
                        InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                );
                togglePassword.setImageResource(R.drawable.eye);
            }

            passwordEditText.setTypeface(ResourcesCompat.getFont(ProfileActivity.this, R.font.alata));
            passwordEditText.setSelection(passwordEditText.getText().length());
            isPasswordVisible = !isPasswordVisible;
        });

        saveButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String address = addressEditText.getText().toString().trim();
            String phone = phoneEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (email.isEmpty() || phone.isEmpty() || username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            if (password.length() < 6) {
                Toast.makeText(this, "Password must be at least 8 characters", Toast.LENGTH_SHORT).show();
                return;
            }

            User updatedUser = new User(Id, username, email, phone, address, password);

            ApiService apiService = RetrofitClient.getApiService();
            Call<Void> call = apiService.updateUser(userId, updatedUser);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(ProfileActivity.this, "Changes saved!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ProfileActivity.this, "Failed to save changes", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(ProfileActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("ProfileActivity", "Update error", t);
                }
            });
        });

        logoutButton.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

        deleteButton.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Delete Account")
                    .setMessage("Are you sure you want to delete your account?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        ApiService apiService = RetrofitClient.getApiService();
                        Call<Void> call = apiService.deleteUser(userId);
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(ProfileActivity.this, "Account deleted", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(ProfileActivity.this, "Failed to delete account", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(ProfileActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                Log.e("ProfileActivity", "Delete error", t);
                            }
                        });
                    })
                    .setNegativeButton("No", null)
                    .show();
        });

        ImageButton home = findViewById(R.id.homebut);
        ImageButton history = findViewById(R.id.histbut);
        ImageButton profile = findViewById(R.id.usbut);

        home.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });

        history.setOnClickListener(v -> {
            startActivity(new Intent(this, RentHistory.class));
            finish();
        });

        profile.setOnClickListener(v -> {
            Toast.makeText(this, "Already in Profile", Toast.LENGTH_SHORT).show();
        });
    }

    private void loadUserProfile() {
        ApiService apiService = RetrofitClient.getApiService();
        Call<User> call = apiService.getUserById(userId);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User user = response.body();

                    Log.d("ProfileActivity", "API success: " + response.body().getUsername());

                    usernameEditText.setText(user.getUsername());
                    emailEditText.setText(user.getEmail());
                    addressEditText.setText(user.getAddress());
                    phoneEditText.setText(user.getPhone());
                    passwordEditText.setText(user.getPassword());
                    nameTitle.setText(user.getUsername());
                    emailTitle.setText(user.getEmail());;
                } else {
                    Toast.makeText(ProfileActivity.this, "Failed to load profile", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("ProfileActivity", "API Error", t);
            }
        });
    }
}
