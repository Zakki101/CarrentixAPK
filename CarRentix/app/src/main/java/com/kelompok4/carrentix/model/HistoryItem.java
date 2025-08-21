package com.kelompok4.carrentix.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class HistoryItem implements Serializable {
    private int payId;
    private int rentedId;
    private int userId;
    private int carId;
    private String name;
    private String end_date;
    private String status;
    private int price;
    @SerializedName("picture_url")
    private String picture;

    public String getImage() {
        return picture;
    }

    public void setImage(String image) {
        image = this.picture;
    }

    public int getPayId() {
        return payId;
    }

    public void setPayId(int payId) {
        this.payId = payId;
    }

    public int getRentedId() {
        return rentedId;
    }

    public void setRentedId(int rentedId) {
        this.rentedId = rentedId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
