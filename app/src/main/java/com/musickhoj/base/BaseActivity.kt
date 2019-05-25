package com.musickhoj.base

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    lateinit var prefs: SharedPreferences
    private val TAG = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        prefs = PreferenceManager.getDefaultSharedPreferences(this)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(getContentView())
    }

    protected abstract fun getContentView(): Int

    fun editPref(): SharedPreferences.Editor {
        return prefs.edit()
    }

    fun log(value: Any?) {
        Log.d(TAG, "$value")
    }

    fun launchActivity(activity: AppCompatActivity, doFinish: Boolean = false, bundle: Bundle? = null) {
        var intent = Intent(this@BaseActivity, activity::class.java)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)

        if (doFinish)
            finish()
    }

    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus.windowToken, 0)
    }
}