package com.netdy.netdy.ui.base

import android.os.Bundle
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyRecyclerView
import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.withState

abstract class BaseEpoxyFragment : BaseFragment() {

    lateinit var recyclerView: EpoxyRecyclerView
    protected val epoxyController by lazy { epoxyController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        epoxyController.onRestoreInstanceState(savedInstanceState)
        epoxyController.setFilterDuplicates(true)
    }

    override fun invalidate() {
        recyclerView.requestModelBuild()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        epoxyController.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        epoxyController.cancelPendingModelBuild()
        super.onDestroyView()
    }

    abstract fun epoxyController(): MvRxEpoxyController

    fun <S : MvRxState, A : MvRxViewModel<S>> simpleController(
        viewModel: A,
        buildModels: EpoxyController.(state: S) -> Unit
    ) = MvRxEpoxyController {

        if (view == null || isRemoving) return@MvRxEpoxyController

        withState(viewModel) { state ->
            buildModels(state)
        }
    }

    fun <A : BaseMvRxViewModel<B>, B : MvRxState, C : BaseMvRxViewModel<D>, D : MvRxState>
            simpleController(
                viewModel1: A,
                viewModel2: C,
                buildModels: EpoxyController.(state1: B, state2: D) -> Unit
            ) = MvRxEpoxyController {
        if (view == null || isRemoving) return@MvRxEpoxyController
        withState(viewModel1, viewModel2) { state1, state2 ->
            buildModels(state1, state2)
        }
    }
}