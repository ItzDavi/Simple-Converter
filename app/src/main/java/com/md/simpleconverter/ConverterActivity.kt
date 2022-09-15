package com.md.simpleconverter

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.text.method.DigitsKeyListener
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.lifecycleScope
import com.google.android.material.card.MaterialCardView
import com.md.simpleconverter.converters.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.round

class ConverterActivity : AppCompatActivity() {
    private lateinit var spinnerData: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_converter)

        val conversion = intent.getStringExtra("conversion")
        val conversionTextView = findViewById<TextView>(R.id.converter_textview)
        val convertButton = findViewById<Button>(R.id.convert_button)

        val tapToCopyTextView = findViewById<TextView>(R.id.tap_to_copy_textview)

        val resultTextView = findViewById<TextView>(R.id.conversion_result_textview)
        val resultCardView = findViewById<MaterialCardView>(R.id.result_cardview)

        val titleConstraintLayout = findViewById<ConstraintLayout>(R.id.title_constraint_layout)
        val fromCardView = findViewById<MaterialCardView>(R.id.from_cardview_layout)
        val toCardView = findViewById<MaterialCardView>(R.id.to_cardview_layout)

        val fromSpinner = findViewById<Spinner>(R.id.from_spinner)
        val toSpinner = findViewById<Spinner>(R.id.to_spinner)

        val sharedPreferences = getSharedPreferences("com.md.simpleconverter", MODE_PRIVATE)

        val fromEditText = findViewById<EditText>(R.id.from_edittext)

        val rootLayout = findViewById<ConstraintLayout>(R.id.root_layout)
        rootLayout.setOnClickListener {
            Utils().removeFocus(fromEditText, baseContext)
        }

        val backButton = findViewById<ImageView>(R.id.back_button_imageview)
        backButton.setOnClickListener {
            finish()
        }

        initEditText(fromEditText)

        spinnerData = ArrayList()

        if (conversion != null) {
            start(conversion, conversionTextView, fromSpinner, toSpinner)

            changeRandomColors(titleConstraintLayout, fromCardView, toCardView, fromSpinner, toSpinner, conversion, sharedPreferences)

            convertButton.setOnClickListener {
                if (checkInputs(fromEditText, fromSpinner, toSpinner)) {
                    resultCardView.visibility = View.VISIBLE
                    tapToCopyTextView.visibility = View.VISIBLE

                    "Tap to copy".also { tapToCopyTextView.text = it }

                    when (conversion) {
                        "time" -> {
                            (fromEditText.text.toString() + " " + fromSpinner.selectedItem.toString() + "   =   " + convert(fromEditText, fromSpinner, toSpinner, conversion).round(14).toString() + " " + toSpinner.selectedItem.toString())
                                .also { resultTextView.text = it }
                        }
                        else -> {
                            (fromEditText.text.toString() + " " + fromSpinner.selectedItem.toString() + "   =   " + convert(fromEditText, fromSpinner, toSpinner, conversion).round(12).toString() + " " + toSpinner.selectedItem.toString())
                                .also { resultTextView.text = it }
                        }
                    }
                }
            }

        } else {
            Toast.makeText(this, "Failed getting intents from Main Activity", Toast.LENGTH_SHORT).show()
        }

        resultCardView.setOnClickListener {
            copyTextToClipboard(resultTextView)

            lifecycleScope.launch {
                "Copied!".also { tapToCopyTextView.text = it }
                tapToCopyTextView.setTypeface(null, Typeface.BOLD_ITALIC)

                delay(2000)

                "Tap to copy".also { tapToCopyTextView.text = it }
                tapToCopyTextView.setTypeface(null, Typeface.ITALIC)
            }
        }
    }

    private fun copyTextToClipboard(text: TextView) {
        val textToCopy = text.text

        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("text", textToCopy)

        clipboardManager.setPrimaryClip(clipData)
    }

    private fun changeRandomColors(layout: ConstraintLayout, fromCardView: MaterialCardView, toCardView: MaterialCardView, fromSpinner: Spinner, toSpinner: Spinner, conversion: String, sharedPref: SharedPreferences) {
        val rootLayout = findViewById<ConstraintLayout>(R.id.root_layout)

        val randomTheme = sharedPref.getBoolean("random_theme", true)
        val themeColor = sharedPref.getInt("theme_color", 0)

        val colors = arrayOf(
            getColor(R.color.yellow_folder_icon),
            getColor(R.color.blue),
            getColor(R.color.red_tape_icon),
            getColor(R.color.purple))

        window.clearFlags(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)

        if (randomTheme) {
            val randomColor = colors.random()

            rootLayout.setBackgroundColor(getColor(R.color.soft_white))

            window.statusBarColor = randomColor
            layout.background.setTint(randomColor)

            fromCardView.setCardBackgroundColor(randomColor)
            toCardView.setCardBackgroundColor(randomColor)

            fromSpinner.popupBackground.setTint(randomColor)
            toSpinner.popupBackground.setTint(randomColor)

        } else if (!randomTheme) {
            val color = colors[themeColor]

            rootLayout.setBackgroundColor(getColor(R.color.soft_white))

            window.statusBarColor = color
            layout.background.setTint(color)

            fromCardView.setCardBackgroundColor(color)
            toCardView.setCardBackgroundColor(color)

            fromSpinner.popupBackground.setTint(color)
            toSpinner.popupBackground.setTint(color)
        }
    }

    private fun start(conversion: String, conversionTV: TextView, fromSpinner: Spinner, toSpinner: Spinner) {
        when (conversion) {
            "datadim" -> {
                "Data Dimension".also { conversionTV.text = it }
                loadSpinnerArray("datadim")
                initSpinners(fromSpinner, toSpinner)
            }

            "lengths" -> {
                "Lengths".also { conversionTV.text = it }
                loadSpinnerArray("lengths")
                initSpinners(fromSpinner, toSpinner)
            }

            "velocity" -> {
                "Velocity".also { conversionTV.text = it }
                loadSpinnerArray("velocity")
                initSpinners(fromSpinner, toSpinner)
            }

            "temperature" -> {
                "Temperature".also { conversionTV.text = it }
                loadSpinnerArray("temperature")
                initSpinners(fromSpinner, toSpinner)
            }

            "mass" -> {
                "Mass".also { conversionTV.text = it }
                loadSpinnerArray("mass")
                initSpinners(fromSpinner, toSpinner)
            }

            "time" -> {
                "Time".also { conversionTV.text = it }
                loadSpinnerArray("time")
                initSpinners(fromSpinner, toSpinner)
            }

            "frequency" -> {
                "Frequency".also { conversionTV.text = it }
                loadSpinnerArray("frequency")
                initSpinners(fromSpinner, toSpinner)
            }

            "consumption" -> {
                "Consumption".also { conversionTV.text = it }
                loadSpinnerArray("consumption")
                initSpinners(fromSpinner, toSpinner)
            }
        }
    }

    private fun initEditText(fet: EditText) {
        fet.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL or InputType.TYPE_NUMBER_FLAG_SIGNED
        fet.keyListener = DigitsKeyListener.getInstance("0123456789.")
    }

    private fun initSpinners(fs: Spinner, ts: Spinner) {
        val adapter = ArrayAdapter(this, R.layout.custom_spinner_layout, spinnerData)
        fs.adapter = adapter
        ts.adapter = adapter

        fs.setSelection(0)
        ts.setSelection(1)
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
                spinnerData.addAll(listOf("tons", "quints", "Kg", "hg", "dag", "g", "dg", "cg", "mg", "lb", "oz"))
            }

            "time" -> {
                spinnerData.addAll(listOf("Centuries", "Decades", "Years", "Months", "Days", "Hours", "Minutes", "Seconds", "Milliseconds"))
            }

            "frequency" -> {
                spinnerData.addAll(listOf("Hertz", "Kilohertz", "Megahertz", "Gigahertz"))
            }

            "consumption" -> {
                spinnerData.addAll(listOf("US mpg", "Imp mpg", "km/L", "L/100km"))
            }
        }
    }

    private fun checkInputs(fet: EditText, fromSpinner: Spinner, toSpinner: Spinner) : Boolean {
        var flag = false

        if (TextUtils.isEmpty(fet.text)) {
            Toast.makeText(this, "The value to convert is missing", Toast.LENGTH_SHORT).show()

        } else if (fromSpinner.selectedItem.toString() == toSpinner.selectedItem.toString()) {
            Toast.makeText(this, "Why would you convert to the same measure unit ?", Toast.LENGTH_SHORT).show()

        } else {
            if (TextUtils.isDigitsOnly(fet.text)) {
                if (fet.text.toString().toDouble() > 0) {
                    flag = true
                }

            } else {
                Toast.makeText(this, "Too many dots in this number", Toast.LENGTH_SHORT).show()
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

            "frequency" -> {
                result = FrequencyConverter().convert(fet, fromSpinner, toSpinner)
            }

            "consumption" -> {
                result = ConsumptionConverter().convert(fet, fromSpinner, toSpinner)
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