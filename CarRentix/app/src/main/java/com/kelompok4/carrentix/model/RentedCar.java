package com.kelompok4.carrentix.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RentedCar implements Serializable {

    private int id;
    private int car_id;
    private int user_id;
    @SerializedName("start_date")
    private String start_datetime;
    @SerializedName("end_date")
    private String end_datetime;
    @SerializedName("insurance_option")
    private String with_insurance;
    private String renter_name;
    @SerializedName("phone_number")
    private String phone;
    private String address;
    private String status;

    public RentedCar(int id, int car_id, int user_id, String start_datetime, String end_datetime,
                     String with_insurance, String renter_name, String phone, String address, String status) {
        this.id = id;
        this.car_id = car_id;
        this.user_id = user_id;
        this.start_datetime = start_datetime;
        this.end_datetime = end_datetime;
        this.with_insurance = with_insurance;
        this.renter_name = renter_name;
        this.phone = phone;
        this.address = address;
        this.status = status;
    }

    public RentedCar() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCar_id() {
        return car_id;
    }

    public void setCar_id(int car_id) {
        this.car_id = car_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getStart_datetime() {
        return start_datetime;
    }

    public void setStart_datetime(String start_datetime) {
        this.start_datetime = start_datetime;
    }

    public String getEnd_datetime() {
        return end_datetime;
    }

    public void setEnd_datetime(String end_datetime) {
        this.end_datetime = end_datetime;
    }

    public String getWith_insurance() {
        return with_insurance;
    }

    public void setWith_insurance(String with_insurance) {
        this.with_insurance = with_insurance;
    }

    public String getRenter_name() {
        return renter_name;
    }

    public void setRenter_name(String renter_name) {
        this.renter_name = renter_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
