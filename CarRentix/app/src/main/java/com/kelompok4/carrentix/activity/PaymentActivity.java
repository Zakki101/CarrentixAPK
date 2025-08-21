package com.kelompok4.carrentix.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.kelompok4.carrentix.R;
import com.kelompok4.carrentix.api.FileUtils;
import com.kelompok4.carrentix.model.Payment;
import com.kelompok4.carrentix.retrofit.ApiService;
import com.kelompok4.carrentix.retrofit.RetrofitClient;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.*;

import java.io.File;
import java.io.IOException;

public class PaymentActivity extends AppCompatActivity {

    private Spinner paymentMethodSpinner;
    private Button uploadIdButton, payNowButton;
    private TextView rentFeeText, insuranceFeeText, subtotalText, dayText;
    private Uri idPhotoUri;
    private int rentedCarId;
    private float rentPrice;
    private float insuranceFee = 0;
    private float subtotal;

    private static final int PICK_IMAGE_REQUEST = 1001;
    private static final String[] METHODS = {"DANA", "GOPAY", "OVO", "E-BANKING", "CARD"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_rent);

        paymentMethodSpinner = findViewById(R.id.paymentMethodSpinner);
        uploadIdButton = findViewById(R.id.uploadIdButton);
        payNowButton = findViewById(R.id.paynow);
        rentFeeText = findViewById(R.id.rentFeeText);
        insuranceFeeText = findViewById(R.id.insuranceFeeText);
        subtotalText = findViewById(R.id.subtotalText);
        dayText = findViewById(R.id.rent_days);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, METHODS);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentMethodSpinner.setAdapter(spinnerAdapter);

        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        long rentedDays = prefs.getLong("rental_duration_days", 1);
        rentedCarId = prefs.getInt("rentedcar_id", -1);
        String insuranceOption = prefs.getString("insurance_option", "NO INSURANCE");

        String strPrice = prefs.getString("car_price", "0.0");
        rentPrice = Float.parseFloat(strPrice) * rentedDays;
        rentFeeText.setText("Rp" + rentPrice);

        if (insuranceOption.equals("WITH INSURANCE")) {
            insuranceFee = 100000;
        }
        else if (insuranceOption.equals("NO INSURANCE")) {
            insuranceFee = 0;
        }

        long days = prefs.getLong("rental_duration_days", 1);
        dayText.setText("" + days);

        insuranceFeeText.setText("Rp" + insuranceFee);

        subtotal = rentPrice + insuranceFee;
        subtotalText.setText("Rp" + subtotal);

        uploadIdButton.setOnClickListener(v -> pickImage());

        payNowButton.setOnClickListener(v -> submitPayment());
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            idPhotoUri = data.getData();
            Toast.makeText(this, "ID image selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void submitPayment() {
        if (idPhotoUri == null) {
            Toast.makeText(this, "Please upload your ID photo.", Toast.LENGTH_SHORT).show();
            return;
        }

        File file = new File(FileUtils.getPath(this, idPhotoUri));
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part idImagePart = MultipartBody.Part.createFormData("id_photo", file.getName(), reqFile);

        RequestBody method = RequestBody.create(MediaType.parse("text/plain"),
                paymentMethodSpinner.getSelectedItem().toString());
        RequestBody rentedId = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(rentedCarId));
        RequestBody price = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(rentPrice));
        RequestBody insurance = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(insuranceFee));
        RequestBody subTotal = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(subtotal));


        ApiService apiService = RetrofitClient.getApiService();
        apiService.submitPayment(method, rentedId, price, insurance, idImagePart, subTotal).enqueue(new Callback<Payment>() {
            @Override
            public void onResponse(Call<Payment> call, Response<Payment> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(PaymentActivity.this, "Payment successful!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(PaymentActivity.this, RentHistory.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(PaymentActivity.this, "Submission failed: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Payment> call, Throwable t) {
                Toast.makeText(PaymentActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PaymentActivity.this, RentHistory.class);
                startActivity(intent);
            }
        });
    }
}
