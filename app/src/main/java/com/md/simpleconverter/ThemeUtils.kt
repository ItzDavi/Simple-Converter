package com.md.simpleconverter

import android.content.SharedPreferences
import de.hdodenhof.circleimageview.CircleImageView

class ThemeUtils {

    fun manageSettingsColors(color1: CircleImageView, color2: CircleImageView, color3: CircleImageView, color4: CircleImageView, sharedPref: SharedPreferences, colorChoice: Int) {
        val editor = sharedPref.edit()

        when (colorChoice) {
            0 -> {
                color1.borderWidth = 5
                color2.borderWidth = 0
                color3.borderWidth = 0
                color4.borderWidth = 0

                editor.putInt("theme_color", 0)
                editor.apply()
            }

            1 -> {
                color1.borderWidth = 0
                color2.borderWidth = 5
                color3.borderWidth = 0
                color4.borderWidth = 0

                editor.putInt("theme_color", 1)
                editor.apply()
            }

            2 -> {
                color1.borderWidth = 0
                color2.borderWidth = 0
                color3.borderWidth = 5
                color4.borderWidth = 0

                editor.putInt("theme_color", 2)
                editor.apply()
            }

            3 -> {
                color1.borderWidth = 0
                color2.borderWidth = 0
                color3.borderWidth = 0
                color4.borderWidth = 5

                editor.putInt("theme_color", 3)
                editor.apply()
            }
        }
    }
}