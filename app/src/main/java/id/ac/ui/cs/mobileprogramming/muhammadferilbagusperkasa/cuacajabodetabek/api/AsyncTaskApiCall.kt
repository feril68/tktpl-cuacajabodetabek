package id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.api

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.gson.Gson
import id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.MainActivity
import id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.R
import id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.database.entity.InformasiCuacaEntity
import id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.database.entity.LogApiCallEntity
import id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.repository.InformasiCuacaRepository
import id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.repository.LogApiCallRepository
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.text.SimpleDateFormat
import java.util.*


class AsyncTaskApiCall(private var application: Application) : AsyncTask<Void, Void, String>() {
    val client = OkHttpClient()
    val stringIdAreaGroup = "1642911,1648473,1645518,1625084,1649378"
    var localeLanguage : String = Locale.getDefault().language
    private var CHANNEL_CODE = "id_test"
    private var NOTIFICATION_ID = 12312213

    override fun doInBackground(vararg p0: Void?): String? {
        localeLanguage = if (localeLanguage == "in"){
            "id"
        }else{
            "en"
        }
        val request: Request = Request.Builder()
                .url("http://api.openweathermap.org/data/2.5/group?id=$stringIdAreaGroup&units=metric&lang=$localeLanguage&appid=63fcf1ce1c302ff0f1447db2fc1f943e")
                .build()
         return try {
            val response : Response = client.newCall(request).execute()
            if (!response.isSuccessful) {
                null
            } else{

                response.body()!!.string()
            }
        } catch (e: Exception) {
            null
        }
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        if (result != null){
            val jsonResult = Gson().fromJson(result, ApiCuacaEntity.ApiCuacaBody::class.java)
            val logApiCallRepository = LogApiCallRepository(application)
            val simpleDateFormat : SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val stringDate : String = simpleDateFormat.format(Date())
            logApiCallRepository.insertLogApiCall(LogApiCallEntity(timestamp = stringDate))
            val textNotification = textNotificationGenerator(jsonResult.list)
            createNotificationChannel()
            createNotification(textNotification)
            jsonResult.list.forEach {
                val informasiCuacaEntity = InformasiCuacaEntity(
                    daerah = it.name,
                    koordinat = it.coord.lat.toString() + "," + it.coord.lon.toString(),
                    cuaca = it.weather[0].description,
                    temperaturRerata = it.main.temp,
                    temperaturMaks = it.main.temp_max,
                    temperaturMin = it.main.temp_min,
                    tekanan = it.main.pressure,
                    kelembapan = it.main.humidity,
                    kecepatanAngin = it.wind.speed,
                    pengelihatan = it.visibility,
                    icon = it.weather[0].icon
                )
                val informasiCuacaRepository = InformasiCuacaRepository(application)
                informasiCuacaRepository.insertInformasiCuaca(informasiCuacaEntity)
            }
        }
    }
    private fun textNotificationGenerator(listCuaca : List<ApiCuacaEntity.ApiCuacaList>) : String{
        var data = ""
        listCuaca.forEach {
            data = data + it.name + " : " + it.weather[0].description + "\n"
        }
        return data
    }
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Cuaca"
            val descriptionText = "Cuaca"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_CODE, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                    application.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(setText: String) {
        val intent = Intent(application, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(application, 0, intent, 0)

        val builder = NotificationCompat.Builder(application, CHANNEL_CODE)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Data : ")
                .setStyle(
                        NotificationCompat.BigTextStyle()
                                .bigText(setText)
                )
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

        with(NotificationManagerCompat.from(application)) {
            notify(NOTIFICATION_ID, builder.build())
        }
    }
}
