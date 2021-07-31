package com.mattkula.template.ui.detail

import com.mattkula.template.Graph
import com.mattkula.template.data.remote.CryptoDetail
import com.mattkula.template.data.remote.GeckoCoinApi
import com.mattkula.template.data.remote.Listing
import com.mattkula.template.ui.core.Controller
import com.mattkula.template.ui.core.ControllerState
import kotlinx.coroutines.launch

class DetailController(
    private val cryptoId: String,
    private val api: GeckoCoinApi = Graph.api
) : Controller<DetailController.State>() {

    data class State(
        val crypto: CryptoDetail? = null,
        val isRefreshing: Boolean = true,
    ) : ControllerState

    override val initialState by lazy { State() }

    init {
        loadData()
    }

    fun loadData() {
        controllerScope.launch {
            updateState { copy(isRefreshing = true) }

            val response = api.getDetail(cryptoId)

            updateState {
                copy(
                    crypto = response,
                    isRefreshing = false
                )
            }
        }
    }
}
