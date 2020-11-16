package id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.adapter

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.R
import id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.database.entity.InformasiCuacaEntity
import id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.fragment.InformasiCuacaDetailFragment
import id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.util.LoadIconUtil


class InformasiCuacaListAdapter(private val context: Context?, private val application: Application) : RecyclerView.Adapter<InformasiCuacaListAdapter.InformasiCuacaViewHolder>() {
    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var allInformasiCuacaList : List<InformasiCuacaEntity>? = null
    private lateinit var fragmentManager: FragmentManager

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InformasiCuacaViewHolder {
        val informasiCuacaView: View = mInflater.inflate(R.layout.recyclerview_item, parent, false)
        return InformasiCuacaViewHolder(informasiCuacaView)
    }

    override fun onBindViewHolder(holder: InformasiCuacaViewHolder, position: Int) {
        val current: InformasiCuacaEntity = allInformasiCuacaList!![position]
        val bitmap: Bitmap? = LoadIconUtil(application).loadIcon(current.icon)
        val resource : Resources? = context!!.resources
        holder.daerahTextView?.text = current.daerah
        holder.cuacaTextView?.text = resource!!.getString(R.string.cuaca, current.cuaca)
        holder.temperaturTextView?.text = resource.getString(R.string.temperatur_rerate, current.temperaturRerata.toString())
        holder.imageView?.setImageBitmap(bitmap)
        holder.cardView?.setOnClickListener(View.OnClickListener {
            val informasiCuacaDetailFragment = InformasiCuacaDetailFragment()
            informasiCuacaDetailFragment.setInformasiCuacaDetail(current)
            val fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentContainer, informasiCuacaDetailFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        })
    }

    fun setInformasiCuaca(informasiCuacaList: List<InformasiCuacaEntity>){
        allInformasiCuacaList = informasiCuacaList
        notifyDataSetChanged()
    }

    fun setFragmentManager(fragmentManager: FragmentManager){
        this.fragmentManager = fragmentManager
    }

    override fun getItemCount(): Int {
        if(allInformasiCuacaList != null){
            return allInformasiCuacaList!!.size
        } else return 0
    }
    class InformasiCuacaViewHolder(informasiCuacaView: View) : RecyclerView.ViewHolder(informasiCuacaView) {
        val daerahTextView: TextView? = informasiCuacaView.findViewById(R.id.textDaerah)
        val cuacaTextView: TextView? = informasiCuacaView.findViewById(R.id.textCuaca)
        val temperaturTextView: TextView? = informasiCuacaView.findViewById(R.id.textTemperatur)
        val imageView: ImageView? = informasiCuacaView.findViewById(R.id.imageView)
        val cardView : CardView? = informasiCuacaView.findViewById(R.id.card_view)
    }
}
