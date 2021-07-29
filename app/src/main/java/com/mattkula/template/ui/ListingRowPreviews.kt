package com.mattkula.template.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mattkula.template.data.remote.Listing
import com.mattkula.template.data.remote.Sparkline

private object Mocks {
    val LISTING = Listing(
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
}

@Composable
@Preview(backgroundColor = 0xffffff, showBackground = true)
private fun ListingRowPreview() {
    Column(Modifier.padding(horizontal = 24.dp, vertical = 12.dp)) {
        ListingRowContent(listing = Mocks.LISTING)
        ListingRowExtras(listing = Mocks.LISTING)
    }
}
