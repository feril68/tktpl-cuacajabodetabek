package id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.database.AppDatabase
import id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.database.dao.LogApiCallDao
import id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.database.entity.LogApiCallEntity

class LogApiCallRepository(application: Application?) {
    private val appDatabase = AppDatabase.invoke(application!!.applicationContext)
    private val logApiCallDao : LogApiCallDao? = appDatabase.logApiCallDao()
    private val latestLogApiCall : LiveData<List<LogApiCallEntity>>? = logApiCallDao?.getLatestLogApiCall()

    fun insertLogApiCall(logApiCallEntity: LogApiCallEntity?){
        InsertAsyncTask(logApiCallDao!!).execute(logApiCallEntity)
    }

    fun getLatestLogApiCall() : LiveData<List<LogApiCallEntity>>?{
        return latestLogApiCall
    }

    private class InsertAsyncTask(dao: LogApiCallDao) :
        AsyncTask<LogApiCallEntity?, Void?, Void?>() {
        private val mAsyncTaskDao: LogApiCallDao = dao
        override fun doInBackground(vararg params: LogApiCallEntity?): Void? {
            mAsyncTaskDao.insertLogApiCall(params[0])
            return null
        }
    }
}