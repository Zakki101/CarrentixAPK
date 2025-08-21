package com.kelompok4.carrentix.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.kelompok4.carrentix.R;
import com.kelompok4.carrentix.model.User;
import com.kelompok4.carrentix.retrofit.ApiService;
import com.kelompok4.carrentix.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    EditText email, phone, username, newpass, confpass;
    Button signUpButton, passVisibility, conPassVisibility, loginButton;
    boolean isPasswordVisible = false, isConPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        username = findViewById(R.id.username);
        newpass = findViewById(R.id.newpass);
        confpass = findViewById(R.id.confpass);
        signUpButton = findViewById(R.id.sign_up);
        passVisibility = findViewById(R.id.passvisibility);
        conPassVisibility = findViewById(R.id.conpassvisibility);
        loginButton = findViewById(R.id.login);

        passVisibility.setOnClickListener(v -> {
            if (isPasswordVisible) {
                newpass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            } else {
                newpass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            }

            newpass.setTypeface(ResourcesCompat.getFont(SignUpActivity.this, R.font.monsterrat));
            newpass.setSelection(newpass.getText().length());
            isPasswordVisible = !isPasswordVisible;
        });

        conPassVisibility.setOnClickListener(v -> {
            if (isConPasswordVisible) {
                confpass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            } else {
                confpass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            }

            confpass.setTypeface(ResourcesCompat.getFont(SignUpActivity.this, R.font.monsterrat));
            confpass.setSelection(confpass.getText().length());
            isConPasswordVisible = !isConPasswordVisible;
        });

        loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        signUpButton.setOnClickListener(v -> {
            String emailStr = email.getText().toString().trim();
            String phoneStr = phone.getText().toString().trim();
            String usernameStr = username.getText().toString().trim();
            String passStr = newpass.getText().toString().trim();
            String conPassStr = confpass.getText().toString().trim();

            if (emailStr.isEmpty() || phoneStr.isEmpty() || usernameStr.isEmpty() || passStr.isEmpty() || conPassStr.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!passStr.equals(conPassStr)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            if (passStr.length() < 8) {
                Toast.makeText(this, "Password must be at least 8 characters", Toast.LENGTH_SHORT).show();
                return;
            }

            User user = new User();
            user.setEmail(emailStr);
            user.setPhone(phoneStr);
            user.setUsername(usernameStr);
            user.setPassword(passStr);

            ApiService apiService = RetrofitClient.getApiService();
            Call<Void> call = apiService.addUser(user);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(SignUpActivity.this, "Sign up successful!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        Toast.makeText(SignUpActivity.this, "Sign up failed", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(SignUpActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });
    }
}
