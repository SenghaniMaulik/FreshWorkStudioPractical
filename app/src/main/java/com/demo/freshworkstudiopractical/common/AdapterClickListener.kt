package com.demo.freshworkstudiopractical.common

import android.view.View

interface AdapterClickListener {
    fun onItemClick(view: View?, pos: Int, `object`: Any?)
}