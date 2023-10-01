package com.example.sipentas.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sipentas.view.form.FormView
import com.example.sipentas.view.login.LoginView

@Composable
fun NavigationAdapter() {
val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppRoute.Login.route ) {
        composable(AppRoute.Login.route) {
            LoginView(navController)
        }
        composable(AppRoute.Form.route) {
            FormView(navController)
        }
    }
}