package id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.database.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class InformasiCuacaEntity(
        @PrimaryKey @NonNull val daerah: String,
        @ColumnInfo(name = "cuaca") val cuaca: String?,
        @ColumnInfo(name = "koordinat") val koordinat: String?,
        @ColumnInfo(name = "temperatur_rerata") val temperaturRerata: Double?,
        @ColumnInfo(name = "temperatur_maks") val temperaturMaks: Double?,
        @ColumnInfo(name = "temperatur_min") val temperaturMin: Double?,
        @ColumnInfo(name = "tekanan") val tekanan: Long?,
        @ColumnInfo(name = "kelembapan") val kelembapan: Long?,
        @ColumnInfo(name = "kecepatan_angin") val kecepatanAngin: Double?,
        @ColumnInfo(name = "pengelihatan") val pengelihatan: Long?,
        @ColumnInfo(name = "icon") val icon: String
)
