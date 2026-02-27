package com.example.cit238_vergara_excercise1

import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cit238_vergara_excercise1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Binding allows us to access views without using findViewById
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set the click listener for the "Enter" button
        binding.enterButton.setOnClickListener {

            // Get text from the input fields and remove extra spaces
            val firstName = binding.firstName.text.toString().trim()
            val lastName = binding.lastName.text.toString().trim()

            // Check if both fields are filled in
            if (firstName.isNotEmpty() && lastName.isNotEmpty()) {
                val nameToDisplay = "$firstName $lastName"

                // Show the greeting in the TextView
                binding.greetingDisplay.text = "${getString(R.string.welcome_to_the_app)} $nameToDisplay!"
            } else {
                // Show a Toast message if fields are empty
                Toast.makeText(this, getString(R.string.please_enter_a_name), Toast.LENGTH_LONG)
                    .apply {
                        setGravity(Gravity.CENTER, 0, 0)
                        show()
                    }
            }
        }
    }
}