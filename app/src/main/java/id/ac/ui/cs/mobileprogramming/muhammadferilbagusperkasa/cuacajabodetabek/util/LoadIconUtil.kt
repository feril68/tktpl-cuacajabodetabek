package id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.util

import android.app.Application
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.util.Log
import id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.api.AsyncTaskDownloadIcon
import id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.database.entity.IconPathEntity
import id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.repository.IconPathRepository
import java.io.IOException

class LoadIconUtil(private val application: Application) {
    fun loadIcon(iconName: String) : Bitmap?{
        val iconPathRepository = IconPathRepository(application)
        val iconPath : IconPathEntity? = try{
            iconPathRepository.getIconPathByIconName(iconName)?.get(0)
        } catch (exception: IndexOutOfBoundsException){
            null
        }
        val iconBitmap : Bitmap?
        if (iconPath != null){
            val uri: Uri = Uri.parse(iconPath.contentPath)
            iconBitmap = try {
                ImageUtilHelper().getImageFromMediaStore(uri, application.applicationContext)
            } catch (exception: IOException){
                exception.printStackTrace()
                val downloadIconResult : Bitmap = AsyncTaskDownloadIcon(application, iconName).execute().get()
                downloadIconResult
            }
        } else {
            iconBitmap = AsyncTaskDownloadIcon(application, iconName).execute().get()
        }
        return resizeIconBitmap(iconBitmap!!, 3)
    }
    private fun resizeIconBitmap(bitmap: Bitmap, scale: Int): Bitmap? {
        val width = bitmap.width
        val height = bitmap.height
        val scaleWidth = (width*scale).toFloat() / width
        val scaleHeight = (width*scale).toFloat() / height
        val matrix = Matrix()
        matrix.postScale(scaleWidth, scaleHeight)
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false)
    }
}