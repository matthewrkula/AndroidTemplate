package com.mattkula.template.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.mattkula.template.data.remote.CryptoDetail
import com.mattkula.template.ui.LineChart
import com.mattkula.template.ui.SampleModels
import com.mattkula.template.ui.core.controller
import com.mattkula.template.ui.currencyFormat

class DetailControllerFactory(
    private val cryptoId: String
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(
        modelClass: Class<T>
    ): T = DetailController(cryptoId) as T
}

@Composable
fun DetailScreen(id: String) {
    val controller = controller<DetailController>(factory = DetailControllerFactory(id))
    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        DetailContent(controller)
    }
}

@Composable
fun DetailContent(controller: DetailController) {
    val state = controller.stateFlow.collectAsState()

    SwipeRefresh(
        state = rememberSwipeRefreshState(state.value.isRefreshing),
        onRefresh = { controller.loadData() }
    ) {
        Column(
            Modifier
                .scrollable(rememberScrollState(), Orientation.Vertical)
                .fillMaxSize()
        ) {
            state.value.crypto?.let {
                CryptoDetailsDisplay(crypto = it)
            }
        }
    }
}

@Composable
fun CryptoDetailsDisplay(crypto: CryptoDetail) {
    Column(Modifier.fillMaxSize()) {
        Text(
            text = crypto.name,
            fontSize = 48.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .padding(top = 20.dp, start = 20.dp)
                .align(Alignment.Start)
        )
        Text(
            text = currencyFormat.format(crypto.marketData.currentPrice.usd),
            fontSize = 40.sp,
            fontWeight = FontWeight.Thin,
            modifier = Modifier.padding(start = 20.dp)
        )
        val prices = crypto.marketData.sparkline7d?.price?.takeLast(24) ?: emptyList()
        LineChart(
            points = prices,
            strokeColor = when {
                prices.first() > prices.last() -> Color.Red
                else -> Color.Green
            },
            modifier = Modifier.fillMaxWidth()
                .height(140.dp)
                .padding(top = 12.dp)
        )
    }
}

@Composable
@Preview
fun CryptoDetailsDisplayPreview() {
    CryptoDetailsDisplay(crypto = SampleModels.CRYPTO_DETAIL)
}
