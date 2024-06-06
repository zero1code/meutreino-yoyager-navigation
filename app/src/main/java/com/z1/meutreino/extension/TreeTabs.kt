package com.z1.meutreino.extension

import android.util.Log
import androidx.compose.runtime.compositionLocalOf
import cafe.adriel.voyager.navigator.tab.Tab

class TreeTabs() {
    private val tabs: MutableList<Tab> = mutableListOf()

    fun addTab(tab: Tab) {
        tabs.add(tab)
        Log.d("TAG", "addTab: ${tabs.size}")
    }
    fun getNextTabToBack(): Tab? {
        Log.d("TAG", "getNextbackTab: ${tabs.lastOrNull()?.key}")
        return tabs.lastOrNull()
    }
    fun removeTab() {
        tabs.removeLast()
        Log.d("TAG", "removeTab: ${tabs.size}")
    }
}

val LocalTreeTabs = compositionLocalOf<TreeTabs> {
    error("Nao foi possivel acessar")
}