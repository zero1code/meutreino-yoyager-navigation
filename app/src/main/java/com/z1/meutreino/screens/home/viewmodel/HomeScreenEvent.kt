package com.z1.meutreino.screens.home.viewmodel

sealed class HomeScreenEvent {
    data object MonthForward: HomeScreenEvent()
    data object MonthBack: HomeScreenEvent()
}