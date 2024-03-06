package edu.uw.ischool.haeun.nutritiontracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.content.Intent


class AddDataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_data)

        val addButton: Button = findViewById(R.id.addButton)
        addButton.setOnClickListener {
            val foodEditText: EditText = findViewById(R.id.foodEditText)
            val portionEditText: EditText = findViewById(R.id.portionEditText)
            val calorieEditText: EditText = findViewById(R.id.calorieEditText)

            val name = foodEditText.text.toString()
            val portionSize = portionEditText.text.toString().toFloatOrNull() ?: 0.0f
            val calorieCount = calorieEditText.text.toString().toIntOrNull() ?: 0

            if (name.isNotBlank() && portionSize > 0 && calorieCount > 0) {
                // Create an intent to pass the data to HomepageActivity
                val intent = Intent(this, HomepageActivity::class.java)
                intent.putExtra("foodName", name)
                intent.putExtra("portionSize", portionSize)
                intent.putExtra("calorieCount", calorieCount)
                startActivity(intent)

                Toast.makeText(this, "Food entry added to dashboard", Toast.LENGTH_SHORT).show()
                // Clear input fields
                foodEditText.text.clear()
                portionEditText.text.clear()
                calorieEditText.text.clear()
            } else {
                Toast.makeText(this, "Please fill in all fields with valid data", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

