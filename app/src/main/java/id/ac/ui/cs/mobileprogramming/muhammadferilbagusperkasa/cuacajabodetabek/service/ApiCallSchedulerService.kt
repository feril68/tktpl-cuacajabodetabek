package id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.api.AsyncTaskApiCall
import java.util.*


class ApiCallSchedulerService : Service() {
    private var running = true

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val threadSchedulerService = Thread {
            while (running) {
                AsyncTaskApiCall(application).execute()
                Log.e("Executed", Calendar.getInstance().time.toString())
                Thread.sleep(30000)
            }
        }
        threadSchedulerService.start()
        return START_STICKY
    }

    override fun onDestroy() {
        running = false
        super.onDestroy()
    }
}
