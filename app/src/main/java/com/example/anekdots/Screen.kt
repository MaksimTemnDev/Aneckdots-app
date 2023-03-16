package com.example.anekdots

sealed class Screen(val route: String){
    object MenuScreen: Screen("menu_sc")
    object AnekdotScreen: Screen("anekdot_sc")
    object StartScreen: Screen("start_sc")
}
