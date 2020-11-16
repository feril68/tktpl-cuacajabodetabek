package id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.database.entity.InformasiCuacaEntity

@Dao
interface InformasiCuacaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInformasiCuaca(informasiCuaca: InformasiCuacaEntity?)

    @Update
    fun updateInformasiCuaca(informasiCuaca: InformasiCuacaEntity)

    @Delete
    fun deleteInformasiCuaca(informasiCuaca: InformasiCuacaEntity)

    @Query("SELECT * FROM InformasiCuacaEntity WHERE daerah == :daerah")
    fun getInformasiCuacaByDaerah(daerah: String): List<InformasiCuacaEntity>

    @Query("SELECT * FROM InformasiCuacaEntity")
    fun getAllInformasiCuaca(): LiveData<List<InformasiCuacaEntity>>
}