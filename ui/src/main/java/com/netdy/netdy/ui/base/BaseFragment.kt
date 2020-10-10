package com.netdy.netdy.ui.base

import android.os.Bundle
import android.util.Log
import android.view.View
import com.airbnb.mvrx.BaseMvRxFragment
import com.airbnb.mvrx.MvRxState
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.netdy.netdy.ui.R
import com.netdy.netdy.ui.TAG_NETDY_DEBUG
import com.netdy.netdy.ui.common.HiddenBottomBar
import kotlinx.android.synthetic.main.activity_main.view.*

abstract class BaseFragment : BaseMvRxFragment() {

    abstract val viewModel: MvRxViewModel<out MvRxState>

    private var bottomBar: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG_NETDY_DEBUG, "onCreate(): $this")
        bottomBar = activity?.findViewById(R.id.navBottomBar)
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG_NETDY_DEBUG, "onStart(): $this")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG_NETDY_DEBUG, "onResume(): $this")

        setBottomBarVisibility()
    }

    override fun onPause() {
        Log.d(TAG_NETDY_DEBUG, "onPause(): $this")
        super.onPause()
    }

    override fun onStop() {
        Log.d(TAG_NETDY_DEBUG, "onStop(): $this")
        super.onStop()
    }

    override fun onDestroy() {
        Log.d(TAG_NETDY_DEBUG, "onDestroy(): $this")
        super.onDestroy()
    }

    override fun onDetach() {
        Log.d(TAG_NETDY_DEBUG, "onDetach(): $this")
        super.onDetach()
    }

    private fun setBottomBarVisibility() {
        if (this is HiddenBottomBar) bottomBar?.visibility = View.GONE
        else bottomBar?.visibility = View.VISIBLE
    }
}