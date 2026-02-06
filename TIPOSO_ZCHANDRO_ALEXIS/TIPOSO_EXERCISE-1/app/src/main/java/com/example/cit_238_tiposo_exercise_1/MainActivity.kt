package com.example.cit_238_tiposo_exercise_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        // 1. Find the button and set a click listener
        findViewById<Button>(R.id.enter_button)?.setOnClickListener {

            // 2. Get the TextView where the greeting will show
            val greetingDisplay = findViewById<TextView>(R.id.greeting_display)

            // 3. Get the values from the input fields and trim white space
            val firstName = findViewById<TextInputEditText>(R.id.first_name)
                ?.text.toString().trim()

            val lastName = findViewById<TextInputEditText>(R.id.last_name)
                ?.text.toString().trim()

            // 4. Check if the names are not empty
            if (firstName.isNotEmpty() && lastName.isNotEmpty()) {
                // 1. Create the full name string
                val nameToDisplay = "$firstName $lastName"

                // 2. Use the resource ID and pass nameToDisplay as the argument
                // This replaces %1$s in your XML with the actual name
                greetingDisplay?.text = getString(R.string.welcome_to_the_app, nameToDisplay)
            } else {
                // 5. If fields are empty, show a Toast message in the center
                Toast.makeText(this, getString(R.string.please_enter_a_name), Toast.LENGTH_LONG)
                    .apply {
                        setGravity(Gravity.CENTER, 0, 0)
                        show()
                    }
            }
        }
    }
}