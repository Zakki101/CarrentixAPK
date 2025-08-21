package com.kelompok4.carrentix.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kelompok4.carrentix.R;
import com.kelompok4.carrentix.model.Car;
import com.kelompok4.carrentix.model.HistoryItem;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private List<HistoryItem> historyList;
    private Context context;

    public HistoryAdapter(Context context, List<HistoryItem> historyList) {
        this.context = context;
        this.historyList = historyList;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_past_rent, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        HistoryItem item = historyList.get(position);
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

        holder.name.setText(item.getName());
        holder.endDate.setText("" + formattedDate);
        holder.status.setText("" + item.getStatus());
        holder.price.setText("Rp" + String.format("%,d", item.getPrice()).replace(',', '.'));
        Glide.with(context)
                .load(item.getImage())
                .placeholder(R.drawable.avanza)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        TextView name, endDate, status, price;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.title);
            endDate = itemView.findViewById(R.id.endDate);
            status = itemView.findViewById(R.id.status);
            price = itemView.findViewById(R.id.price);
            image = itemView.findViewById(R.id.image);
        }
    }
}
