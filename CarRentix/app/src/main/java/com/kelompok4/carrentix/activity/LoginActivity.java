package com.kelompok4.carrentix.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.kelompok4.carrentix.R;
import com.kelompok4.carrentix.login.LoginRequest;
import com.kelompok4.carrentix.login.LoginResponse;
import com.kelompok4.carrentix.retrofit.ApiService;
import com.kelompok4.carrentix.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin;
    private Button passVisibilityButton;
    private Button btnSignUp;
    private boolean isPasswordVisible = false;
    private CheckBox rememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.username);
        etPassword = findViewById(R.id.password);
        btnLogin   = findViewById(R.id.login);
        btnSignUp = findViewById(R.id.signup);
        rememberMe = findViewById(R.id.remember);
        passVisibilityButton = findViewById(R.id.passvisibility);

        btnLogin.setOnClickListener(v -> attemptLogin());

        btnSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });


        passVisibilityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPasswordVisible) {
                    // Hide password
                    etPassword.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    // Show password
                    etPassword.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }

                // Re-apply font
                etPassword.setTypeface(ResourcesCompat.getFont(LoginActivity.this, R.font.monsterrat));

                // Keep cursor at end
                etPassword.setSelection(etPassword.getText().length());

                isPasswordVisible = !isPasswordVisible;
            }
        });
    }

    private void attemptLogin() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
            return;
        }

        LoginRequest loginRequest = new LoginRequest(username, password);
        ApiService apiService = RetrofitClient.getApiService();

        Call<LoginResponse> call = apiService.login(loginRequest);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse loginResponse = response.body();
                    if (loginResponse.isSuccess()) {
                        Toast.makeText(LoginActivity.this, "Welcome " + loginResponse.getUser().getUsername(), Toast.LENGTH_SHORT).show();

                        int userId = Integer.parseInt(loginResponse.getUser().getId());
                        SharedPreferences sharedPref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putInt("user_id", userId);
                        editor.apply();


                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Login failed: Invalid response", Toast.LENGTH_SHORT).show();
                    Log.e("LOGIN", "Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("LOGIN", "Network failure", t);
            }
        });
    }
}