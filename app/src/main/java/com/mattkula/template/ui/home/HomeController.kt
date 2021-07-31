package com.mattkula.template.ui.home

import com.mattkula.template.Graph
import com.mattkula.template.data.remote.GeckoCoinApi
import com.mattkula.template.data.remote.Listing
import com.mattkula.template.ui.core.Controller
import com.mattkula.template.ui.core.ControllerState
import kotlinx.coroutines.launch

class HomeController(
    private val api: GeckoCoinApi = Graph.api
) : Controller<HomeController.State>() {

    data class State(
        val listings: List<Listing> = emptyList(),
        val isRefreshing: Boolean = true,
    ) : ControllerState

    override val initialState by lazy { State() }

    init {
        refreshData()
    }

    fun refreshData() {
        controllerScope.launch {
            updateState { copy(isRefreshing = true) }

            val response = api.getListing()

            updateState {
                copy(
                    listings = response,
                    isRefreshing = false
                )
            }
        }
    }
}
