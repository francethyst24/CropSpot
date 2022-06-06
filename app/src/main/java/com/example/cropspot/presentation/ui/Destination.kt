package com.example.cropspot.presentation.ui

enum class Destination(
    val route: String,
    val canPopBackStack: Boolean,
) {
    HOME(
        route = "home",
        canPopBackStack = false
    ),
    CROP(
        route = "crop",
        canPopBackStack = true
    ),
    ACCOUNT(
        route = "account",
        canPopBackStack = false
    ),
    HISTORY(
        route = "history",
        canPopBackStack = false
    ),
    DISEASES(
        route = "diseases",
        canPopBackStack = false
    ),
    SETTINGS(
        route = "settings",
        canPopBackStack = false
    ),
    HELP(
        route = "help",
        canPopBackStack = false
    ),
}