package com.example.fuelstation.model

/**
 * موجودی کارت سوخت.
 * توجه: در نسخه واقعی این داده باید از API رسمی سامانه هوشمند سوخت خوانده شود.
 * اینجا فقط داده نمونه (Mock) برای نمایش ساختار برنامه قرار داده شده است.
 */
data class FuelCard(
    val cardNumber: String,
    val remainingLiters: Double,
    val quotaType: String // مثلاً "سهمیه آزاد" یا "سهمیه ماهانه"
)
