package com.md.simpleconverter

import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.text.method.DigitsKeyListener
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.md.simpleconverter.converters.TemperatureConverter
import com.md.simpleconverter.converters.VelocityConverter

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
        //val toEditText = findViewById<EditText>(R.id.to_edittext)
        initEditText(fromEditText)

        spinnerData = ArrayList()

        if (conversion != null) {
            start(conversion, conversionTextView, fromSpinner, toSpinner)

            convertButton.setOnClickListener {
                if (checkInputs(fromEditText, fromSpinner, toSpinner)) {
                    resultTextView.visibility = View.VISIBLE
                    resultTextView.text = convert(fromEditText, fromSpinner, toSpinner, conversion).toString()
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
                spinnerData.add("km")
                spinnerData.add("m")
                spinnerData.add("cm")
                spinnerData.add("mm")
                spinnerData.add("µm")
                spinnerData.add("nm")
                spinnerData.add("mi")
                spinnerData.add("yd")
                spinnerData.add("ft")
                spinnerData.add("in")
                spinnerData.add("nmi")

            }
            "datadim" -> {
                spinnerData.add("Bit")
                spinnerData.add("Kbit")
                spinnerData.add("Mbit")
                spinnerData.add("Gbit")
                spinnerData.add("Tbit")
                spinnerData.add("Pbit")
                spinnerData.add("Byte")
                spinnerData.add("KByte")
                spinnerData.add("MByte")
                spinnerData.add("GByte")
                spinnerData.add("TByte")
                spinnerData.add("PByte")

            }
            "velocity" -> {
                spinnerData.add("mp/h")
                spinnerData.add("ft/s")
                spinnerData.add("m/s")
                spinnerData.add("km/h")
                spinnerData.add("kn")

            }
            "temperature" -> {
                spinnerData.add("Celsius")
                spinnerData.add("Fahrenheit")
                spinnerData.add("Kelvin")

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
        val fromUnit = fromSpinner.selectedItem.toString()
        val toUnit = toSpinner.selectedItem.toString()

        val fromValue = fet.text.toString().toDouble()

        val kelvin = 273.15

        var result = 0.0

        if (conversionType == "datadim") {
            Toast.makeText(this, "WIP", Toast.LENGTH_SHORT).show()

        } else if (conversionType == "lengths") {
            Toast.makeText(this, "WIP", Toast.LENGTH_SHORT).show()

        } else if (conversionType == "velocity") {
            result = VelocityConverter().convert(fet, fromSpinner, toSpinner, conversionType)

        } else if (conversionType == "temperature") {
            result = TemperatureConverter().convert(fet, fromSpinner, toSpinner, conversionType)
        }

        return result
    }
}