package id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.util

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import java.io.IOException

class ImageUtilHelper {
    fun saveImageToMediaStoreAndReturnPath(
        title: String?,
        bitmap: Bitmap,
        context: Context
    ) : String{
        val saveData = MediaStore.Images.Media.insertImage(
            context.contentResolver, bitmap, title, title
        )
        return saveData.toString()
    }
    fun getImageFromMediaStore(uri: Uri, context: Context) : Bitmap? {
        val image: Bitmap?
        try {
            image = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        } catch (exception: IOException) {
            throw exception
        }
        return image!!

    }

}

