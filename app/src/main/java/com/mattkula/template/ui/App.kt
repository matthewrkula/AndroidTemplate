package com.mattkula.template.ui

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.mattkula.template.ui.theme.NeonGreen

@Composable
fun App() {
    val navController = rememberNavController()

    Scaffold(
        content = { paddingValues ->
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                AppNavigation(navController = navController)
            }
        },
        bottomBar = {
            val currentSelectedItem by navController.currentNavAsState()

            HomeBottomNavigation(
                selectedNavigation = currentSelectedItem,
                onNavigationSelected = { selected ->
                    navController.navigate(selected.route) {
                        launchSingleTop = true
                        restoreState = true

                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    )
}

@Composable
private fun NavController.currentNavAsState(): State<PrimaryNav> {
    val selectedItem = remember { mutableStateOf<PrimaryNav>(PrimaryNav.Home) }

    DisposableEffect(this) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            when {
                destination.hierarchy.any { it.route == PrimaryNav.Home.route } -> { selectedItem.value = PrimaryNav.Home }
                destination.hierarchy.any { it.route == PrimaryNav.Portfolio.route } -> { selectedItem.value = PrimaryNav.Portfolio }
            }
        }
        addOnDestinationChangedListener(listener)
        onDispose { removeOnDestinationChangedListener(listener) }
    }

    return selectedItem
}

@Composable
internal fun HomeBottomNavigation(
    selectedNavigation: PrimaryNav,
    onNavigationSelected: (PrimaryNav) -> Unit,
    modifier: Modifier = Modifier
) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.surface,
        modifier = modifier
    ) {
        HomeBottomNavigationItem(
            label = "Home",
            selected = selectedNavigation == PrimaryNav.Home,
            onClick = { onNavigationSelected(PrimaryNav.Home) },
            contentDescription = "Home",
            selectedPainter = rememberVectorPainter(Icons.Filled.Home),
            painter = rememberVectorPainter(Icons.Outlined.Home),
        )

        HomeBottomNavigationItem(
            label = "Portfolio",
            selected = selectedNavigation == PrimaryNav.Portfolio,
            onClick = { onNavigationSelected(PrimaryNav.Portfolio) },
            contentDescription = "Portfolio",
            selectedPainter = rememberVectorPainter(Icons.Default.Favorite),
            painter = rememberVectorPainter(Icons.Default.FavoriteBorder),
        )
    }
}

@Composable
private fun RowScope.HomeBottomNavigationItem(
    selected: Boolean,
    selectedPainter: Painter? = null,
    painter: Painter,
    contentDescription: String,
    label: String,
    onClick: () -> Unit,
) {
    BottomNavigationItem(
        icon = {
            if (selectedPainter != null) {
                Crossfade(targetState = selected) { selected ->
                    Icon(
                        painter = if (selected) selectedPainter else painter,
                        contentDescription = contentDescription
                    )
                }
            } else {
                Icon(
                    painter = painter,
                    contentDescription = contentDescription
                )
            }
        },
        label = { Text(label) },
        selected = selected,
        onClick = onClick,
    )
}
