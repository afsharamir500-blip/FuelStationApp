package com.example.fuelstation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fuelstation.databinding.ActivityReceiptBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ReceiptActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReceiptBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReceiptBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val stationNumber = intent.getStringExtra("station_number") ?: ""
        val stationName = intent.getStringExtra("station_name") ?: ""
        val liters = intent.getDoubleExtra("liters", 0.0)
        val total = intent.getLongExtra("total", 0)
        val trackingCode = intent.getStringExtra("tracking_code") ?: ""

        val dateStr = SimpleDateFormat("yyyy/MM/dd - HH:mm", Locale.getDefault()).format(Date())

        binding.tvReceiptTitle.text = "رسید پرداخت"
        binding.tvReceiptStation.text = "جایگاه: $stationName (شماره $stationNumber)"
        binding.tvReceiptLiters.text = "مقدار سوخت: $liters لیتر"
        binding.tvReceiptTotal.text = "مبلغ پرداخت‌شده: ${"%,d".format(total)} تومان"
        binding.tvReceiptTracking.text = "کد پیگیری: $trackingCode"
        binding.tvReceiptDate.text = "تاریخ: $dateStr"

        binding.btnBackHome.setOnClickListener { finish() }
    }
}
