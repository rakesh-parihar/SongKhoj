package com.musickhoj.ui.activity

import android.os.Bundle
import android.os.Handler
import com.musickhoj.R
import com.musickhoj.base.BaseActivity
import com.musickhoj.util.PREF_COUNTRY
import java.util.*

class SplashActivity : BaseActivity() {

    override fun getContentView(): Int {
        return R.layout.activity_splash

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        editPref().putString(PREF_COUNTRY, Locale.getDefault().country).apply()

        /**
         * Delayed launch
         */
        Handler().postDelayed({
            launchActivity(SearchActivity(),true)
        }, 1000)
    }
}
