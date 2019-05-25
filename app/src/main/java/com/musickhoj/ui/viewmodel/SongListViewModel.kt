package com.musickhoj.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.musickhoj.api.model.Songs
import com.musickhoj.repository.SongRepository


class SongListViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: SongRepository = SongRepository(application)

    fun insert(list: List<Songs>) {
        repository.deleteAllSongs()
        for (data in list)
            repository.insert(data)
    }

    fun deleteAllSongs() {
        repository.deleteAllSongs()
    }

    fun getAllSongs(): LiveData<List<Songs>> {
        return repository.getSongs()
    }

    fun getServerData(query: String, country: String): LiveData<List<Songs>> {
        return repository.getSongsList(query, country)
    }

}