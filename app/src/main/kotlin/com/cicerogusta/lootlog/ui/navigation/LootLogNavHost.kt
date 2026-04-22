package com.cicerogusta.lootlog.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cicerogusta.lootlog.ui.screen.add.AddItemScreen
import com.cicerogusta.lootlog.ui.screen.auth.AuthScreen
import com.cicerogusta.lootlog.ui.screen.auth.AuthViewModel
import com.cicerogusta.lootlog.ui.screen.home.HomeScreen
import com.cicerogusta.lootlog.ui.screen.paywall.PaywallScreen
import com.cicerogusta.lootlog.ui.screen.settings.SettingsScreen
import com.cicerogusta.lootlog.ui.screen.splash.SplashScreen

@Composable
fun LootLogNavHost(
    navController: NavHostController = rememberNavController(),
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val isLoggedIn by authViewModel.isLoggedIn.collectAsState(initial = false)

    NavHost(
        navController = navController,
        startDestination = Route.Splash.route
    ) {
        composable(Route.Splash.route) {
            SplashScreen(
                onNavigateNext = {
                    navController.navigate(if (isLoggedIn) Route.Home.route else Route.Auth.route) {
                        popUpTo(Route.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Route.Auth.route) {
            AuthScreen(
                onGoogleSignIn = { idToken ->
                    authViewModel.signInWithGoogle(idToken)
                }
            )
        }

        composable(Route.Home.route) {
            HomeScreen(
                onNavigateToAddItem = {
                    navController.navigate(Route.AddItem.route)
                },
                onNavigateToPaywall = {
                    navController.navigate(Route.Paywall.route)
                },
                onNavigateToSettings = {
                    navController.navigate(Route.Settings.route)
                },
                onLogout = {
                    authViewModel.signOut()
                    navController.navigate(Route.Auth.route) {
                        popUpTo(Route.Home.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Route.AddItem.route) {
            AddItemScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onItemAdded = {
                    navController.popBackStack()
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

        composable(Route.Settings.route) {
            SettingsScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}

sealed class Route(val route: String) {
    object Splash : Route("splash")
    object Auth : Route("auth")
    object Home : Route("home")
    object AddItem : Route("add_item")
    object Paywall : Route("paywall")
    object Settings : Route("settings")
}
