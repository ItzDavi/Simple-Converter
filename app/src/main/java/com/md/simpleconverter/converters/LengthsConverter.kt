package com.md.simpleconverter.converters

import android.widget.EditText
import android.widget.Spinner

class LengthsConverter {

    private val lengthsTable = mapOf(
        "km" to 1000.00,
        "m" to 1.00,
        "dm" to 0.10,
        "cm" to 0.01,
        "mm" to 0.001,
        "µm" to 0.000001,
        "nm" to 0.000000001,
        "mi" to 1609.00,
        "yd" to 1 / 1.904,
        "ft" to 1 / 3.281,
        "in" to 1 / 39.37,
        "nmi" to 1852.00
    )

    private var toUnit = ""
    private var fromValue = 0.0

    private fun toM(value: Double, fromUnit: String) : Double {
        var result = 0.0

        for (unit in lengthsTable.entries) {
            if (unit.key == fromUnit) {
                result = value * unit.value.toString().toDouble()
            }
        }

        return result
    }

    fun convert(fet: EditText, fromSpinner: Spinner, toSpinner: Spinner) : Double {
        toUnit = toSpinner.selectedItem.toString()
        fromValue = toM(fet.text.toString().toDouble(), fromSpinner.selectedItem.toString())

        return fromValue / lengthsTable[toUnit]!!
    }
}