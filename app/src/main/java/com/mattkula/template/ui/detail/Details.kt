package com.mattkula.template.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.mattkula.template.ui.core.controller

class DetailControllerFactory(
    private val cryptoId: String
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(
        modelClass: Class<T>
    ): T = DetailController(cryptoId) as T
}

@Composable
fun Details(id: String) {
    val controller = controller<DetailController>(factory = DetailControllerFactory(id))
    val state = controller.stateFlow.collectAsState()

    val crypto = state.value.crypto
    Column(modifier = Modifier.background(Color.White)) {
        TopAppBar(
            title = {
                Text(
                    text = crypto?.name.orEmpty(),
                    fontWeight = FontWeight.Bold,
                )
            },
        )
        SwipeRefresh(
            state = rememberSwipeRefreshState(state.value.isRefreshing),
            onRefresh = { controller.loadData() }
        ) {
            Column(
                Modifier.scrollable(rememberScrollState(), Orientation.Vertical)
                    .fillMaxSize()
            ) {
                if (crypto != null) {
                    Text(crypto.id)
                }
            }
        }
    }
}
