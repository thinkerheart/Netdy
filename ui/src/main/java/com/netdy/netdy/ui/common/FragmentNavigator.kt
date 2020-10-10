package com.netdy.netdy.ui.common

import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.IdRes
import com.airbnb.mvrx.MvRx
import com.netdy.netdy.domain.navigation.ScreenNavigator
import com.netdy.netdy.ui.R

class FragmentNavigator : ScreenNavigator {

    lateinit var tabManager: TabManager

    private fun navigateTo(
        @IdRes actionId: Int,
        arg: Parcelable? = null,
        allowSeveralInstances: Boolean = false
    ) {
        if (!allowSeveralInstances &&
            tabManager.currentController.popBackStack(actionId, false)
        ) {
            return
        }

        val bundle = arg?.let { Bundle().apply { putParcelable(MvRx.KEY_ARG, it) } }
        tabManager.currentController.navigate(actionId, bundle)
    }

    private fun setNavRoot(@IdRes actionId: Int, arg: Parcelable? = null) {
        val bundle = arg?.let { Bundle().apply { putParcelable(MvRx.KEY_ARG, it) } }
        val graph = tabManager.currentController.navInflater.inflate(R.navigation.graph_nav_main)

        graph.startDestination = actionId

        tabManager.currentController.setGraph(graph, bundle)
    }

    override fun displayNewsFeedAsNavRoot() {
        setNavRoot(R.id.frmNewsFeed)
    }

    override fun displayGettingStartedAsNavRoot() {
        setNavRoot(R.id.frmGettingStarted)
    }

    override fun displayIntroductionAsNavRoot() {
        setNavRoot(R.id.frmIntroduction)
    }

    override fun navigateToSignIn() {
        navigateTo(R.id.frmSignIn)
    }

    override fun navigateToSignUp() {
        navigateTo(R.id.frmSignUp)
    }

    override fun goBackScreen() {
        tabManager.onBackPressed()
    }

    fun supportNavigateUpTo() {
        tabManager.supportNavigateUpTo()
    }

    fun switchTab(tabId: Int, addToHistory: Boolean = true) {
        tabManager.switchTab(tabId, addToHistory)
    }

    override fun resetMainScreen() {
        tabManager.resetMainActivity()
    }
}