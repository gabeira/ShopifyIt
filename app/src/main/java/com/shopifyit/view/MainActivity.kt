package com.shopifyit.view

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shopifyit.MyApp
import com.shopifyit.R
import com.shopifyit.data.model.Repository
import com.shopifyit.domain.getSortedRepositories
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var repositoryViewModel: RepositoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        (application as MyApp).component?.inject(this)

        repositoryViewModel = ViewModelProviders.of(this).get(RepositoryViewModel::class.java)
        repositoryViewModel.repositoryList.observe(this, Observer { data ->
            swipeRefreshLayout.isRefreshing = false
            data?.let {
                showEmptyListMessage(it.isEmpty())
                repoList.adapter = RepositoryAdapter(
                        it.getSortedRepositories(),
                        onListEventInteractionListener()
                )
            }
        })

        setupScrollListener()

        swipeRefreshLayout.setOnRefreshListener {
            repositoryViewModel.reload()
        }

        repositoryViewModel.getNetworkErrors().observe(this, Observer<String> {
            swipeRefreshLayout.isRefreshing = false
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
    }

    private fun showEmptyListMessage(show: Boolean) {
        if (show) {
            emptyList.visibility = View.VISIBLE
            repoList.visibility = View.GONE
        } else {
            emptyList.visibility = View.GONE
            repoList.visibility = View.VISIBLE
        }
    }

    private fun setupScrollListener() {
        val layoutManager = repoList.layoutManager as LinearLayoutManager
        repoList.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager.itemCount
                val visibleItemCount = layoutManager.childCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                repositoryViewModel.listScrolled(visibleItemCount, lastVisibleItem, totalItemCount)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        if (repositoryViewModel.repositoryList.hasObservers()) {
            repositoryViewModel.repositoryList.removeObservers(this)
        }
    }

    interface OnListRepositoryInteractionListener {
        fun onRepositoryClick(repo: Repository)
    }

    private fun onListEventInteractionListener(): OnListRepositoryInteractionListener {
        return object : OnListRepositoryInteractionListener {
            override fun onRepositoryClick(repo: Repository) {
                openWebUrl(repo.html_url)
            }
        }
    }

    fun openWebUrl(url: String) {
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                this,
                getString(R.string.open_url_browser_error), Toast.LENGTH_LONG
            ).show()
            e.printStackTrace()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
