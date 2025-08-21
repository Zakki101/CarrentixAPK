package com.kelompok4.carrentix.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.kelompok4.carrentix.R;
import com.kelompok4.carrentix.model.Car;

public class CarDetailsActivity extends AppCompatActivity {

    private TextView carName, carLocation, availability, priceText, description;
    private TextView specColour, specTrim, specCapacity, specTransmission, specEngine, specFuel;
    private ImageView carImage;
    private Button backButton, rentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);

        Car car = (Car) getIntent().getSerializableExtra("car");
        if (car == null) {
            finish();
            return;
        }

        carName = findViewById(R.id.carName);
        carLocation = findViewById(R.id.setmap);
        availability = findViewById(R.id.availabilityText);
        priceText = findViewById(R.id.priceText);
        description = findViewById(R.id.descriptionText);
        carImage = findViewById(R.id.carImage);
        backButton = findViewById(R.id.back);
        rentButton = findViewById(R.id.rentbtn);

        specColour = findViewById(R.id.specColour);
        specTrim = findViewById(R.id.specTrim);
        specCapacity = findViewById(R.id.specCapacity);
        specTransmission = findViewById(R.id.specTransmission);
        specEngine = findViewById(R.id.specEngine);
        specFuel = findViewById(R.id.specFuel);

        carName.setText(car.getName() + " " + car.getYear());
        carLocation.setText(car.getAddress());
        availability.setText("Currently Available");
        priceText.setText("Rp" + car.getPrice() + "/day");
        description.setText(car.getDescription());

        specColour.setText("Colour: " + car.getColour());
        specTrim.setText("Type: " + car.getTrim());
        specCapacity.setText("Capacity: " + car.getPassengerCapacity() + " People");
        specTransmission.setText("Transmission: " + car.getTransmission());
        specEngine.setText("Engine: " + car.getEngine());
        specFuel.setText("Fuel Type: " + car.getFuelType());

        SharedPreferences sharedPref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("car_id", car.getId());
        editor.putString("car_price", String.valueOf(car.getPrice()));
        editor.apply();

        Glide.with(this)
                .load(car.getPictures())
                .placeholder(R.drawable.avanza)
                .into(carImage);

        backButton.setOnClickListener(v -> onBackPressed());

        rentButton.setOnClickListener(v -> {
            Intent intent = new Intent(CarDetailsActivity.this, RentCar.class);
            startActivity(intent);
        });
    }
}
