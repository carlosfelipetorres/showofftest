package com.carlostorres.showofftest

import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        try {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        } catch (e: StackOverflowError) {
        }
    }

    fun showError(message: String?) {
        try {
            val error = message ?: getString(R.string.error_generic)
            val snackbar = nav_host_fragment.view?.let { Snackbar.make(it, error, Snackbar.LENGTH_LONG) }

            snackbar?.view?.background = ContextCompat.getDrawable(this ,R.drawable.squared_button_colored_purple)
            snackbar?.animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE

            val mTextView = snackbar?.view?.findViewById(R.id.snackbar_text) as TextView
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
                mTextView.textAlignment = View.TEXT_ALIGNMENT_CENTER
            else
                mTextView.gravity = Gravity.CENTER_HORIZONTAL
            snackbar.show()
        } catch (e: Exception) {
        }
    }
}
