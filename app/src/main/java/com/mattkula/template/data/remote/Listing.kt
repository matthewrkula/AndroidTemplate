package com.mattkula.template.data.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Listing(
    val name: String,
    val symbol: String,
    val image: String,
    @Json(name = "current_price") val currentPrice: Float,
    @Json(name = "high_24h") val high24h: Float? = null,
    @Json(name = "low_24h") val low24h: Float?,
    @Json(name = "price_change_24h") val priceChange24h: Float,
    @Json(name = "price_change_percentage_24h_in_currency") val percentChange24h: Float,
    @Json(name = "price_change_percentage_7d_in_currency") val percentChange7d: Float,
    @Json(name = "sparkline_in_7d") val sparkline7d: Sparkline? = null,
)
@JsonClass(generateAdapter = true)
data class Sparkline(
    val price: List<Float>,
)
