package com.kelompok4.carrentix.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kelompok4.carrentix.R;
import com.kelompok4.carrentix.adapter.HistoryAdapter;
import com.kelompok4.carrentix.model.HistoryItem;
import com.kelompok4.carrentix.retrofit.ApiService;
import com.kelompok4.carrentix.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RentHistory extends AppCompatActivity {

    private RecyclerView pastRecyclerView;
    private HistoryAdapter historyAdapter;
    private List<HistoryItem> pastRentList = new ArrayList<>();
    private FrameLayout currentRentCard;

    private int currentUserId;

    private ImageButton homeButton, histButton, userButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_history);

        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        currentUserId = prefs.getInt("user_id", -1);

        pastRecyclerView = findViewById(R.id.past_rents_recycler);
        pastRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        historyAdapter = new HistoryAdapter(this, pastRentList);
        pastRecyclerView.setAdapter(historyAdapter);

        currentRentCard = findViewById(R.id.current_rent_card);

        setupBottomBar();
        fetchRentalHistory();
    }

    private void fetchRentalHistory() {
        ApiService apiService = RetrofitClient.getApiService();
        Call<List<HistoryItem>> call = apiService.getHistoryView();

        call.enqueue(new Callback<List<HistoryItem>>() {
            @Override
            public void onResponse(Call<List<HistoryItem>> call, Response<List<HistoryItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<HistoryItem> allHistory = response.body();
                    List<HistoryItem> userHistory = new ArrayList<>();

                    for (HistoryItem item : allHistory) {
                        if (item.getUserId() == currentUserId) {
                            userHistory.add(item);
                        }
                    }

                    if (!userHistory.isEmpty()) {
                        Collections.reverse(userHistory);

                        HistoryItem latest = userHistory.get(0);
                        loadCurrentRentCard(latest);

                        userHistory.remove(0);
                        pastRentList.clear();
                        pastRentList.addAll(userHistory);
                        historyAdapter.notifyDataSetChanged();
                    }

                } else {
                    Toast.makeText(RentHistory.this, "Failed to load rental history", Toast.LENGTH_SHORT).show();
                    Log.e("RentHistory", "Response not successful");
                }
            }

            @Override
            public void onFailure(Call<List<HistoryItem>> call, Throwable t) {
                Toast.makeText(RentHistory.this, "Network error", Toast.LENGTH_SHORT).show();
                Log.e("RentHistory", "API Error: " + t.getMessage());
            }
        });
    }

    private void loadCurrentRentCard(HistoryItem item) {
        View cardView = LayoutInflater.from(this).inflate(R.layout.item_current_rent, currentRentCard, false);

        TextView title = cardView.findViewById(R.id.title);
        TextView endDate = cardView.findViewById(R.id.endDate);
        TextView status = cardView.findViewById(R.id.status);
        TextView price = cardView.findViewById(R.id.price);
        ImageView image = cardView.findViewById(R.id.image);

        String isoDate = item.getEnd_date();
        String formattedDate = "";

        try {
            SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            isoFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = isoFormat.parse(isoDate);

            SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMMM yyyy");
            outputFormat.setTimeZone(TimeZone.getDefault());
            formattedDate = outputFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            formattedDate = isoDate;
        }

        title.setText(item.getName());
        endDate.setText("" + formattedDate);
        status.setText("" + item.getStatus());
        price.setText("Rp" + String.format("%,d", item.getPrice()).replace(',', '.'));
        Glide.with(this)
                .load(item.getImage())
                .placeholder(R.drawable.avanza)
                .into(image);

        currentRentCard.removeAllViews();
        currentRentCard.addView(cardView);
    }

    private void setupBottomBar() {
        homeButton = findViewById(R.id.homebut);
        histButton = findViewById(R.id.histbut);
        userButton = findViewById(R.id.usbut);

        homeButton.setOnClickListener(v -> startActivity(new Intent(RentHistory.this, MainActivity.class)));
        histButton.setOnClickListener(v -> Toast.makeText(this, "Already here", Toast.LENGTH_SHORT).show());
        userButton.setOnClickListener(v -> startActivity(new Intent(RentHistory.this, ProfileActivity.class)));
    }
}
