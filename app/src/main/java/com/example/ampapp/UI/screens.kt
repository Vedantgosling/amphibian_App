package com.example.ampapp.UI

sealed class screens(val route: String){
    object Home:screens(route = "first")

    object Detail:screens(route = "Second")
}
