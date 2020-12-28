package id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.fragment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.R


class AboutFragment : Fragment(){
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.about_me, container, false)
        val getIntentReciever: BroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent) {
                var text = intent.extras!!.getInt("counter").toString()
                view.findViewById<TextView>(R.id.apicall).text = "ApiCallSchedulerService run for $text"
            }
        }
        context!!.registerReceiver(getIntentReciever, IntentFilter("apicall_counter"))
        view.findViewById<TextView>(R.id.hello).text = helloName(resources.getString(R.string.nama))
        view.findViewById<TextView>(R.id.random).text = resources.getString(R.string.nomor_hoki, luckyNumber().toString())
        return view
    }

    private external fun helloName(string: String): String?
    private external fun luckyNumber(): Int

    companion object {
        init {
            System.loadLibrary("hello-jni")
        }
    }
}