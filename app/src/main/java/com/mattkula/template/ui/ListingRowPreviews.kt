package com.mattkula.template.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mattkula.template.data.remote.CryptoDetail
import com.mattkula.template.data.remote.Listing
import com.mattkula.template.data.remote.Sparkline

object SampleModels {
    val LISTING = Listing(
        id = "bitcoin",
        symbol = "BTC",
        name = "Bitcoin",
        image = "https://assets.coingecko.com/coins/images/279/large/ethereum.png?1595348880",
        priceChange24h = 1234.12f,
        currentPrice = 1234.12f,
        percentChange7d = 25.3f,
        percentChange24h = 25.3f,
        high24h = 1234f,
        low24h = 1134f,
        sparkline7d = Sparkline(
            price = listOf(1f, 2f, -10f, 12f)
        )
    )

    val CRYPTO_DETAIL = CryptoDetail(
        id = "bitcoin",
        name = "Bitcoin",
        symbol = "BTC",
        marketData = CryptoDetail.MarketData(
            currentPrice = CryptoDetail.MarketData.ByCurrency(
                usd = 1234.56f
            ),
            sparkline7d = Sparkline(price = listOf(1f, 2f, -10f, 12f)),
        ),
    )
}

@Composable
@Preview(backgroundColor = 0xffffff, showBackground = true)
private fun ListingRowPreview() {
    Column(Modifier.padding(horizontal = 24.dp, vertical = 12.dp)) {
        ListingRowContent(listing = SampleModels.LISTING)
        ListingRowExtras(listing = SampleModels.LISTING)
    }
}
