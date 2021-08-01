package com.mattkula.template.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.mattkula.template.ui.detail.DetailScreen
import com.mattkula.template.ui.home.HomeScreen

internal sealed class PrimaryNav(val route: String) {
    object Home : PrimaryNav("homeroot")
    object Portfolio : PrimaryNav("portfolioroot")
}

internal sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Details : Screen("details/{cryptoId}") {
        fun createRoute(cryptoId: String) = "details/$cryptoId"
    }

    object Portfolio : Screen("portfolio")
}

@Composable
internal fun AppNavigation(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = PrimaryNav.Home.route
    ) {
        addHomeTopLevel(navController)
        addPortfolioTopLevel(navController)
    }
}


private fun NavGraphBuilder.addHomeTopLevel(
    navController: NavController,
) {
    navigation(
        route = PrimaryNav.Home.route,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                navigateToDetail = { cryptoId ->
                    navController.navigate(Screen.Details.createRoute(cryptoId))
                }
            )
        }
        composable(Screen.Details.route) { DetailScreen(it.arguments?.getString("cryptoId").orEmpty()) }
    }
}

private fun NavGraphBuilder.addPortfolioTopLevel(
    navController: NavController,
) {
    navigation(
        route = PrimaryNav.Portfolio.route,
        startDestination = Screen.Portfolio.route
    ) {
        composable(Screen.Portfolio.route) { DetailScreen("bitcoin") }
    }
}
