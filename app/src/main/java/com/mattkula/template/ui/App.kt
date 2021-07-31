package com.mattkula.template.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mattkula.template.ui.detail.Details
import com.mattkula.template.ui.home.Home

@Composable
fun App() {
    val navController = rememberNavController()
    val navigator = remember { NavigatorImpl(navController) }

    CompositionLocalProvider(
        LocalNavigator provides navigator,
    ) {
        NavHost(navController = navController, startDestination = "home") {
            composable("home") { Home() }
            composable("details/{cryptoId}") {
                Details(it.arguments?.getString("cryptoId").orEmpty())
            }
        }
    }
}
