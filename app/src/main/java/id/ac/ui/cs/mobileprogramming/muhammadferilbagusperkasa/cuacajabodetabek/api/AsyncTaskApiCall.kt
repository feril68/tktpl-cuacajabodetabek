package id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.api

import android.app.Application
import android.os.AsyncTask
import android.util.Log
import com.google.gson.Gson
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
}
