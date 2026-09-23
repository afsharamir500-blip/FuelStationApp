package com.example.fuelstation.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fuelstation.databinding.ActivityPaymentBinding
import java.util.UUID

class PaymentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaymentBinding
    private var pricePerLiter: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val stationNumber = intent.getStringExtra("station_number") ?: ""
        val stationName = intent.getStringExtra("station_name") ?: ""
        pricePerLiter = intent.getLongExtra("price_per_liter", 0)

        binding.tvStationInfo.text = "جایگاه: $stationName (شماره $stationNumber)"
        binding.tvPricePerLiter.text = "قیمت هر لیتر: ${"%,d".format(pricePerLiter)} تومان"

        binding.etLiters.addTextChangedListener {
            val liters = binding.etLiters.text.toString().toDoubleOrNull() ?: 0.0
            val total = (liters * pricePerLiter).toLong()
            binding.tvTotal.text = "مبلغ قابل پرداخت: ${"%,d".format(total)} تومان"
        }

        binding.btnConfirmPay.setOnClickListener {
            val liters = binding.etLiters.text.toString().toDoubleOrNull()
            if (liters == null || liters <= 0) {
                Toast.makeText(this, "لطفاً مقدار لیتر را صحیح وارد کنید", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val total = (liters * pricePerLiter).toLong()
            startPaymentGateway(stationNumber, stationName, liters, total)
        }
    }

    /**
     * TODO: این تابع باید به درگاه پرداخت واقعی (مثلاً زرین‌پال / آی‌دی‌پی) وصل شود:
     * 1) درخواست ایجاد تراکنش با Merchant ID خود به سرور PSP بفرستید.
     * 2) کاربر را به صفحه پرداخت درگاه (WebView یا مرورگر) هدایت کنید.
     * 3) پس از بازگشت، نتیجه تراکنش (کد پیگیری) را از سرور خودتان verify کنید.
     * فعلاً برای نمایش ساختار برنامه، پرداخت به‌صورت شبیه‌سازی‌شده انجام و رسید تولید می‌شود.
     */
    private fun startPaymentGateway(stationNumber: String, stationName: String, liters: Double, total: Long) {
        val trackingCode = UUID.randomUUID().toString().take(10).uppercase()

        val intent = Intent(this, ReceiptActivity::class.java)
        intent.putExtra("station_number", stationNumber)
        intent.putExtra("station_name", stationName)
        intent.putExtra("liters", liters)
        intent.putExtra("total", total)
        intent.putExtra("tracking_code", trackingCode)
        startActivity(intent)
        finish()
    }
}

// Extension برای ساده‌سازی TextWatcher
private inline fun android.widget.EditText.addTextChangedListener(crossinline onChanged: () -> Unit) {
    this.addTextChangedListener(object : android.text.TextWatcher {
        override fun afterTextChanged(s: android.text.Editable?) = onChanged()
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}
