package id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.fragment

import android.content.res.Resources
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.R
import id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.database.entity.InformasiCuacaEntity
import id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.util.LoadIconUtil
import java.lang.Exception

class InformasiCuacaDetailFragment : Fragment() {
    private lateinit var informasiCuacaDetail: InformasiCuacaEntity

    fun setInformasiCuacaDetail(informasiCuacaEntity: InformasiCuacaEntity){
        this.informasiCuacaDetail = informasiCuacaEntity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.informasi_cuaca_detail, container, false)
        try{
            val resource : Resources? = context!!.resources
            view.findViewById<TextView>(R.id.textDetailDaerah).text =
                    informasiCuacaDetail.daerah

            view.findViewById<TextView>(R.id.textDetailCuaca).text =
                    resource!!.getString(R.string.cuaca, informasiCuacaDetail.cuaca)

            view.findViewById<TextView>(R.id.textDetailKelembaman).text =
                    resource.getString(R.string.kelembapan, informasiCuacaDetail.kelembapan.toString())

            view.findViewById<TextView>(R.id.textDetailKoordinat).text =
                    resource.getString(R.string.koordinat, informasiCuacaDetail.koordinat)

            view.findViewById<TextView>(R.id.textDetailPengelihatan).text =
                    resource.getString(R.string.pengelihatan, informasiCuacaDetail.pengelihatan.toString())

            view.findViewById<TextView>(R.id.textDetailTekanan).text =
                    resource.getString(R.string.tekanan, informasiCuacaDetail.tekanan.toString())

            view.findViewById<TextView>(R.id.textDetailTemperatur).text =
                    resource.getString(R.string.temperatur_rerate, informasiCuacaDetail.temperaturRerata.toString())

            view.findViewById<TextView>(R.id.textDetailTemperaturMax).text =
                    resource.getString(R.string.temperatur_maks, informasiCuacaDetail.temperaturMaks.toString())

            view.findViewById<TextView>(R.id.textDetailTemperaturMin).text =
                    resource.getString(R.string.temperatur_min, informasiCuacaDetail.temperaturMin.toString())

            val bitmap1: Bitmap? = LoadIconUtil(activity!!.application).loadIcon(informasiCuacaDetail.icon)
            view.findViewById<ImageView>(R.id.imageViewDetail).setImageBitmap(bitmap1)
        }catch (exception :Exception){
            //do nothing
        }

        return view
    }
}