package id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.database.entity.InformasiCuacaEntity
import id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.repository.InformasiCuacaRepository

class InformasiCuacaViewModel(application: Application): AndroidViewModel(application) {
    private val informasiCuacaRepository = InformasiCuacaRepository(application)
    private val informasiCuacaList: LiveData<List<InformasiCuacaEntity>>?

    init {
        informasiCuacaList = informasiCuacaRepository.getAllInformasiCuaca()
    }

    fun getAllInformasiCuaca() : LiveData<List<InformasiCuacaEntity>>? {
        return informasiCuacaList
    }
}