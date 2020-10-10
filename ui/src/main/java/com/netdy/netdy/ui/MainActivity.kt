package com.netdy.netdy.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.airbnb.mvrx.BaseMvRxActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.netdy.netdy.ui.common.FragmentNavigator
import com.netdy.netdy.ui.common.TabHistory
import com.netdy.netdy.ui.common.TabManager
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : BaseMvRxActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private val navigator: FragmentNavigator by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        navBottomBar.setOnNavigationItemSelectedListener(this)

        navigator.tabManager = TabManager(TabHistory(), this)

        navigator.displayIntroductionAsNavRoot()
    }

    override fun onBackPressed() {
        navigator.goBackScreen()
    }

    override fun supportNavigateUpTo(upIntent: Intent) {
        navigator.supportNavigateUpTo()
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        navigator.switchTab(p0.itemId)
        return true
    }
}