package com.md.simpleconverter

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import de.hdodenhof.circleimageview.CircleImageView

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val sharedPreferences = getSharedPreferences("com.md.simpleconverter", MODE_PRIVATE)

        val backButton = findViewById<ImageView>(R.id.back_settings_button)

        val titleLayout = findViewById<ConstraintLayout>(R.id.settings_title_constraintlayout)
        val colorsLayout = findViewById<LinearLayout>(R.id.customize_theme_colors_gridlayout)

        val randomRadioButton = findViewById<RadioButton>(R.id.customize_theme_random_radiobutton)
        val fixedRadioButton = findViewById<RadioButton>(R.id.customize_theme_fixed_radiobutton)
        val radioGroup = findViewById<RadioGroup>(R.id.customize_theme_radiogroup)

        val changeThemeLayout = findViewById<LinearLayout>(R.id.change_theme_linear_layout)
        changeThemeLayout.setOnClickListener {
            val radioButtonsLayout = findViewById<LinearLayout>(R.id.customize_theme_options_layout)
            val expandCollapseImageView = findViewById<ImageView>(R.id.expand_customize_theme)

            if (radioButtonsLayout.visibility == View.VISIBLE) {
                expandCollapseImageView.setImageResource(R.drawable.ic_round_expand_more_24)
                radioButtonsLayout.visibility = View.GONE

            } else {
                expandCollapseImageView.setImageResource(R.drawable.ic_round_expand_less_24)
                radioButtonsLayout.visibility = View.VISIBLE
            }
        }

        manageRadioButtons(sharedPreferences, radioGroup, randomRadioButton, fixedRadioButton, colorsLayout)
        changeRandomColors(titleLayout, sharedPreferences)

        backButton.setOnClickListener {
            finish()
        }
    }

    private fun changeRandomColors(layout: ConstraintLayout, sharedPref: SharedPreferences) {
        val rootLayout = findViewById<ConstraintLayout>(R.id.settings_root_layout)
        rootLayout.setBackgroundColor(getColor(R.color.soft_white))

        val randomTheme = sharedPref.getBoolean("random_theme", true)
        val themeColor = sharedPref.getInt("theme_color", 0)

        window.clearFlags(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)

        val colors = arrayOf(
            getColor(R.color.red_tape_icon),
            getColor(R.color.blue),
            getColor(R.color.yellow_folder_icon),
            getColor(R.color.purple)
        )

        if (randomTheme) {
            val randomColor = colors.random()
            window.statusBarColor = randomColor
            layout.background.setTint(randomColor)

            Log.d("randomThemeInfo", "$randomTheme *** $themeColor *** $randomColor")

        } else if (!randomTheme) {
            val color = colors[themeColor]
            window.statusBarColor = color
            layout.background.setTint(color)

            Log.d("fixedThemeInfo", "$randomTheme *** $themeColor *** $color")
        }

        Log.d("themeInfo", "$randomTheme *** $themeColor")
    }

    private fun manageRadioButtons(sharedPref: SharedPreferences, radioGroup: RadioGroup, randomRadioButton: RadioButton, fixedRadioButton: RadioButton, colorsLayout: LinearLayout) {
        val editor = sharedPref.edit()

        val titleLayout = findViewById<ConstraintLayout>(R.id.settings_title_constraintlayout)

        val yellowColor = findViewById<CircleImageView>(R.id.yellow_color_imageview)
        val blueColor = findViewById<CircleImageView>(R.id.blue_color_imageview)
        val redColor = findViewById<CircleImageView>(R.id.red_color_imageview)
        val purpleColor = findViewById<CircleImageView>(R.id.purple_color_imageview)

        randomRadioButton.isChecked = sharedPref.getBoolean("random_theme", true)
        fixedRadioButton.isChecked = sharedPref.getBoolean("fixed_theme", false)

        if (fixedRadioButton.isChecked) {
            colorsLayout.visibility = View.VISIBLE
        } else {
            colorsLayout.visibility = View.GONE
        }

        radioGroup.setOnCheckedChangeListener { _, _ ->
            if (!randomRadioButton.isChecked) {
                editor.putBoolean("random_theme", false)
                editor.putBoolean("fixed_theme", true)
                editor.apply()

                colorsLayout.visibility = View.VISIBLE

            } else {
                editor.putBoolean("random_theme", true)
                editor.putBoolean("fixed_theme", false)
                editor.putInt("theme_color", 0)
                editor.apply()

                changeRandomColors(titleLayout, sharedPref)

                colorsLayout.visibility = View.GONE
            }
        }

        yellowColor.setOnClickListener {
            val colorChoice = sharedPref.getInt("theme_color", 3)
            ThemeUtils().manageSettingsColors(yellowColor, blueColor, redColor, purpleColor, sharedPref, colorChoice)
            changeRandomColors(titleLayout, sharedPref)
        }

        blueColor.setOnClickListener {
            val colorChoice = sharedPref.getInt("theme_color", 3)
            ThemeUtils().manageSettingsColors(yellowColor, blueColor, redColor, purpleColor, sharedPref, colorChoice)
            changeRandomColors(titleLayout, sharedPref)
        }

        redColor.setOnClickListener {
            val colorChoice = sharedPref.getInt("theme_color", 3)
            ThemeUtils().manageSettingsColors(yellowColor, blueColor, redColor, purpleColor, sharedPref, colorChoice)
            changeRandomColors(titleLayout, sharedPref)
        }

        purpleColor.setOnClickListener {
            val colorChoice = sharedPref.getInt("theme_color", 3)
            ThemeUtils().manageSettingsColors(yellowColor, blueColor, redColor, purpleColor, sharedPref, colorChoice)
            changeRandomColors(titleLayout, sharedPref)
        }

        val colorChoice = sharedPref.getInt("theme_color", 3)
        ThemeUtils().manageSettingsColors(yellowColor, blueColor, redColor, purpleColor, sharedPref, colorChoice)
    }
}