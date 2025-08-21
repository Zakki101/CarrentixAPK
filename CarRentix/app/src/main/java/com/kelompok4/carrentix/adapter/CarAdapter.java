package com.kelompok4.carrentix.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kelompok4.carrentix.R;
import com.kelompok4.carrentix.model.Car;

import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {

    private Context context;
    private List<Car> carList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Car car);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public CarAdapter(Context context, List<Car> carList) {
        this.context = context;
        this.carList = carList;
    }

    @Override
    public CarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_car, parent, false);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CarViewHolder holder, int position) {
        Car car = carList.get(position);

        holder.name.setText(car.getName());
        holder.price.setText("Rp " + car.getPrice() + "/day");
        holder.trim.setText(car.getTrim());
        holder.transmission.setText(car.getTransmission());
        holder.address.setText(car.getAddress());
        holder.availability.setText("Available");
        holder.insurance.setText("RENTER'S INSURANCE AVAILABLE");

        Glide.with(context)
                .load(car.getPictures())
                .placeholder(R.drawable.avanza)
                .into(holder.image);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(car);
            }
        });
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    public class CarViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, price, trim, transmission, insurance, address, availability;

        public CarViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.car_image);
            name = itemView.findViewById(R.id.car_name);
            price = itemView.findViewById(R.id.car_price);
            trim = itemView.findViewById(R.id.trim);
            transmission = itemView.findViewById(R.id.trans);
            insurance = itemView.findViewById(R.id.insurance);
            address = itemView.findViewById(R.id.address_car);
            availability = itemView.findViewById(R.id.availability);
        }
    }
}
