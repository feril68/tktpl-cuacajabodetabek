package id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.broadcastreciever

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.util.Log
import id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.service.ApiCallSchedulerService


class NetworkChangeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val wifi = connectivityManager
            .getNetworkInfo(ConnectivityManager.TYPE_WIFI)

        val mobile = connectivityManager
            .getNetworkInfo(ConnectivityManager.TYPE_MOBILE)

        if (wifi?.isConnected!! || mobile?.isConnected!!) {
            Log.e("status inet", "true")
            val intentApiCall = Intent(context, ApiCallSchedulerService::class.java)
            context.startService(intentApiCall)
        }
        else{
            Log.e("status inet", "false")
            val intentApiCall = Intent(context, ApiCallSchedulerService::class.java)
            context.stopService(intentApiCall)
        }
    }
}