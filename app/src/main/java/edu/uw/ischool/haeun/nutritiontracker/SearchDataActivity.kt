package edu.uw.ischool.haeun.nutritiontracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
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
            .baseUrl("https://api.api-ninjas.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        nutritionAPI = retrofit.create(NutritionAPI::class.java)

        // search for a food item using API
        val searchButton: Button = findViewById(R.id.searchbutton)
        val foodEditText: EditText = findViewById(R.id.searchbar)
        val addbutton: Button = findViewById(R.id.addbutton)
        var input = ""

        val output_name: TextView = findViewById(R.id.foodName)
        val output_calories: TextView = findViewById(R.id.calorieAmount)

        addbutton.setOnClickListener {
            val intent = Intent(this, HomepageActivity::class.java)
            intent.putExtra("foodName", output_name.text.toString())
            intent.putExtra("portionSize", 1)
            intent.putExtra("calorieCount", output_calories.text.toString().toIntOrNull() ?: 0)
            startActivity(intent)
        }


        searchButton.setOnClickListener {
            input = foodEditText.text.toString()
            if (input.isNotBlank()) {

                // Make API call
                val call: Call<NutritionResponse> = nutritionAPI.getNutritionData(input)
                call.enqueue(object : Callback<NutritionResponse> {
                    override fun onResponse(call: Call<NutritionResponse>, response: Response<NutritionResponse>) {
                        if (response.isSuccessful) {

                            // Successful API call
                            Log.d("STATE", response.body().toString())
                            val nutritionResponse: NutritionResponse? = response.body()
                            if (nutritionResponse != null) {
                                output_name.text = nutritionResponse.itemName
                                output_calories.text = nutritionResponse.calories.toString()
                                addbutton.visibility = View.VISIBLE
                            }
                        } else {
                            // Handle errors
                            Toast.makeText(this@SearchDataActivity, "Could not get a response from the API", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<NutritionResponse>, t: Throwable) {
                        // Handle failures
                        Toast.makeText(this@SearchDataActivity, "Could not find the food in the database", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                Toast.makeText(this, "Please enter a food to search for", Toast.LENGTH_SHORT).show()
            }
        }

        // redirect to add data activity
        val adddataButton: Button = findViewById(R.id.adddatabutton)
        adddataButton.setOnClickListener {
            val foodEditText: EditText = findViewById(R.id.searchbar)
            val intent = Intent(this, AddDataActivity::class.java)
            startActivity(intent)
            foodEditText.text.clear()
        }

    }
}