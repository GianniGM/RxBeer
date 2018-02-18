package com.giangraziano.rxbeers

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.TextView
import com.giangraziano.rxbeers.adapter.BeersAdapter
import com.giangraziano.rxbeers.common.setColumnsLayout
import com.giangraziano.rxbeers.utils.callService
import com.giangraziano.rxbeers.utils.showToast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    private val recyclerView: RecyclerView by lazy {
        beers_list.setColumnsLayout(this)
        beers_list.adapter = BeersAdapter()
        beers_list
    }

    private var currentSearchingValue: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setPullToRefresh()

        serve(4) {
            showToast(this, it)
        }
    }

    private fun setPullToRefresh() {
        swipe_to_refresh.setOnRefreshListener {
            Log.d(TAG, "REFRESHING APP")
            val int = currentSearchingValue?.toInt()
            if (int == null)
                serve(4) {
                    swipe_to_refresh.isRefreshing = false
                }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        Log.d(TAG, "input texted: $searchView")

        searchView.queryHint = "Beer name"
        searchView.setOnQueryTextListener(this)
        return super.onCreateOptionsMenu(menu)
    }

    private fun serve(abv: Int, messageCallback: (String) -> Unit) {
        showProgressBar()
        callService(abv).subscribe(
                {
                    (recyclerView.adapter as BeersAdapter).setData(it.data)
                    hideProgressBar(true)
                    messageCallback("Success :)")
                },
                {
                    hideProgressBar(false)
                    messageCallback("Error :(")
                }
        )
    }

    private fun showProgressBar() {
        progress_bar.visibility = ProgressBar.VISIBLE
        error_text_message.visibility = TextView.GONE
        recyclerView.visibility = RecyclerView.GONE
    }

    private fun hideProgressBar(loadingSuccess: Boolean) {
        progress_bar.visibility = ProgressBar.GONE
        recyclerView.visibility = if (loadingSuccess) RecyclerView.VISIBLE else RecyclerView.GONE
        error_text_message.visibility = if (loadingSuccess) TextView.GONE else TextView.VISIBLE
    }

    override fun onQueryTextSubmit(submitted: String?): Boolean {
        Log.d(TAG, "submitted text: $submitted")
        currentSearchingValue = submitted
        submitted?.toInt()?.let {
            serve(it) {}
        }
        return false
    }

    override fun onQueryTextChange(changed: String?): Boolean {
        Log.d(TAG, "search text changed: $changed")
        return false
    }
}
