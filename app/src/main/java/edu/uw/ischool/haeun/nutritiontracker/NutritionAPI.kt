package edu.uw.ischool.haeun.nutritiontracker

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface NutritionAPI {
    @Headers("X-Api-Key: L5mVtmGsD1EBdiuhOM6lAg==advHXEa7n7uizaVF")
    @GET("/api/nutrition")
    fun getNutritionData(): Call<NutritionResponse>
}

// Add endpoints to the api as needed, but we will probably only need GETs