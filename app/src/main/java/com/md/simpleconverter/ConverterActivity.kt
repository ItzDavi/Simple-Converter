package com.md.simpleconverter

import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.text.method.DigitsKeyListener
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.md.simpleconverter.converters.*
import kotlin.math.round

class ConverterActivity : AppCompatActivity() {
    private lateinit var spinnerData: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_converter)

        val conversion = intent.getStringExtra("conversion")
        val conversionTextView = findViewById<TextView>(R.id.converter_textview)
        val convertButton = findViewById<Button>(R.id.convert_button)

        val resultTextView = findViewById<TextView>(R.id.conversion_result_textview)

        val fromSpinner = findViewById<Spinner>(R.id.from_spinner)
        val toSpinner = findViewById<Spinner>(R.id.to_spinner)

        val backButton = findViewById<ImageView>(R.id.back_button_imageview)
        backButton.setOnClickListener {
            finish()
        }

        val fromEditText = findViewById<EditText>(R.id.from_edittext)

        initEditText(fromEditText)

        spinnerData = ArrayList()

        if (conversion != null) {
            start(conversion, conversionTextView, fromSpinner, toSpinner)

            convertButton.setOnClickListener {
                if (checkInputs(fromEditText, fromSpinner, toSpinner)) {
                    resultTextView.visibility = View.VISIBLE

                    if (conversion == "lengths") {
                        (fromEditText.text.toString() + " " + fromSpinner.selectedItem.toString() + "   =   " + convert(fromEditText, fromSpinner, toSpinner, conversion).round(5).toString() + " " + toSpinner.selectedItem.toString())
                            .also { resultTextView.text = it }

                    } else if (conversion == "datadim") {
                        (fromEditText.text.toString() + " " + fromSpinner.selectedItem.toString() + "   =   " + convert(fromEditText, fromSpinner, toSpinner, conversion).round(7).toString() + " " + toSpinner.selectedItem.toString())
                            .also { resultTextView.text = it }

                    } else if (conversion == "mass") {
                        (fromEditText.text.toString() + " " + fromSpinner.selectedItem.toString() + "   =   " + convert(fromEditText, fromSpinner, toSpinner, conversion).round(4).toString() + " " + toSpinner.selectedItem.toString())
                            .also { resultTextView.text = it }

                    } else {
                        (fromEditText.text.toString() + " " + fromSpinner.selectedItem.toString() + "   =   " + convert(fromEditText, fromSpinner, toSpinner, conversion).round(2).toString() + " " + toSpinner.selectedItem.toString())
                            .also { resultTextView.text = it }
                    }
                }
            }

        } else {
            Toast.makeText(this, "Failed getting intents from Main Activity", Toast.LENGTH_SHORT).show()
        }
    }

    private fun start(conversion: String, conversionTV: TextView, fromSpinner: Spinner, toSpinner: Spinner) {
        when (conversion) {
            "datadim" -> {
                "Data Dimension".also { conversionTV.text = it }

                loadSpinnerArray("datadim")

                val spinnerAdapter = ArrayAdapter(this, R.layout.custom_spinner_layout, spinnerData)
                fromSpinner.adapter = spinnerAdapter
                toSpinner.adapter = spinnerAdapter
            }

            "lengths" -> {
                "Lengths".also { conversionTV.text = it }

                loadSpinnerArray("lengths")

                val spinnerAdapter = ArrayAdapter(this, R.layout.custom_spinner_layout, spinnerData)
                fromSpinner.adapter = spinnerAdapter
                toSpinner.adapter = spinnerAdapter
            }

            "velocity" -> {
                "Velocity".also { conversionTV.text = it }

                loadSpinnerArray("velocity")

                val spinnerAdapter = ArrayAdapter(this, R.layout.custom_spinner_layout, spinnerData)
                fromSpinner.adapter = spinnerAdapter
                toSpinner.adapter = spinnerAdapter
            }

            "temperature" -> {
                "Temperature".also { conversionTV.text = it }

                loadSpinnerArray("temperature")

                val spinnerAdapter = ArrayAdapter(this, R.layout.custom_spinner_layout, spinnerData)
                fromSpinner.adapter = spinnerAdapter
                toSpinner.adapter = spinnerAdapter
            }

            "mass" -> {
                "Mass".also { conversionTV.text = it }

                loadSpinnerArray("mass")

                val spinnerAdapter = ArrayAdapter(this, R.layout.custom_spinner_layout, spinnerData)
                fromSpinner.adapter = spinnerAdapter
                toSpinner.adapter = spinnerAdapter
            }

            "time" -> {
                "Time".also { conversionTV.text = it }

                loadSpinnerArray("time")

                val spinnerAdapter = ArrayAdapter(this, R.layout.custom_spinner_layout, spinnerData)
                fromSpinner.adapter = spinnerAdapter
                toSpinner.adapter = spinnerAdapter
            }
        }
    }

    private fun initEditText(fet: EditText) {
        fet.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL or InputType.TYPE_NUMBER_FLAG_SIGNED
        fet.keyListener = DigitsKeyListener.getInstance("0123456789,")
    }


    private fun loadSpinnerArray(conversionType: String) {
        if (spinnerData.isNotEmpty()) {
            spinnerData.clear()
        }

        when (conversionType) {
            "lengths" -> {
                spinnerData.addAll(listOf("km", "m", "dm", "cm", "mm", "µm", "nm", "mi", "yd", "ft", "in", "nmi"))
            }

            "datadim" -> {
                spinnerData.addAll(listOf("Bit", "Kbit", "Mbit", "Gbit", "Tbit", "Pbit", "Byte", "KByte", "MByte", "GByte", "TByte", "PByte"))
            }

            "velocity" -> {
                spinnerData.addAll(listOf("mp/h", "ft/s", "m/s", "km/h", "kn"))
            }

            "temperature" -> {
                spinnerData.addAll(listOf("Celsius", "Fahrenheit", "Kelvin"))
            }

            "mass" -> {
                spinnerData.addAll(listOf("T", "Q", "Kg", "hg", "dag", "g", "dg", "cg", "mg", "lb", "oz"))
            }

            "time" -> {
                spinnerData.addAll(listOf("Centuries", "Decades", "Years", "Months", "Days", "Hours", "Minutes", "Seconds", "Milliseconds"))
            }
        }
    }

    private fun checkInputs(fet: EditText, fromSpinner: Spinner, toSpinner: Spinner) : Boolean {
        var flag = false

        if (TextUtils.isEmpty(fet.text)) {
            Toast.makeText(this, "Some inputs are missing", Toast.LENGTH_SHORT).show()

        } else if (fromSpinner.selectedItem.toString() == toSpinner.selectedItem.toString()) {
            Toast.makeText(this, "Why would you convert to the same measure unit ?", Toast.LENGTH_SHORT).show()

        } else {
            if (TextUtils.isDigitsOnly(fet.text)) {
                flag = true
            }
        }

        return flag
    }

    private fun convert(fet: EditText, fromSpinner: Spinner, toSpinner: Spinner, conversionType: String) : Double {
        var result = 0.0

        when (conversionType) {
            "datadim" -> {
                result = DataDimConverter().convert(fet, fromSpinner, toSpinner)
            }

            "lengths" -> {
                result = LengthsConverter().convert(fet, fromSpinner, toSpinner)
            }

            "velocity" -> {
                result = VelocityConverter().convert(fet, fromSpinner, toSpinner)
            }

            "temperature" -> {
                result = TemperatureConverter().convert(fet, fromSpinner, toSpinner)
            }

            "mass" -> {
                result = MassConverter().convert(fet, fromSpinner, toSpinner)
            }

            "time" -> {
                result = TimeConverter().convert(fet, fromSpinner, toSpinner)
            }
        }

        return result
    }

    private fun Double.round(decimals: Int) : Double {
        var multiplier = 1.00
        repeat(decimals) { multiplier *= 10 }
        return round(this * multiplier) / multiplier
    }
}