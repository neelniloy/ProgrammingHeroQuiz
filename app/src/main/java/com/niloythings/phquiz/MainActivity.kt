package com.niloythings.phquiz

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.niloythings.phquiz.databinding.ActivityMainBinding
import com.niloythings.phquiz.databinding.CustomNoInternetDialogBinding
import com.niloythings.phquiz.service.NetworkCallback
import com.niloythings.phquiz.service.NetworkChangeReceiver
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket


class MainActivity : AppCompatActivity(), NetworkCallback {

        private lateinit var binding: ActivityMainBinding
        private lateinit var navController: NavController
        private lateinit var appBarConfiguration: AppBarConfiguration

        //check Internet Activity
        private lateinit var networkChangeReceiver: NetworkChangeReceiver
        private var alertDialog: AlertDialog? = null

        @RequiresApi(Build.VERSION_CODES.N)
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

            // Register the broadcast receiver to monitor network state changes
            networkChangeReceiver = NetworkChangeReceiver(this)
            val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
            registerReceiver(networkChangeReceiver, intentFilter)

            setSupportActionBar(binding.mToolbar)
            val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.fragmentContainerView) as NavHostFragment
            navController = navHostFragment.navController
            appBarConfiguration = AppBarConfiguration(navController.graph)
            setupActionBarWithNavController(navController, appBarConfiguration)


        }

        override fun onSupportNavigateUp(): Boolean {
            return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
        }


        override fun onDestroy() {
            // Unregister the broadcast receiver when the activity is destroyed
            unregisterReceiver(networkChangeReceiver)
            super.onDestroy()
        }


        private fun isInternetAccessible(): Boolean {
            return try {
                val timeoutMs = 1500
                val socket = Socket()
                val socketAddress = InetSocketAddress("8.8.8.8", 53)  // Use Google's DNS server
                socket.connect(socketAddress, timeoutMs)
                socket.close()
                true
            } catch (e: IOException) {
                false
            }
        }

        override fun isConnectedToNetwork(): Boolean {
            val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> isInternetAccessible()
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> isInternetAccessible()
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> isInternetAccessible()
                else -> false
            }
        }

        override fun onNetworkStateChanged(isConnected: Boolean) {
            runOnUiThread {
                if (!isConnected) {
                    showCustomNoInternetDialog()
                } else alertDialog?.dismiss()
            }
        }

        private fun showCustomNoInternetDialog() {
            val dialogBinding = CustomNoInternetDialogBinding.inflate(layoutInflater)
            val dialogView = dialogBinding.root
            val dialogBuilder = AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(false)

            alertDialog = dialogBuilder.create()

            // Define the action when the exit button is clicked
            dialogBinding.openBtn.setOnClickListener {
                alertDialog?.dismiss()
                startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS))
            }

            alertDialog?.show()
        }
    }

