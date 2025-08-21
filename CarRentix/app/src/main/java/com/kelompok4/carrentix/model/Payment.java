package com.kelompok4.carrentix.model;

import java.io.Serializable;

public class Payment implements Serializable {

    private int id;
    private String payment_method;
    private int rentedcar_id;
    private float price;
    private float insurance_fee;
    private String id_photo;
    private float sub_total;


    public Payment() {}

    public Payment(int id, String payment_method, int rentedcar_id, float price, float insurance_fee, String id_photo, float sub_total) {
        this.id = id;
        this.payment_method = payment_method;
        this.rentedcar_id = rentedcar_id;
        this.price = price;
        this.insurance_fee = insurance_fee;
        this.id_photo = id_photo;
        this.sub_total = sub_total;
    }

    public int getId() {
        return id;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public int getRentedcar_id() {
        return rentedcar_id;
    }

    public float getPrice() {
        return price;
    }

    public float getInsurance_fee() {
        return insurance_fee;
    }

    public String getId_photo() {
        return id_photo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public void setRentedcar_id(int rentedcar_id) {
        this.rentedcar_id = rentedcar_id;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setInsurance_fee(int insurance_fee) {
        this.insurance_fee = insurance_fee;
    }

    public void setId_photo(String id_photo) {
        this.id_photo = id_photo;
    }

    public int sub_total() {
        return id;
    }

    public float getSub_total() {
        return sub_total;
    }

}
