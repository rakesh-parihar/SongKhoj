package com.musickhoj.repository

import android.app.Application
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData
import com.musickhoj.api.APIService
import com.musickhoj.api.ServiceFactory
import com.musickhoj.api.model.Songs
import com.musickhoj.db.SongDB
import com.musickhoj.db.dao.SongDao
import com.musickhoj.db.entity.SongEntity
import androidx.lifecycle.MutableLiveData
import com.musickhoj.api.model.SongResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import kotlin.math.log


class SongRepository(application: Application) {

    private var songDao: SongDao
    private var allSongs: LiveData<List<Songs>>
    private var apiService: APIService = ServiceFactory.getInstance()

    init {
        val database: SongDB = SongDB.getInstance(application.applicationContext)
        songDao = database.songDao()
        allSongs = songDao.getSongs()
    }


    fun getSongsList(query: String, country: String): LiveData<List<Songs>> {
        val data = MutableLiveData<List<Songs>>()

        apiService.performSearch(query, country).enqueue(object : Callback<SongResult> {
            override fun onResponse(call: Call<SongResult>, response: Response<SongResult>) {
                    if (response.body() != null) {
                        val resp: SongResult = response.body() as SongResult
                        data.value = resp.results
                    }
            }

            override fun onFailure(call: Call<SongResult>, t: Throwable) {
                data.value = listOf()
            }

        })

        return data
    }


    fun insert(song: Songs) {
        InsertSongAsyncTask(songDao).execute(
            SongEntity(
                song.trackId,
                song.artistName,
                song.trackName,
                song.previewUrl,
                song.artworkUrl100,
                song.trackPrice,
                song.currency,
                song.primaryGenreName
            )
        )

    }

    fun deleteAllSongs() {
        DeleteAllSongsAsyncTask(songDao).execute()
    }

    fun getSongs(): LiveData<List<Songs>> {
        return allSongs
    }


    private class InsertSongAsyncTask(val songDao: SongDao) : AsyncTask<SongEntity, Unit, Unit>() {

        override fun doInBackground(vararg song: SongEntity) {
            songDao.insertSongData(song)
        }
    }


    private class DeleteAllSongsAsyncTask(val songDao: SongDao) : AsyncTask<Unit, Unit, Unit>() {

        override fun doInBackground(vararg p0: Unit?) {
            songDao.removeSongData()
        }
    }

}