package com.mattkula.template.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.mattkula.template.data.remote.Listing
import java.text.NumberFormat
import java.util.*

private val currencyFormat = NumberFormat.getCurrencyInstance().apply {
    currency = Currency.getInstance("USD")
}

@OptIn(
    ExperimentalAnimationApi::class,
    ExperimentalFoundationApi::class,
)
@Composable
fun ListingRow(
    listing: Listing,
) {
    val isExpanded = rememberSaveable { mutableStateOf(false) }

    Column(
        Modifier
            .combinedClickable(
                interactionSource = remember { MutableInteractionSource() },
                onLongClick = { isExpanded.value = !isExpanded.value },
                indication = rememberRipple(color = Color.DarkGray),
            ) { /* do nothing on click */ }
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 12.dp)
    ) {
        ListingRowContent(listing = listing)
        AnimatedVisibility(visible = isExpanded.value) {
            ListingRowExtras(listing = listing)
        }
    }
}
@Composable
fun ListingRowContent(
    listing: Listing,
) {
    var showPercentage by rememberSaveable { mutableStateOf(true) }

    Row {
        Image(
            painter = rememberImagePainter(data = listing.image),
            contentDescription = null,
            modifier = Modifier
                .size(36.dp)
                .align(Alignment.CenterVertically)
        )
        Column(
            Modifier
                .weight(1f)
                .padding(start = 16.dp)
        ) {
            Text(
                text = listing.symbol.toUpperCase(Locale.current),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )
            Text(
                text = listing.name,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
        Column(
            Modifier
                .align(Alignment.CenterVertically)
                .padding(start = 8.dp)
                .clickable { showPercentage = !showPercentage }
        ) {
            Text(
                text = currencyFormat.format(listing.currentPrice),
                fontSize = 16.sp,
            )
            Text(
                text = when {
                    showPercentage ->"%.2f".format(listing.percentChange24h) + "%"
                    else -> currencyFormat.format(listing.priceChange24h)
                },
                fontSize = 14.sp,
                color = when {
                    listing.percentChange24h >= 0 -> Color.Green
                    else -> Color.Red
                },
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}

@Composable
fun ListingRowExtras(
    listing: Listing,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "High 24: " + currencyFormat.format(listing.high24h),
            fontSize = 12.sp,
        )
        Text(
            text = "Low 24: " + currencyFormat.format(listing.low24h),
            fontSize = 12.sp,
        )
        val prices = listing.sparkline7d?.price?.takeLast(24) ?: emptyList()
        if (prices.isNotEmpty()) {
            LineChart(
                points = prices,
                strokeColor = when {
                    prices.first() > prices.last() -> Color.Red
                    else -> Color.Green
                },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .height(48.dp)
            )
        }
    }
}
