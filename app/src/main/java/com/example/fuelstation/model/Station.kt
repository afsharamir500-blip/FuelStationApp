package com.example.fuelstation.model

/**
 * مدل داده‌ی یک جایگاه سوخت.
 * pricePerLiter بر حسب تومان است.
 */
data class Station(
    val stationNumber: String,   // شماره جایگاه (مثلاً: 12345)
    val name: String,            // نام جایگاه
    val address: String,         // آدرس / منطقه
    val latitude: Double,
    val longitude: Double,
    val pricePerLiterToman: Long, // قیمت هر لیتر به تومان
    val fuelType: String          // نوع سوخت: بنزین سوپر / معمولی / گازوئیل
)
