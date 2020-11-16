package id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.database.entity.IconPathEntity

@Dao
interface IconPathDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIconPath(iconPathEntity: IconPathEntity?)

    @Query("SELECT * FROM IconPathEntity WHERE icon_name == :iconName")
    fun getIconPathByIconName(iconName: String) : List<IconPathEntity>

}