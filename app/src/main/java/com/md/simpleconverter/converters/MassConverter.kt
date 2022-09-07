package com.md.simpleconverter.converters

import android.widget.EditText
import android.widget.Spinner

class MassConverter {

    private val massTable = mapOf(
        "T" to 1000000.00,
        "Q" to 100000.00,
        "Kg" to 1000.00,
        "hg" to 100.00,
        "dag" to 10.00,
        "g" to 1.00,
        "dg" to 0.10,
        "cg" to 0.01,
        "mg" to 0.0001,
        "lb" to 453.6,
        "oz" to 28.35
    )

    private var toUnit = ""
    private var fromValue = 0.0

    private fun toGrams(value: Double, fromUnit: String) : Double {
        var result = 0.0

        for (unit in massTable.entries) {
            if (unit.key == fromUnit) {
                result = value * unit.value.toString().toDouble()
            }
        }

        return result
    }

    fun convert(fet: EditText, fromSpinner: Spinner, toSpinner: Spinner) : Double {
        toUnit = toSpinner.selectedItem.toString()
        fromValue = toGrams(fet.text.toString().toDouble(), fromSpinner.selectedItem.toString())

        return fromValue / massTable[toUnit]!!
    }
}