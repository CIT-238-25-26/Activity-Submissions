package com.example.cit_238_briones_exercise_1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Gravity
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.cit_238_briones_exercise_1.ui.theme.Cit_238Brionesexercise1Theme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set content view to R.layout.main as the file is named main.xml
        setContentView(R.layout.main)

        findViewById<Button>(R.id.enter_button)?.setOnClickListener {
            // Get the greeting display text
            val greetingDisplay = findViewById<TextView>(R.id.greeting_display)

            // Get the first name TextInputEditText value
            val firstName = findViewById<TextInputEditText>(R.id.first_name)
                ?.text.toString().trim() ?: ""

            // Get the last name TextInputEditText value
            val lastName = findViewById<TextInputEditText>(R.id.last_name)
                ?.text.toString().trim() ?: ""

            // Check names are not empty and update display
            if (firstName.isNotEmpty() && lastName.isNotEmpty()) {
                val nameToDisplay = firstName.plus(" ").plus(lastName)
                // Use Kotlin's string templates feature to display the name
                greetingDisplay?.text = " ${getString(R.string.welcome_to_the_app)} ${nameToDisplay}!"
            } else {
                Toast.makeText(this, getString(R.string.please_enter_a_name), Toast.LENGTH_LONG)
                    .apply {
                        setGravity(Gravity.CENTER, 0, 0)
                        show()
                    }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Cit_238Brionesexercise1Theme {
        Greeting("Android")
    }
}