package com.demo.freshworkstudiopractical.utils

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.demo.freshworkstudiopractical.R
import com.demo.freshworkstudiopractical.common.SafeClickListener
import java.io.IOException

fun Any?.toast(context: Context, isShort: Boolean = false): Toast {
    return Toast.makeText(
        context,
        this.toString(),
        if (isShort) Toast.LENGTH_SHORT else Toast.LENGTH_LONG
    ).apply { show() }
}

fun Context.checkForInternetConnection(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
    return when {
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> true
        else -> false
    }
}

fun Application.checkForInternetConnection(): Boolean {
    val connectivityManager = getSystemService(android.content.Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
    return when {
        capabilities.hasTransport(android.net.NetworkCapabilities.TRANSPORT_WIFI) -> true
        capabilities.hasTransport(android.net.NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        capabilities.hasTransport(android.net.NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        capabilities.hasTransport(android.net.NetworkCapabilities.TRANSPORT_VPN) -> true
        else -> false
    }
}

fun ImageView.loadImageFromUrl(imageUrl: String?, placeHolder: Int? = null) {
    Glide
        .with(this.context)
        .load(imageUrl)
        .placeholder(placeHolder ?: R.drawable.ic_error_placeholder)
        .error(placeHolder ?: R.drawable.ic_error_placeholder)
        .into(this)
}


@Throws(IOException::class)
fun String.assetJSONFile(
    context: Context
): String? {
    val manager = context.assets
    val file = manager.open(this)
    val formArray = ByteArray(file.available())
    file.read(formArray)
    file.close()
    return String(formArray)
}

fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
    val safeClickListener = SafeClickListener {
        onSafeClick(it)
    }
    setOnClickListener(safeClickListener)
}