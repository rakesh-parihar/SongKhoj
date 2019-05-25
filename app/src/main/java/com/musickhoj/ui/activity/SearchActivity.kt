package com.musickhoj.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.musickhoj.R
import com.musickhoj.api.model.Songs
import com.musickhoj.base.BaseActivity
import com.musickhoj.ui.adapter.SongAdapter
import com.musickhoj.ui.viewmodel.SongListViewModel
import com.musickhoj.util.BUNDLE_TAG
import com.musickhoj.util.PREF_COUNTRY
import kotlinx.android.synthetic.main.activity_search.*


class SearchActivity : BaseActivity(), SearchView.OnQueryTextListener, SongAdapter.Listener {

    private lateinit var songListViewModel: SongListViewModel
    private lateinit var mAdapter: SongAdapter
    private lateinit var rvSongs: RecyclerView
    private var country: String = ""
    private var firstLaunch: Boolean = true

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query.isNullOrEmpty())
            return false

        hideKeyboard()
        progress_bar.visibility=View.VISIBLE
        progress_bar.bringToFront()
        songListViewModel.getServerData(query!!, country).observe(this,
            Observer<List<Songs>> { t ->
                handleEmptyData(t.isNullOrEmpty())
                progress_bar.visibility=View.GONE
                mAdapter.setSongs(t)
                songListViewModel.insert(t)
            })
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {

        return false
    }

    override fun getContentView(): Int {
        return R.layout.activity_search
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.search, menu)
        val searchMenuItem = menu?.findItem(R.id.action_search)
        val searchView = searchMenuItem?.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initRecyclerView()

        songListViewModel = ViewModelProviders.of(this).get(SongListViewModel::class.java)
        songListViewModel.getAllSongs().observe(this,
            Observer<List<Songs>> { t ->

                handleEmptyData(t.isNullOrEmpty())

                if (firstLaunch && t.isNotEmpty()) {
                    firstLaunch = false
                    mAdapter.setSongs(t!!)
                }
            })
    }

    override fun onItemClick(songModel: Songs) {
        val bundle = Bundle()
        bundle.putString(BUNDLE_TAG, Gson().toJson(songModel))
        launchActivity(DetailsActivity(), bundle = bundle)
    }

    private fun initRecyclerView() {
        country = prefs.getString(PREF_COUNTRY, "")
        rvSongs = findViewById(R.id.rvSongs)
        mAdapter = SongAdapter(this)
        rvSongs.setHasFixedSize(true)
        val layoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(this)
        rvSongs.layoutManager = layoutManager
        rvSongs.adapter = mAdapter

    }

    private fun handleEmptyData(isNotEmpty: Boolean) {
        if (isNotEmpty) {
            empty_view.visibility = View.VISIBLE
        } else {
            empty_view.visibility = View.GONE
        }
    }
}