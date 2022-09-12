package com.md.simpleconverter.converters

import android.widget.EditText
import android.widget.Spinner

class DataDimConverter {

    private val datadimTable = mapOf(
        "Bit" to 8.00,
        "Kbit" to 1 / 125.00,
        "Mbit" to 1 / 125000.00,
        "Gbit" to 1 / 1.25e+8,
        "Tbit" to 1 / 1.25e+11,
        "Pbit" to 1 / 1.25e+14,
        "Byte" to 1.00,
        "KByte" to 1 / 1000.00,
        "MByte" to 1 / 1e+6,
        "GByte" to 1 / 1e+9,
        "TByte" to 1 / 1e+12,
        "PByte" to 1 / 1e+15
    )

    private var toUnit = ""
    private var fromValue = 0.0

    private fun toByte(value: Double, fromUnit: String) : Double {
        var result = 0.0

        for (unit in datadimTable.entries) {
            if (unit.key == fromUnit) {
                result = value / unit.value.toString().toDouble()
            }
        }

        return result
    }

    fun convert(fet: EditText, fromSpinner: Spinner, toSpinner: Spinner) : Double {
        val fromUnit = fromSpinner.selectedItem.toString()
        val valueToConvert = fet.text.toString().toDouble()

        toUnit = toSpinner.selectedItem.toString()
        fromValue = toByte(valueToConvert, fromUnit)

        return fromValue * datadimTable[toUnit]!!
    }
}