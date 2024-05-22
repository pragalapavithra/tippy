package com.example.tippy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView

private const val TAG ="MainActivity"
private const val INITIAL_TIP_PERCENTAGE =15
class MainActivity : AppCompatActivity() {
    private lateinit var etBaseAmount: EditText
    private lateinit var etTipPercentage: TextView
    private lateinit var etTipAmount: TextView
    private lateinit var etTotalAmount: TextView
    private lateinit var seekBarTip: SeekBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etBaseAmount = findViewById(R.id.etBaseAmount)
        etTipPercentage = findViewById(R.id.tvTipPercent)
        etTipAmount = findViewById(R.id.tipAmount)
        etTotalAmount = findViewById(R.id.tvTotalAmount)
        seekBarTip = findViewById(R.id.seekBar)
        seekBarTip.progress = INITIAL_TIP_PERCENTAGE
        "$INITIAL_TIP_PERCENTAGE%".also {
            etTipPercentage.text = it
        }
        seekBarTip.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.i(TAG,"$progress")
                "$progress%".also { etTipPercentage.text = it }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })
    }
}