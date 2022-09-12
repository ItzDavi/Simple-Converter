package com.md.simpleconverter.converters

import android.widget.EditText
import android.widget.Spinner

class ConsumptionConverter {

    private val consumptionTable = mapOf(
        "US mpg" to 2.352,
        "Imp mpg" to 2.825,
        "km/L" to 1.00,
        "L/100km" to 100.00
    )

    private var toUnit = ""
    private var fromValue = 0.0

    private fun toKmL(value: Double, fromUnit: String) : Double {
        var result = 0.0

        for (unit in consumptionTable.entries) {
            if (unit.key == fromUnit) {
                result = if (fromUnit == "km/L" && toUnit == "L/100km" || fromUnit == "L/100km" && toUnit == "km/L") {
                    100.00 * value

                } else {
                    value * unit.value.toString().toDouble()
                }
            }
        }

        return result
    }

    fun convert(fet: EditText, fromSpinner: Spinner, toSpinner: Spinner): Double {
        val fromUnit = fromSpinner.selectedItem.toString()
        val valueToConvert = fet.text.toString().toDouble()

        toUnit = toSpinner.selectedItem.toString()
        fromValue = toKmL(valueToConvert, fromUnit)

        return fromValue / consumptionTable[toUnit]!!
    }
}