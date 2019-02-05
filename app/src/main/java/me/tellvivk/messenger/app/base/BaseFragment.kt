package me.tellvivk.messenger.app.base

import android.widget.Toast
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment(), BaseView {

    fun showToast(msg: String) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
    }

}