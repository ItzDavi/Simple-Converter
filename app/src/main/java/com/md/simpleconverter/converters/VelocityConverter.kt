package com.md.simpleconverter.converters

import android.widget.EditText
import android.widget.Spinner

class VelocityConverter {
    
    fun convert(fet: EditText, fromSpinner: Spinner, toSpinner: Spinner, conversionType: String) : Double {
        val fromUnit = fromSpinner.selectedItem.toString()
        val toUnit = toSpinner.selectedItem.toString()

        val fromValue = fet.text.toString().toDouble()

        var result = 0.0

        if (conversionType == "velocity") {

            when (fromUnit) {
                //fromUnit
                "mp/h" -> {

                    when (toUnit) {
                        "ft/s" -> {
                            result = fromValue * 1.467

                        }
                        "m/s" -> {
                            result = fromValue / 2.237

                        }
                        "km/h" -> {
                            result = fromValue * 1.609

                        }
                        "kn" -> {
                            result = fromValue / 1.151

                        }
                    }

                }
                //fromUnit
                "ft/s" -> {

                    when (toUnit) {
                        "mp/h" -> {
                            result = fromValue / 1.467

                        }
                        "m/s" -> {
                            result = fromValue / 3.281

                        }
                        "km/h" -> {
                            result = fromValue * 1.907

                        }
                        "kn" -> {
                            result = fromValue / 1.688

                        }
                    }

                }
                //fromUnit
                "m/s" -> {

                    when (toUnit) {
                        "mp/h" -> {
                            result = fromValue * 2.237

                        }
                        "ft/s" -> {
                            result = fromValue * 3.281

                        }
                        "km/h" -> {
                            result = fromValue * 3.6

                        }
                        "kn" -> {
                            result = fromValue * 1.944

                        }
                    }

                }
                //fromUnit
                "km/h" -> {

                    when (toUnit) {
                        "mp/h" -> {
                            result = fromValue / 1.609

                        }
                        "ft/s" -> {
                            result = fromValue / 1.907

                        }
                        "m/s" -> {
                            result = fromValue / 3.6

                        }
                        "kn" -> {
                            result = fromValue / 1.852

                        }
                    }

                }
                //fromUnit
                "kn" -> {

                    when (toUnit) {
                        "mp/h" -> {
                            result = fromValue * 1.151

                        }
                        "ft/s" -> {
                            result = fromValue * 1.688

                        }
                        "m/s" -> {
                            result = fromValue / 1.944

                        }
                        "km/h" -> {
                            result = fromValue * 1.852

                        }
                    }
                }
            }

        }

        return result
    }
}