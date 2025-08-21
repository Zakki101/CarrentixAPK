package com.kelompok4.carrentix.model;

public class User {
    String id;
    String username;
    String email;
    String address;
    String phone;
    String password;

    public User(String id, String username, String email, String phone, String address, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.password = password;
    }

    public User(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
