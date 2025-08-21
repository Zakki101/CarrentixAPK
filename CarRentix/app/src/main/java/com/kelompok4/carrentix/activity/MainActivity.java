package com.kelompok4.carrentix.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kelompok4.carrentix.R;
import com.kelompok4.carrentix.adapter.CarAdapter;
import com.kelompok4.carrentix.model.Car;
import com.kelompok4.carrentix.retrofit.ApiService;
import com.kelompok4.carrentix.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CarAdapter carAdapter;
    private ImageButton btnHistory, btnProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnHistory = findViewById(R.id.history);
        btnProfile = findViewById(R.id.user);

        btnHistory.setOnClickListener(v -> {
            Intent hist = new Intent(MainActivity.this, RentHistory.class);
            startActivity(hist);
        });

        btnProfile.setOnClickListener(v -> {
            Intent prof = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(prof);
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        int spacing = getResources().getDimensionPixelSize(R.dimen.recycler_item_spacing);
        int sidePadding = getResources().getDimensionPixelSize(R.dimen.recycler_side_padding);

        recyclerView.setPadding(sidePadding, 0, sidePadding, 0);
        recyclerView.setClipToPadding(false);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = parent.getChildAdapterPosition(view);
                int column = position % 2;

                outRect.top = spacing;

                if (column == 0) {
                    outRect.right = spacing / 2;
                } else {
                    outRect.left = spacing / 2;
                }
            }
        });

        fetchCarsFromApi();
    }

    private void fetchCarsFromApi() {
        ApiService apiService = RetrofitClient.getApiService();
        Call<List<Car>> call = apiService.getCars();

        call.enqueue(new Callback<List<Car>>() {
            @Override
            public void onResponse(Call<List<Car>> call, Response<List<Car>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Car> carList = response.body();
                    carAdapter = new CarAdapter(MainActivity.this, carList);
                    recyclerView.setAdapter(carAdapter);

                    for (Car car : carList) {
                        Log.d("CarDebug", "Car: " + car.getName() + ", Capacity: " + car.getPassengerCapacity());
                    }

                    carAdapter.setOnItemClickListener(car -> {
                        Intent intent = new Intent(MainActivity.this, CarDetailsActivity.class);
                        intent.putExtra("car", car);
                        startActivity(intent);
                    });

                } else {
                    Toast.makeText(MainActivity.this, "Failed to load cars", Toast.LENGTH_SHORT).show();
                    Log.e("MainActivity", "Response failed: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Car>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("MainActivity", "API call failed", t);
            }
        });
    }
}
