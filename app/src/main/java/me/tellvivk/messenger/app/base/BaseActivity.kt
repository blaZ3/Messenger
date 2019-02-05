package me.tellvivk.messenger.app.base

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import me.tellvivk.messenger.R

open class BaseActivity: AppCompatActivity() {

    protected fun initToolbar(title: String, toolbar: androidx.appcompat.widget.Toolbar) {
        setSupportActionBar(toolbar)
        supportActionBar?.title = title
        toolbar.navigationIcon = resources
            .getDrawable(R.drawable.ic_arrow_back_white)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }



    fun showToast(msg: String){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

}