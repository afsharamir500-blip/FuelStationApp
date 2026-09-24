package com.example.fuelstation.model

/**
 * مدل داده‌ی یک جایگاه سوخت.
 * fuelPrices: نگاشت نوع سوخت به قیمت هر لیتر (تومان)
 * pumpCount: تعداد پمپ‌های موجود در جایگاه
 */
data class Station(
    val stationNumber: String,
    val name: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val pumpCount: Int,
    val fuelPrices: Map<String, Long>
)
