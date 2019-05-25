package com.musickhoj.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import com.google.gson.Gson
import com.musickhoj.R
import com.musickhoj.api.model.Songs
import com.musickhoj.base.BaseActivity
import com.musickhoj.util.BUNDLE_TAG
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : BaseActivity() {

    override fun getContentView(): Int {
        return R.layout.activity_details
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val bundle = intent.extras
        if (bundle != null) {
            updateUI(Gson().fromJson<Songs>(bundle.getString(BUNDLE_TAG), Songs::class.java))
        }
    }

    private fun updateUI(model: Songs) {
        log(model.artistName)
        Picasso.get().load(model.artworkUrl100).placeholder(R.drawable.ic_music).into(img_thumb)
        txt_title.text = model.trackName
        txt_artist.text = model.artistName
        txt_gen.text = model.primaryGenreName
        txt_price.text = "${model.currency}${model.trackPrice}"
        img_play.setOnClickListener {
            if (model.previewUrl != null) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setDataAndType((Uri.parse(model.previewUrl)), "audio/*")
                startActivity(intent)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)

    }
}
