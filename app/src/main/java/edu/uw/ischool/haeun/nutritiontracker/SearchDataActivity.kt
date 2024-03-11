package edu.uw.ischool.haeun.nutritiontracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchDataActivity : AppCompatActivity() {
    private lateinit var nutritionAPI: NutritionAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_data)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api-ninjas.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        nutritionAPI = retrofit.create(NutritionAPI::class.java)

        // Make API call
        val call: Call<NutritionResponse> = nutritionAPI.getNutritionData()
        call.enqueue(object : Callback<NutritionResponse> {
            override fun onResponse(call: Call<NutritionResponse>, response: Response<NutritionResponse>) {
                if (response.isSuccessful) {
                    val nutritionResponse: NutritionResponse? = response.body()
                    // Handle the response here
                } else {
                    // Handle errors
                }
            }

            override fun onFailure(call: Call<NutritionResponse>, t: Throwable) {
                // Handle failures
            }
        })
    }
}