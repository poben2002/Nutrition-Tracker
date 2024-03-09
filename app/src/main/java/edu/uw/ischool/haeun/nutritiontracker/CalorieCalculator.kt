package edu.uw.ischool.haeun.nutritiontracker

object CalorieCalculator {
    // Constants for calorie calculation
    private const val MALE_CONSTANT = 66.5 // Constant for males
    private const val FEMALE_CONSTANT = 655.1 // Constant for females
    private const val CALORIES_PER_POUND = 3500 // Calories per pound

    fun calculateCalorieGoals(
        height: Double, // Height in inches
        weight: Double, // Weight in pounds
        sex: String,    // Sex ("male" or "female")
        age: Int,
        exerciseDaysPerWeek: Int, // Number of days exercised per week for at least 30 minutes
        weightGoal: String // Weight management goal ("lose", "gain", or "maintain")
    ): Int {
        val bmr: Double = if (sex.equals("male", ignoreCase = true)) {
            MALE_CONSTANT + (6.23 * weight) + (12.7 * height) - (6.8 * age)
        } else {
            FEMALE_CONSTANT + (4.35 * weight) + (4.7 * height) - (4.7 * age)
        }

        // Adjust BMR based on activity level
        val activityFactor = when {
            exerciseDaysPerWeek >= 5 -> 1.725 // Very active
            exerciseDaysPerWeek >= 3 -> 1.55  // Moderately active
            else -> 1.375 // Lightly active
        }

        val calorieGoal = when (weightGoal.toLowerCase()) {
            "lose" -> (bmr * activityFactor - 500).toInt() // Aim for 500 calories deficit for weight loss
            "gain" -> (bmr * activityFactor + 500).toInt() // Aim for 500 calories surplus for weight gain
            else -> (bmr * activityFactor).toInt() // Maintain weight
        }

        return calorieGoal
    }
}

