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
import kotlin.math.roundToInt

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

        val outputName: TextView = findViewById(R.id.foodName)
        val outputCalories: TextView = findViewById(R.id.calorieAmount)

        addbutton.setOnClickListener {
            val intent = Intent(this, HomepageActivity::class.java)
            val calories =  outputCalories.text.substring(0, outputCalories.text.length - 9)
            Log.d("CAL_COUNT", calories)
            intent.putExtra("foodName", outputName.text.toString())
            intent.putExtra("portionSize", 1.0f)
            intent.putExtra("calorieCount", calories.toFloat().roundToInt())
            startActivity(intent)
        }


        searchButton.setOnClickListener {
            input = foodEditText.text.toString()
            if (input.isNotBlank()) {

                // Make API call
                val call: Call<List<NutritionResponse>> = nutritionAPI.getNutritionData(input)
                call.enqueue(object : Callback<List<NutritionResponse>> {
                    override fun onResponse(
                        call: Call<List<NutritionResponse>>,
                        response: Response<List<NutritionResponse>>
                    ) {
                        if (response.isSuccessful) {
                            // Successful API call
                            val nutritionResponse: List<NutritionResponse>? = response.body()
                            if (nutritionResponse != null && nutritionResponse.first() != null) {
                                outputName.text = nutritionResponse.first().name
                                outputCalories.text = nutritionResponse.first().calories.toString() + " calories"
                                addbutton.visibility = View.VISIBLE
                            }
                        } else {
                            // Handle errors
                            Toast.makeText(this@SearchDataActivity, "Could not get a response from the API", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<List<NutritionResponse>>, t: Throwable) {
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