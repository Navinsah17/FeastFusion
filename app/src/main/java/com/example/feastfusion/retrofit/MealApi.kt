package com.example.feastfusion.retrofit

import com.example.feastfusion.models.MealList
import retrofit2.Call
import retrofit2.http.GET

interface MealApi {
    @GET("random.php")
    fun getRandomMeal():Call<MealList>
}