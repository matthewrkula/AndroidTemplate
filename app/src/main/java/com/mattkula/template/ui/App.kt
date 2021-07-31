package com.mattkula.template.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.mattkula.template.R
import com.mattkula.template.ui.core.controller
import com.mattkula.template.ui.home.HomeController

@Composable
fun App() {
    Home()
}

@Composable
fun Home() {
    val state = controller<HomeController>().stateFlow.collectAsState()

    Column(
        modifier = Modifier.background(Color.White)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.app_name),
                    fontWeight = FontWeight.Bold,
                )
            },
        )
        HomeContent(
            state = state.value,
        )
    }
}

@Composable
fun HomeContent(
    state: HomeController.State,
) {
    val controller = controller<HomeController>()

    SwipeRefresh(
        state = rememberSwipeRefreshState(state.isRefreshing),
        onRefresh = { controller.refreshData() }
    ) {
        LazyColumn(Modifier.fillMaxSize()) {
            itemsIndexed(state.listings) { index, listing ->
                ListingRow(listing = listing)
                if (index != state.listings.indices.last) {
                    Divider(
                        color = Color.LightGray,
                        modifier = Modifier.padding(horizontal = 24.dp)
                    )
                }
            }
        }
    }
}
