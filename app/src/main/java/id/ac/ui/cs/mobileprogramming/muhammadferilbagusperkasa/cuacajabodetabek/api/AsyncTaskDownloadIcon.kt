package id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.api

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.AsyncTask
import id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.database.entity.IconPathEntity
import id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.repository.IconPathRepository
import id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.util.ImageUtilHelper
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.*


class AsyncTaskDownloadIcon(private var application: Application, private val iconName:String) : AsyncTask<Void, Void, Bitmap>() {
    val client = OkHttpClient()
    val request: Request = Request.Builder()
        .url("http://openweathermap.org/img/wn/$iconName@2x.png")
        .build()

    override fun doInBackground(vararg p0: Void?): Bitmap? {
        return try {
            val response : Response = client.newCall(request).execute()
            if (!response.isSuccessful) {
                null
            } else{
                val inputStream : InputStream = response.body()!!.byteStream()
                val bufferedInputStream = BufferedInputStream(inputStream)
                val bitmap : Bitmap = BitmapFactory.decodeStream(bufferedInputStream)
                replaceColor(bitmap, Color.TRANSPARENT, Color.WHITE)
            }
        } catch (e: Exception) {
            null
        }
    }

    override fun onPostExecute(result: Bitmap?) {
        super.onPostExecute(result)
        if (result != null){
            val stringPathAfterSave = ImageUtilHelper().saveImageToMediaStoreAndReturnPath(
                title = iconName,
                bitmap = result,
                context = application.applicationContext
            )
            val saveImageReturnedString : String = stringPathAfterSave
            val iconPath = IconPathEntity(iconName, saveImageReturnedString)
            IconPathRepository(application).insertIconPath(iconPath)
        }
    }

    //Source https://stackoverflow.com/questions/7237915/replace-black-color-in-bitmap-with-red
    fun replaceColor(src: Bitmap?, fromColor: Int, targetColor: Int): Bitmap? {
        if (src == null) {
            return null
        }
        val width = src.width
        val height = src.height
        val pixels = IntArray(width * height)
        src.getPixels(pixels, 0, width, 0, 0, width, height)
        for (x in pixels.indices) {
            pixels[x] = if (pixels[x] == fromColor) targetColor else pixels[x]
        }
        val result = Bitmap.createBitmap(width, height, src.config)
        result.setPixels(pixels, 0, width, 0, 0, width, height)
        return result
    }
}
