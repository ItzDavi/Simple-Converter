package com.md.simpleconverter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.net.toUri
import com.google.android.material.card.MaterialCardView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this, ConverterActivity::class.java)

        val dataDimCardView = findViewById<MaterialCardView>(R.id.home_datadim_cardview)
        val lengthsCardView = findViewById<MaterialCardView>(R.id.home_lengths_cardview)
        val velocityCardView = findViewById<MaterialCardView>(R.id.home_velocity_cardview)
        val temperatureCardView = findViewById<MaterialCardView>(R.id.home_temperature_cardview)
        val massCardView = findViewById<MaterialCardView>(R.id.home_mass_cardview)
        val timeCardView = findViewById<MaterialCardView>(R.id.home_time_cardview)

        dataDimCardView.setOnClickListener {
            startConverter("datadim", intent)
        }

        lengthsCardView.setOnClickListener {
            startConverter("lengths", intent)
        }

        velocityCardView.setOnClickListener {
            startConverter("velocity", intent)
        }

        temperatureCardView.setOnClickListener {
            startConverter("temperature", intent)
        }

        massCardView.setOnClickListener {
            startConverter("mass", intent)
        }

        timeCardView.setOnClickListener {
            startConverter("time", intent)
        }
    }

    private fun cleanExtra(i: Intent) {
        if (i.hasExtra("conversion")) i.removeExtra("conversion")
    }

    private fun startConverter(conversion: String, i: Intent) {
        cleanExtra(i)
        i.putExtra("conversion", conversion)
        startActivity(i)
    }
}