package com.md.simpleconverter.converters

import android.widget.EditText
import android.widget.Spinner

class TemperatureConverter {
    fun convert(fet: EditText, fromSpinner: Spinner, toSpinner: Spinner, conversionType: String) : Double {
        val fromUnit = fromSpinner.selectedItem.toString()
        val toUnit = toSpinner.selectedItem.toString()

        val fromValue = fet.text.toString().toDouble()

        val kelvin = 273.15

        var result = 0.0

        if (conversionType == "temperature") {
            //Celsius convert
            if (fromUnit == "Celsius") {

                if (toUnit == "Kelvin") {
                    result = fromValue + kelvin

                } else if (toUnit == "Fahrenheit") {
                    result = (fromValue * 9 / 5) + 32
                }

                //Kelvin convert
            } else if (fromUnit == "Kelvin") {

                if (toUnit == "Celsius") {
                    result = fromValue - kelvin

                } else if (toUnit == "Fahrenheit") {
                    result = ((fromValue - kelvin) * 9 / 5) + 32
                }

            } else if (fromUnit == "Fahrenheit") {

                if (toUnit == "Celsius") {
                    result = (fromValue - 32) * 5 / 9

                } else if (toUnit == "Kelvin") {
                    result = ((fromValue - 32) * 5 / 9) + kelvin
                }
            }
        }

        return result
    }
}