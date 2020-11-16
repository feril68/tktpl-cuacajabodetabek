package id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.database.AppDatabase
import id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.database.dao.InformasiCuacaDao
import id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.database.entity.InformasiCuacaEntity


class InformasiCuacaRepository(application: Application?) {
    private val appDatabase = AppDatabase.invoke(application!!.applicationContext)
    private val informasiCuacaDao : InformasiCuacaDao? = appDatabase.informasiCuacaDao()
    private val allInformasiCuaca : LiveData<List<InformasiCuacaEntity>>? = informasiCuacaDao?.getAllInformasiCuaca()

    fun insertInformasiCuaca(informasiCuacaEntity: InformasiCuacaEntity?) {
        InsertAsyncTask(informasiCuacaDao!!).execute(informasiCuacaEntity)
    }

    fun getAllInformasiCuaca() : LiveData<List<InformasiCuacaEntity>>?{
        return allInformasiCuaca
    }

    private class InsertAsyncTask(dao: InformasiCuacaDao) :
        AsyncTask<InformasiCuacaEntity?, Void?, Void?>() {
        private val mAsyncTaskDao: InformasiCuacaDao = dao
        override fun doInBackground(vararg params: InformasiCuacaEntity?): Void? {
            mAsyncTaskDao.insertInformasiCuaca(params[0])
            return null
        }
    }

}