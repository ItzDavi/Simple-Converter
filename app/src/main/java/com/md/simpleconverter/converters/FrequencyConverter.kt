package com.md.simpleconverter.converters

import android.widget.EditText
import android.widget.Spinner

class FrequencyConverter {

    private val frequencyTable = mapOf(
        "Hertz" to 1.00,
        "Kilohertz" to 1000.00,
        "Megahertz" to 1e+6,
        "Gigahertz" to 1e+9
    )

    private var toUnit = ""
    private var fromValue = 0.0

    private fun toHertz(value: Double, fromUnit: String) : Double {
        var result = 0.0

        for (unit in frequencyTable.entries) {
            if (unit.key == fromUnit) {
                result = value * unit.value.toString().toDouble()
            }
        }

        return result
    }

    fun convert(fet: EditText, fromSpinner: Spinner, toSpinner: Spinner) : Double {
        toUnit = toSpinner.selectedItem.toString()
        fromValue = toHertz(fet.text.toString().toDouble(), fromSpinner.selectedItem.toString())

        return fromValue / frequencyTable[toUnit]!!
    }
}