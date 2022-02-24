package com.example.fiassesment

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import java.util.*
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {
    var etPan: EditText? = null
    var etMonth: EditText? = null
    var etDate: EditText? = null
    var etYear: EditText? = null
    var next: Button? = null
    var noPan: TextView? = null
    var root: LinearLayout? = null
    val myCalendar = Calendar.getInstance()
    var dayS: String? = null
    var monthS: String? = null
    var yearS: String? = null
    private var loginViewModel: MainActivityViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loginViewModel = ViewModelProviders.of(this@MainActivity).get(
            MainActivityViewModel::class.java
        )
        initView()
    }

    private fun initView() {
        etPan = findViewById(R.id.etPan) as EditText;
        etMonth = findViewById(R.id.etMonth) as EditText;
        etDate = findViewById(R.id.etDate) as EditText;
        etYear = findViewById(R.id.etYear) as EditText;
        next = findViewById(R.id.next) as Button;
        noPan = findViewById(R.id.noPan) as TextView;
        root = findViewById(R.id.root) as LinearLayout;
        root!!.setOnClickListener(View.OnClickListener { view: View? -> hideSoftKeyboard(this) })
        etPan!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                activateNext()
            }

            override fun afterTextChanged(editable: Editable) {}
        })
        val date = OnDateSetListener { view, year, month, day ->
            dayS = day.toString()
            monthS = (month + 1).toString()
            myCalendar[Calendar.YEAR] = year
            myCalendar[Calendar.MONTH] = month + 1
            myCalendar[Calendar.DAY_OF_MONTH] = day
            if (day < 10) {
                dayS = "0$dayS"
            }
            if (month < 10) {
                monthS = "0$monthS"
            }
            etDate!!.setText(dayS.toString())
            etMonth!!.setText(monthS.toString())
            etYear!!.setText(year.toString())
            activateNext()
        }
        etDate!!.setOnClickListener(View.OnClickListener { v: View? ->
            DatePickerDialog(
                this@MainActivity,
                date,
                myCalendar[Calendar.YEAR],
                myCalendar[Calendar.MONTH],
                myCalendar[Calendar.DAY_OF_MONTH]
            ).show()
        })
        etMonth!!.setOnClickListener(View.OnClickListener { v: View? ->
            DatePickerDialog(
                this@MainActivity,
                date,
                myCalendar[Calendar.YEAR],
                myCalendar[Calendar.MONTH],
                myCalendar[Calendar.DAY_OF_MONTH]
            ).show()
        })
        etYear!!.setOnClickListener(View.OnClickListener { v: View? ->
            DatePickerDialog(
                this@MainActivity,
                date,
                myCalendar[Calendar.YEAR],
                myCalendar[Calendar.MONTH],
                myCalendar[Calendar.DAY_OF_MONTH]
            ).show()
        })
        noPan!!.setOnClickListener(View.OnClickListener { view: View? -> exitDialog() })
        next!!.setOnClickListener(View.OnClickListener {
            AlertDialog.Builder(this@MainActivity)
                .setTitle("Success!")
                .setMessage("Details submitted successfully.")
                .setPositiveButton(android.R.string.yes) { dialog, which -> finish() }
                .setIcon(android.R.drawable.ic_dialog_info)
                .show()
        })
    }

    private fun exitDialog() {
        AlertDialog.Builder(this)
            .setTitle("Alert!")
            .setMessage("Cannot proceed without PAN, Do you want to Exit?")
            .setPositiveButton(android.R.string.yes) { dialog, which -> finish() }
            .setNegativeButton(android.R.string.no, null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }

    private fun activateNext() {
        val pan_pattern = "(([A-Za-z]{5})([0-9]{4})([a-zA-Z]))"
        val r = Pattern.compile(pan_pattern)
        if (!etPan!!.text.toString().isEmpty()) if (regex_matcher(
                r,
                etPan!!.text.toString()
            ) && !etDate!!.text.toString().isEmpty()
        ) {
            next!!.background = resources.getDrawable(R.drawable.button_enabled)
            next!!.isEnabled = true;
        } else {
            next!!.background = resources.getDrawable(R.drawable.button_desabled)
            next!!.isEnabled = false;
        }
    }

    private fun regex_matcher(pattern: Pattern, string: String): Boolean {
        val m = pattern.matcher(string)
        return m.find() && m.group(0) != null
    }

    companion object {
        fun hideSoftKeyboard(activity: Activity) {
            val inputMethodManager = activity.getSystemService(
                INPUT_METHOD_SERVICE
            ) as InputMethodManager
            if (inputMethodManager.isAcceptingText) {
                inputMethodManager.hideSoftInputFromWindow(
                    activity.currentFocus!!.windowToken,
                    0
                )
            }
        }
    }
}