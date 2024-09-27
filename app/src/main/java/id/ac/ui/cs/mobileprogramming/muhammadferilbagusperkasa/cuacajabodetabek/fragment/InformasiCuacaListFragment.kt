package id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.R
import id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.adapter.InformasiCuacaListAdapter
import id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.database.entity.InformasiCuacaEntity
import id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.viewmodel.InformasiCuacaViewModel

class InformasiCuacaListFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var informasiCuacaListAdapter: InformasiCuacaListAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e("cek fragment 1", "masuk")
        val view = inflater.inflate(R.layout.informasi_cuaca_list, container, false)
        recyclerView = view.findViewById(R.id.recyclerviewCuaca)
        informasiCuacaListAdapter = InformasiCuacaListAdapter(view.context, activity!!.application)
        informasiCuacaListAdapter.setFragmentManager(fragmentManager!!)
        recyclerView.adapter = informasiCuacaListAdapter
        recyclerView.layoutManager = LinearLayoutManager(view.context)

        val informasiCuacaViewModel = ViewModelProviders.of(this).get(InformasiCuacaViewModel::class.java)
        informasiCuacaViewModel.getAllInformasiCuaca()?.observe(
            this,
            Observer<List<InformasiCuacaEntity>> {
                try {
                    informasiCuacaListAdapter.setInformasiCuaca(it)
                } catch (exception: Exception) {
                    exception.printStackTrace()
                }
            })
        return view
    }
}