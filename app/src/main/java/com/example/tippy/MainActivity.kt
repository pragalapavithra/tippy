package com.example.tippy

import android.animation.ArgbEvaluator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.content.ContextCompat

private const val TAG = "MainActivity"
private const val INITIAL_TIP_PERCENTAGE = 15

class MainActivity : AppCompatActivity() {
    private lateinit var etBaseAmount: EditText
    private lateinit var etTipPercentage: TextView
    private lateinit var etTipAmount: TextView
    private lateinit var etTotalAmount: TextView
    private lateinit var seekBarTip: SeekBar
    private lateinit var tvTipDescription: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etBaseAmount = findViewById(R.id.etBaseAmount)
        etTipPercentage = findViewById(R.id.tvTipPercent)
        etTipAmount = findViewById(R.id.tipAmount)
        etTotalAmount = findViewById(R.id.totalAmount)
        seekBarTip = findViewById(R.id.seekBar)
        seekBarTip.progress = INITIAL_TIP_PERCENTAGE
        tvTipDescription = findViewById(R.id.tvTipDescription)
        "$INITIAL_TIP_PERCENTAGE%".also {
            etTipPercentage.text = it
        }
        updateTipDescription(INITIAL_TIP_PERCENTAGE)
        seekBarTip.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.i(TAG, "$progress")
                "$progress%".also { etTipPercentage.text = it }
                computeTipAndTotal()
                updateTipDescription(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })

        etBaseAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                Log.i(TAG, "$s")
                computeTipAndTotal()
            }

        })
    }

    private fun updateTipDescription(tipPercent : Int) {
        val tipDescription = when(tipPercent){
            in 0..9 -> "Poor"
            in 9..14 -> "Acceptable"
            in 15..19 -> "Good"
            in 20..24 -> "Great"
            else -> "Amazing"
        }
        tvTipDescription.text = tipDescription

        val color = ArgbEvaluator().evaluate(
            tipPercent.toFloat() /seekBarTip.max,
            ContextCompat.getColor(this, R.color.worst_tip_color),
            ContextCompat.getColor(this, R.color.best_tip_color),
        ) as Int
        tvTipDescription.setTextColor(color)
    }

    private fun computeTipAndTotal() {
        if(etBaseAmount.text.isEmpty()){
            etTipAmount.text = ""
            etTotalAmount.text = ""
            return
        }
        //1.get the tip and base amount
        val tipPercentage = seekBarTip.progress
        val baseAmountText = etBaseAmount.text.toString()
        val baseAmount =
            if (baseAmountText.isEmpty()) 0.0 else baseAmountText
                .toDouble()

        //2.calculate tip amount and totalamount
        val tipAmount = baseAmount * tipPercentage / 100
        val totalAmount = tipAmount + baseAmount
        //3.Update UI
        "%.2f".format(tipAmount).also { etTipAmount.text = it }
        "%.2f".format(totalAmount).also { etTotalAmount.text =it }
    }
}