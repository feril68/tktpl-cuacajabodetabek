package id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.database.entity.LogApiCallEntity

@Dao
interface LogApiCallDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLogApiCall(logApiCallEntity: LogApiCallEntity?)

    @Query("SELECT * FROM LogApiCallEntity")
    fun getAllLogApiCall(): LiveData<List<LogApiCallEntity>>

    @Query("SELECT * FROM LogApiCallEntity ORDER BY id DESC LIMIT 1")
    fun getLatestLogApiCall(): LiveData<List<LogApiCallEntity>>
}