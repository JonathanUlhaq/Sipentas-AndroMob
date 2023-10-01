package com.example.sipentas.navigation

sealed class AppRoute(val route:String) {
    object Login:AppRoute("login")
    object Form:AppRoute("route")
}
