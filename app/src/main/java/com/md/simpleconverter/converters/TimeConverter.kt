package com.md.simpleconverter.converters

import android.widget.EditText
import android.widget.Spinner
import kotlin.math.roundToInt

class TimeConverter {

    private val timeTable = mapOf(
        "Centuries" to 1 / 36500.00,
        "Decades" to 1 / 3650.00,
        "Years" to 1 / 365.00,
        "Months" to 1 / 30.417,
        "Days" to 1.00,
        "Hours" to 24.00,
        "Minutes" to 1440.00,
        "Seconds" to 86400.00,
        "Milliseconds" to 8.64e+7
    )

    private var toUnit = ""
    private var fromValue = 0.0

    private fun toDays(value: Double, fromUnit: String) : Double {
        var result = 0.0

        for (unit in timeTable.entries) {
            if (unit.key == fromUnit) {
                result = value / unit.value.toString().toDouble()
            }
        }

        return result
    }

    fun convert(fet: EditText, fromSpinner: Spinner, toSpinner: Spinner) : Double {
        toUnit = toSpinner.selectedItem.toString()
        fromValue = toDays(fet.text.toString().toDouble(), fromSpinner.selectedItem.toString())

        return fromValue * timeTable[toUnit]!!
    }
}