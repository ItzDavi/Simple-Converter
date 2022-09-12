package com.md.simpleconverter.converters

import android.widget.EditText
import android.widget.Spinner

class VelocityConverter {

    private val velocityTable = mapOf(
        "mp/h" to 2.237,
        "ft/s" to 3.281,
        "km/h" to 3.6,
        "kn" to 1.944,
        "m/s" to 1.0
    )

    private var toUnit = ""
    private var fromValue = 0.0

    private fun toMS(value: Double, fromUnit: String) : Double {
        return value / velocityTable[fromUnit]!!
    }

    fun convert(fet: EditText, fromSpinner: Spinner, toSpinner: Spinner) : Double {
        val fromUnit = fromSpinner.selectedItem.toString()
        val valueToConvert = fet.text.toString().toDouble()

        toUnit = toSpinner.selectedItem.toString()
        fromValue = toMS(valueToConvert, fromUnit)

        return fromValue * velocityTable[toUnit]!!
    }
}