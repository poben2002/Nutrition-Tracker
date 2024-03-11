package edu.uw.ischool.haeun.nutritiontracker

import retrofit2.Call
import retrofit2.http.GET
interface NutritionAPI {
    @GET("/api/nutrition")
    fun getNutritionData(): Call<NutritionResponse>
}

// Add endpoints to the api as needed, but we will probably only need GETs