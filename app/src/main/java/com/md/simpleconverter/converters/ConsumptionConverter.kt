package com.md.simpleconverter.converters

import android.widget.EditText
import android.widget.Spinner

class ConsumptionConverter {

    private val consumptionTable = mapOf(
        "US mpg" to 2.352,
        "Imp mpg" to 2.825,
        "km/L" to 1.00
    )

    private var toUnit = ""
    private var fromValue = 0.0

    private fun toKmL(value: Double, fromUnit: String) : Double {
        var result = 0.0

        for (unit in consumptionTable.entries) {
            if (unit.key == fromUnit) {

                // TODO

                result = if (fromUnit == "" && toUnit == "L/100km" || fromUnit == "L/100km" && toUnit == "") {
                    100.00 / value

                } else {
                    value * unit.value.toString().toDouble()
                }
            }
        }

        return result
    }

    fun convert(fet: EditText, fromSpinner: Spinner, toSpinner: Spinner) : Double {
        // TODO

        toUnit = toSpinner.selectedItem.toString()
        fromValue = toKmL(fet.text.toString().toDouble(), fromSpinner.selectedItem.toString())

        var result = fromValue / consumptionTable[toUnit]!!


        return result
    }
}