package com.niloythings.phquiz.service

interface NetworkCallback {
    fun isConnectedToNetwork(): Boolean
    fun onNetworkStateChanged(isConnected: Boolean)
}