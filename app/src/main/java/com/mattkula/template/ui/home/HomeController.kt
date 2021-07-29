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
        val listings: List<Listing> = emptyList()
    ) : ControllerState

    override val initialState by lazy { State() }

    init {
        controllerScope.launch {
            val response = api.getListing()
            updateState {
                copy(listings = response)
            }
        }
    }
}
