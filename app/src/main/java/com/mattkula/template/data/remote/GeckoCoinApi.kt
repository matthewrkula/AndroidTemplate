package com.mattkula.template.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GeckoCoinApi {
    @GET("coins/markets")
    suspend fun getListing(
        @Query("vs_currency") currency: String = "usd",
        @Query("order") order: String = "market_cap_desc",
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 25,
        @Query("sparkline") sparkline: Boolean = true,
        @Query("price_change_percentage") priceChangePct: String = "1h,24h,7d",
    ): List<Listing>

    @GET("coins/{id}")
    suspend fun getDetail(
        @Path("id") id: String = "bitcoin",
        @Query("sparkline") sparkline: Boolean = true,
    ): CryptoDetail
}
