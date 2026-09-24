package com.example.fuelstation.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fuelstation.databinding.ActivityPaymentBinding
import com.example.fuelstation.model.StationRepository
import java.util.UUID

class PaymentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaymentBinding
    private var currentPricePerLiter: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val stationNumber = intent.getStringExtra("station_number") ?: ""
        val station = StationRepository.getStationByNumber(stationNumber)

        if (station == null) {
            Toast.makeText(this, "جایگاه پیدا نشد", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        binding.tvStationInfo.text = "جایگاه: ${station.name} (شماره ${station.stationNumber})"

        // پمپ‌ها
        val pumpNumbers = (1..station.pumpCount).map { "پمپ شماره $it" }
        binding.spPump.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, pumpNumbers)

        // نوع سوخت (نازل)
        val fuelTypes = station.fuelPrices.keys.toList()
        binding.spFuelType.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, fuelTypes)

        currentPricePerLiter = station.fuelPrices[fuelTypes.first()] ?: 0
        updatePriceLabel()

        binding.spFuelType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                val selectedFuel = fuelTypes[position]
                currentPricePerLiter = station.fuelPrices[selectedFuel] ?: 0
                updatePriceLabel()
                recalcTotal()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.etLiters.addTextChangedListener { recalcTotal() }

        binding.btnConfirmPay.setOnClickListener {
            val liters = binding.etLiters.text.toString().toDoubleOrNull()
            if (liters == null || liters <= 0) {
                Toast.makeText(this, "لطفاً مقدار لیتر را صحیح وارد کنید", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val total = (liters * currentPricePerLiter).toLong()
            val pumpLabel = pumpNumbers[binding.spPump.selectedItemPosition]
            val fuelLabel = fuelTypes[binding.spFuelType.selectedItemPosition]
            startPaymentGateway(station.stationNumber, station.name, liters, total, pumpLabel, fuelLabel)
        }
    }

    private fun updatePriceLabel() {
        binding.tvPricePerLiter.text = "قیمت هر لیتر: ${"%,d".format(currentPricePerLiter)} تومان"
    }

    private fun recalcTotal() {
        val liters = binding.etLiters.text.toString().toDoubleOrNull() ?: 0.0
        val total = (liters * currentPricePerLiter).toLong()
        binding.tvTotal.text = "مبلغ قابل پرداخت: ${"%,d".format(total)} تومان"
    }

    private fun startPaymentGateway(
        stationNumber: String, stationName: String, liters: Double, total: Long,
        pumpLabel: String, fuelLabel: String
    ) {
        val trackingCode = UUID.randomUUID().toString().take(10).uppercase()
        val intent = Intent(this, ReceiptActivity::class.java)
        intent.putExtra("station_number", stationNumber)
        intent.putExtra("station_name", stationName)
        intent.putExtra("liters", liters)
        intent.putExtra("total", total)
        intent.putExtra("tracking_code", trackingCode)
        intent.putExtra("pump_label", pumpLabel)
        intent.putExtra("fuel_label", fuelLabel)
        startActivity(intent)
        finish()
    }
}

private inline fun android.widget.EditText.addTextChangedListener(crossinline onChanged: () -> Unit) {
    this.addTextChangedListener(object : android.text.TextWatcher {
        override fun afterTextChanged(s: android.text.Editable?) = onChanged()
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}
