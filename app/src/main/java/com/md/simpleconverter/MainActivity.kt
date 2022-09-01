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

        dataDimCardView.setOnClickListener {
            cleanExtra(intent)
            intent.putExtra("conversion", "datadim")
            startActivity(intent)
        }

        lengthsCardView.setOnClickListener {
            cleanExtra(intent)
            intent.putExtra("conversion", "lengths")
            startActivity(intent)
        }

        velocityCardView.setOnClickListener {
            cleanExtra(intent)
            intent.putExtra("conversion", "velocity")
            startActivity(intent)
        }

        temperatureCardView.setOnClickListener {
            cleanExtra(intent)
            intent.putExtra("conversion", "temperature")
            startActivity(intent)
        }
    }

    private fun cleanExtra(i: Intent) {
        if (i.hasExtra("conversion")) i.removeExtra("conversion")
    }
}