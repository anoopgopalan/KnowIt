package com.app.knowit.ui.view

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import com.app.knowit.KnowIt
import com.app.knowit.R
import com.app.knowit.ui.viewmodel.FactViewModel
import com.app.knowit.ui.viewmodel.FactViewModelProvideFactory
import com.app.knowit.util.subscribeAndObserveOnMainThread
import kotlinx.android.synthetic.main.launcher_activity_layout.*
import javax.inject.Inject

/**
 * Main activity which loads the [FactAdapter] to display the Facts
 */

class LauncherActivity : AppCompatActivity() {

    @Inject
    lateinit var factViewModelProviderFactory: FactViewModelProvideFactory

    private lateinit var factViewModel: FactViewModel

    private val adapter = FactAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.launcher_activity_layout)
        KnowIt.appComponent.inject(this)
        factViewModel = createNewsViewModel()

        initUi()
        setupUiSubscriptions()
    }

    private fun initUi() {
        factsList.adapter = adapter
        swipeToRefresh.setOnRefreshListener {
            factViewModel.swipedToRefreshData()
        }
    }

    private fun setupUiSubscriptions() {
        factViewModel.factLoads.subscribeAndObserveOnMainThread {
            adapter.adapterItem = it
        }

        factViewModel.errorMessageShows.subscribeAndObserveOnMainThread {
            Snackbar.make(factsList, getString(it), Snackbar.LENGTH_LONG).show()
        }

        factViewModel.progressBarVisibilityChanges.subscribeAndObserveOnMainThread {
            swipeToRefresh.isRefreshing = it
            if (it) {
                loadingView.visibility = View.VISIBLE
                factsList.visibility = View.GONE
                loadingView.startShimmerAnimation()
            } else {
                loadingView.visibility = View.GONE
                factsList.visibility = View.VISIBLE
                loadingView.stopShimmerAnimation()
            }
        }

        factViewModel.titleUpdates.subscribeAndObserveOnMainThread {
            supportActionBar?.title = it
        }
        factViewModel.loadFacts()
    }

    private fun createNewsViewModel(): FactViewModel {
        return ViewModelProviders.of(this, factViewModelProviderFactory).get(FactViewModel::class.java)
    }

}
