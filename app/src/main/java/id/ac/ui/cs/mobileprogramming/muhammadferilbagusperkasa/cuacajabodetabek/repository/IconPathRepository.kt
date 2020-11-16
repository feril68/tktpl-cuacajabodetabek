package id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.repository

import android.app.Application
import android.os.AsyncTask
import id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.database.AppDatabase
import id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.database.dao.IconPathDao
import id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.database.entity.IconPathEntity

class IconPathRepository(application: Application) {
    private val appDatabase = AppDatabase.invoke(application.applicationContext)
    private val iconPathDao : IconPathDao? = appDatabase.iconPathDao()

    fun insertIconPath(iconPathEntity: IconPathEntity?){
        InsertAsyncTask(iconPathDao!!).execute(iconPathEntity)
    }

    fun getIconPathByIconName(icon : String): List<IconPathEntity>?{
        return iconPathDao?.getIconPathByIconName(icon)
    }

    private class InsertAsyncTask(dao: IconPathDao) :
            AsyncTask<IconPathEntity?, Void?, Void?>() {
        private val mAsyncTaskDao: IconPathDao = dao
        override fun doInBackground(vararg params: IconPathEntity?): Void? {
            mAsyncTaskDao.insertIconPath(params[0])
            return null
        }
    }

}