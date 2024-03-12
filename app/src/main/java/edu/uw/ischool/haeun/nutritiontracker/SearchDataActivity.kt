package edu.uw.ischool.haeun.nutritiontracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
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



        // search for a food item using API
        val searchButton: Button = findViewById(R.id.searchbutton)
        val foodEditText: EditText = findViewById(R.id.foodEditText)
        val input = foodEditText.text.toString()
        val output_name: TextView = findViewById(R.id.foodName)
        val output_calories: TextView = findViewById(R.id.calorieAmount)

        searchButton.setOnClickListener {


            if (input.isNotBlank()) {
                //TODO: make GET API call using input variable

                // Make API call
                val call: Call<NutritionResponse> = nutritionAPI.getNutritionData()
                call.enqueue(object : Callback<NutritionResponse> {
                    override fun onResponse(call: Call<NutritionResponse>, response: Response<NutritionResponse>) {
                        if (response.isSuccessful) {
                            val nutritionResponse: NutritionResponse? = response.body()
                            if (nutritionResponse != null) {
                                output_name.text = nutritionResponse.itemName
                                output_calories.text = nutritionResponse.calories.toString()
                            }

                        } else {
                            // Handle errors
                            Toast.makeText(this, "Could not get a response from the API", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<NutritionResponse>, t: Throwable) {
                        // Handle failures
                        Toast.makeText(this, "Could not find the food in the database", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                Toast.makeText(this, "Please enter a food to search for", Toast.LENGTH_SHORT).show()
            }
        }

        // redirect to add data activity
        val addButton: Button = findViewById(R.id.adddatabutton)
        addButton.setOnClickListener {
            val foodEditText: EditText = findViewById(R.id.foodEditText)
            val intent = Intent(this, AddDataActivity::class.java)
            startActivity(intent)
            foodEditText.text.clear()
        }

    }
}