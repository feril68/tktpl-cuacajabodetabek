package id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class IconPathEntity (
   @PrimaryKey @ColumnInfo(name = "icon_name") val iconName: String,
   @ColumnInfo(name ="content_path") val contentPath: String
)