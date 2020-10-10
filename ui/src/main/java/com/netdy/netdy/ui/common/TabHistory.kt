package com.netdy.netdy.ui.common

import java.io.Serializable

class TabHistory : Serializable {

    private val tabs = mutableListOf<Int>()

    val isEmpty: Boolean
        get() = tabs.size == 0

    val size: Int
        get() = tabs.size

    fun add(entry: Int) {
        tabs.add(entry)
    }

    fun removePrevious(): Int {
        var entry = -1

        if (tabs.size > 1) {
            tabs.removeAt(tabs.lastIndex)
            entry = tabs.last()
        }

        return entry
    }

    fun remove(item: Int): Boolean {
        return tabs.remove(item)
    }

    fun contains(item: Int): Boolean {
        return tabs.contains(item)
    }

    fun clear() {
        tabs.clear()
    }
}