package com.cicerogusta.lootlog.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cicerogusta.lootlog.ui.screen.home.HomeScreen
import com.cicerogusta.lootlog.ui.screen.paywall.PaywallScreen

@Composable
fun LootLogNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Route.Home.route
    ) {
        composable(Route.Home.route) {
            HomeScreen(
                onNavigateToPaywall = {
                    navController.navigate(Route.Paywall.route)
                }
            )
        }

        composable(Route.Paywall.route) {
            PaywallScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onSubscribeClick = {
                    // Integração com RevenueCat será feita aqui
                    navController.popBackStack()
                },
                onRestorePurchases = {
                    // Integração com RevenueCat será feita aqui
                }
            )
        }
    }
}

sealed class Route(val route: String) {
    object Home : Route("home")
    object Paywall : Route("paywall")
}
