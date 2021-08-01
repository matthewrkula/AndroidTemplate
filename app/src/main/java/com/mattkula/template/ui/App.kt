package com.mattkula.template.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mattkula.template.ui.detail.DetailScreen
import com.mattkula.template.ui.home.HomeScreen

@Composable
fun App() {
    val navController = rememberNavController()
    val navigator = remember { NavigatorImpl(navController) }

    CompositionLocalProvider(
        LocalNavigator provides navigator,
    ) {
        NavHost(navController = navController, startDestination = "home") {
            composable("home") { HomeScreen() }
            composable("details/{cryptoId}") {
                DetailScreen(it.arguments?.getString("cryptoId").orEmpty())
            }
        }
    }
}
