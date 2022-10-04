package com.example.foodintaketracking.retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitInterface {

    // https://api.edamam.com/api/nutrition-data?app_id=d4cfc906&app_key=c6ede7a97e1b6c9fba3752e8e7e4e344&nutrition-type=cooking&ingr=a%20banana
    @GET("v1/nutrition")
    Call<FoodNutrition> foodSearch(@Header("X-Api-Key") String App_key,
                                   @Query("query") String keyword);


}

