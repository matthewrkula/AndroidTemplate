package com.mattkula.template.ui

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavController

interface Navigator {
    fun navigateToDetailScreen(id: String)
}

class NavigatorImpl(
    private val navController: NavController
) : Navigator {
    override fun navigateToDetailScreen(cryptoId: String) {
        navController.navigate("details/$cryptoId")
    }
}

val LocalNavigator = staticCompositionLocalOf<Navigator> { error("Nothing") }
