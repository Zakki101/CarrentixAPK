package com.kelompok4.carrentix.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.kelompok4.carrentix.R;
import com.kelompok4.carrentix.model.Car;
import com.kelompok4.carrentix.retrofit.ApiService;
import com.kelompok4.carrentix.retrofit.RetrofitClient;
import com.kelompok4.carrentix.model.RentedCar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RentCar extends AppCompatActivity {

    private TextView carTitle, carAddress;
    private ImageView carImage;
    private EditText startDateInput, endDateInput;
    private Spinner insuranceSpinner;
    private EditText renterNameInput, phoneInput, addressInput;
    private Button rentNowButton;

    private int userId, carId;
    private String carImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_car);

        carTitle = findViewById(R.id.cartitle);
        carAddress = findViewById(R.id.carAddress);
        carImage = findViewById(R.id.carImage);
        startDateInput = findViewById(R.id.start_date_input);
        endDateInput = findViewById(R.id.end_date_input);
        insuranceSpinner = findViewById(R.id.insurance_spinner);
        renterNameInput = findViewById(R.id.renter_name_input);
        phoneInput = findViewById(R.id.phone_input);
        addressInput = findViewById(R.id.address_input);
        rentNowButton = findViewById(R.id.rentnow);

        SharedPreferences sharedPref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        userId = sharedPref.getInt("user_id", -1);
        carId = sharedPref.getInt("car_id", -1);
        Log.d("CarDebug", "Car: " + userId);
        loadCarData(carId);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.insurance_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        insuranceSpinner.setAdapter(adapter);

        setupDateTimePicker(startDateInput);
        setupDateTimePicker(endDateInput);

        rentNowButton.setOnClickListener(v -> submitRentRequest());
    }

    private void loadCarData(int carId) {
        ApiService apiService = RetrofitClient.getApiService();
        Call<Car> call = apiService.getCarById(carId);

        call.enqueue(new Callback<Car>() {
            @Override
            public void onResponse(Call<Car> call, Response<Car> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Car car = response.body();
                    carTitle.setText(car.getName());
                    carAddress.setText(car.getAddress());
                    Glide.with(RentCar.this)
                            .load(car.getPictures())
                            .placeholder(R.drawable.avanza)
                            .into(carImage);
                } else {
                    Toast.makeText(RentCar.this, "Failed to load car info", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Car> call, Throwable t) {
                Toast.makeText(RentCar.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupDateTimePicker(EditText targetEditText) {
        targetEditText.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(RentCar.this,
                    (view, year, month, dayOfMonth) -> {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        TimePickerDialog timePickerDialog = new TimePickerDialog(RentCar.this,
                                (view1, hourOfDay, minute) -> {
                                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                    calendar.set(Calendar.MINUTE, minute);

                                    String formatted = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(calendar.getTime());
                                    targetEditText.setText(formatted);
                                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
                        timePickerDialog.show();

                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });
    }

    private void submitRentRequest() {
        String start = startDateInput.getText().toString().trim();
        String end = endDateInput.getText().toString().trim();
        String insurance = insuranceSpinner.getSelectedItem().toString();
        String renterName = renterNameInput.getText().toString().trim();
        String phone = phoneInput.getText().toString().trim();
        String address = addressInput.getText().toString().trim();

        if (start.isEmpty() || end.isEmpty() || renterName.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            Toast.makeText(this, "Please fill all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        long durationDays = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            long startMillis = sdf.parse(start).getTime();
            long endMillis = sdf.parse(end).getTime();
            long diffMillis = endMillis - startMillis;

            durationDays = diffMillis / (1000 * 60 * 60 * 24);

            Log.d("RENT_DEBUG", "Duration in days: " + durationDays);
        } catch (Exception e) {
            Toast.makeText(this, "Invalid date format", Toast.LENGTH_SHORT).show();
            return;
        }


        RentedCar rentedCar = new RentedCar();
        rentedCar.setCar_id(carId);
        rentedCar.setUser_id(userId);
        rentedCar.setStart_datetime(start);
        rentedCar.setEnd_datetime(end);
        rentedCar.setWith_insurance(insurance);
        rentedCar.setRenter_name(renterName);
        rentedCar.setPhone(phone);
        rentedCar.setAddress(address);
        rentedCar.setStatus("within rental period");

        ApiService apiService = RetrofitClient.getApiService();
        Call<RentedCar> call = apiService.createRentRequest(rentedCar);
        long finaldurationDays = durationDays;
        call.enqueue(new Callback<RentedCar>() {
            @Override
            public void onResponse(Call<RentedCar> call, Response<RentedCar> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RentCar.this, "Rent request successful!", Toast.LENGTH_SHORT).show();

                    RentedCar responseCar = response.body();
                    int rentId = responseCar.getId();

                    SharedPreferences sharedPref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();

                    editor.putInt("rentedcar_id", rentId);
                    editor.putString("insurance_option", insurance);
                    editor.putLong("rental_duration_days", finaldurationDays);
                    editor.apply();


                    Log.d("RENT_DEBUG", "Rent ID stored: " + rentId);

                    Intent intent = new Intent(RentCar.this, WaitRequest.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RentCar.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RentedCar> call, Throwable t) {
                Toast.makeText(RentCar.this, "Failure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
