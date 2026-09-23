package com.example.fuelstation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fuelstation.databinding.ActivityFuelcardBinding
import com.example.fuelstation.model.StationRepository

/**
 * TODO: در نسخه واقعی، داده کارت باید با فراخوانی API رسمی سامانه هوشمند سوخت
 * (پس از احراز هویت کاربر با کد ملی / شماره کارت سوخت) دریافت شود.
 * فعلاً از داده نمونه استفاده می‌شود.
 */
class FuelCardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFuelcardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFuelcardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val card = StationRepository.getFuelCard()

        binding.tvCardNumber.text = "شماره کارت سوخت: ${card.cardNumber}"
        binding.tvRemainingLiters.text = "موجودی سهمیه: ${card.remainingLiters} لیتر"
        binding.tvQuotaType.text = "نوع سهمیه: ${card.quotaType}"
    }
}
