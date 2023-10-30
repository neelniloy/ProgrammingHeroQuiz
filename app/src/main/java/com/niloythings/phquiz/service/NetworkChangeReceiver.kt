package com.niloythings.phquiz.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import com.niloythings.phquiz.service.NetworkCallback
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NetworkChangeReceiver(private val callback: NetworkCallback) : BroadcastReceiver() {
    @OptIn(DelicateCoroutinesApi::class)
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == ConnectivityManager.CONNECTIVITY_ACTION) {
            GlobalScope.launch(Dispatchers.IO) {
                val isConnected = callback.isConnectedToNetwork()
                callback.onNetworkStateChanged(isConnected)
            }
        }
    }
}