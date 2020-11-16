package id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.database.entity.LogApiCallEntity
import id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.repository.LogApiCallRepository

class LogApiCallViewModel(application: Application): AndroidViewModel(application) {
    private val logApiCallRepository = LogApiCallRepository(application)
    private val latestLogApiCall: LiveData<List<LogApiCallEntity>>? = logApiCallRepository.getLatestLogApiCall()

    fun getLatestLogApiCall() : LiveData<List<LogApiCallEntity>>?{
        return latestLogApiCall
    }

}