package com.mattkula.template.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.mattkula.template.R
import com.mattkula.template.ui.ListingRow
import com.mattkula.template.ui.core.controller

@Composable
fun Home() {
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
        HomeContent()
    }
}

@Composable
fun HomeContent() {
    val controller = controller<HomeController>()
    val state = controller.stateFlow.collectAsState()

    SwipeRefresh(
        state = rememberSwipeRefreshState(state.value.isRefreshing),
        onRefresh = { controller.refreshData() }
    ) {
        LazyColumn(Modifier.fillMaxSize()) {
            itemsIndexed(state.value.listings) { index, listing ->
                ListingRow(listing = listing)
                if (index != state.value.listings.indices.last) {
                    Divider(
                        color = Color.LightGray,
                        modifier = Modifier.padding(horizontal = 24.dp)
                    )
                }
            }
        }
    }
}
