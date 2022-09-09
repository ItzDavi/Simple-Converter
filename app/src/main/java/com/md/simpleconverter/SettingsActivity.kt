package com.md.simpleconverter

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val backButton = findViewById<ImageView>(R.id.back_settings_button)

        val themeSharedPref = getSharedPreferences("themeMode", Context.MODE_PRIVATE)
        manageRadioButtons(themeSharedPref)

        val titleLayout = findViewById<ConstraintLayout>(R.id.settings_title_constraintlayout)
        changeRandomColors(titleLayout, themeSharedPref)

        backButton.setOnClickListener {
            finish()
        }
    }

    private fun changeRandomColors(layout: ConstraintLayout, sharedPref: SharedPreferences) {
        val rootLayout = findViewById<ConstraintLayout>(R.id.settings_root_layout)
        rootLayout.setBackgroundColor(getColor(R.color.soft_white))

        val colors = arrayOf(
            getColor(R.color.red_tape_icon),
            getColor(R.color.blue),
            getColor(R.color.yellow_folder_icon),
            getColor(R.color.purple)
        )

        val randomColor = colors.random()

        window.clearFlags(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        window.statusBarColor = randomColor

        layout.background.setTint(randomColor)
    }

    private fun manageRadioButtons(sharedPref: SharedPreferences) {
        val randomRadioButton = findViewById<RadioButton>(R.id.customize_theme_random_radiobutton)
        val fixedRadioButton = findViewById<RadioButton>(R.id.customize_theme_fixed_radiobutton)

        val radioGroup = findViewById<RadioGroup>(R.id.customize_theme_radiogroup)

        val colorsLayout = findViewById<LinearLayout>(R.id.customize_theme_colors_gridlayout)

        randomRadioButton.setOnClickListener {
            colorsLayout.visibility = View.GONE
        }

        fixedRadioButton.setOnClickListener {
            colorsLayout.visibility = View.VISIBLE
        }

        //TODO SAVE TO SHARED PREF
    }
}