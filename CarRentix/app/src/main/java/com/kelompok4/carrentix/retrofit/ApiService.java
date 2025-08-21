package com.kelompok4.carrentix.retrofit;

import com.kelompok4.carrentix.login.LoginRequest;
import com.kelompok4.carrentix.login.LoginResponse;
import com.kelompok4.carrentix.model.Car;
import com.kelompok4.carrentix.model.HistoryItem;
import com.kelompok4.carrentix.model.Payment;
import com.kelompok4.carrentix.model.RentedCar;
import com.kelompok4.carrentix.model.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {

    @POST("api/users")
    Call<Void> addUser(@Body User users);

    @GET("api/users/{id}")
    Call<User> getUser(@Path("id") int id);

    @PUT("api/users/{id}")
    Call<Void> updateUser(@Path("id") int id, @Body User user);

    @DELETE("api/users/{id}")
    Call<Void> deleteUser(@Path("id") int id);

    @POST("api/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @GET("api/cars")
    Call<List<Car>> getCars();

    @GET("api/cars/{id}")
    Call<Car> getCarById(@Path("id") int carId);

    @GET("api/users/{id}")
    Call<User> getUserById(@Path("id") int id);

    @POST("/rent")
    Call<Void> createRental(@Body RentedCar rentRequest);

    @GET("/rent/user/{userId}")
    Call<List<RentedCar>> getRentalsByUser(@Path("userId") int userId);

    @POST("/api/rentedcars")
    Call<RentedCar> createRentRequest(@Body RentedCar rentedCar);

    @Multipart
    @POST("api/payments")
    Call<Payment> submitPayment(
            @Part("payment_method") RequestBody method,
            @Part("rentedcar_id") RequestBody rentedCarId,
            @Part("price") RequestBody price,
            @Part("insurance_fee") RequestBody insuranceFee,
            @Part MultipartBody.Part id_photo,
            @Part("total") RequestBody subTotal
    );

    @GET("rentedcars/{id}/price")
    Call<Integer> getRentPrice(@Path("id") int rentedCarId);

    @GET("api/history")
    Call<List<HistoryItem>> getHistoryView();
}
