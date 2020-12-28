package id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.facebook.stetho.Stetho
import id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.api.AsyncTaskApiCall
import id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.broadcastreciever.NetworkChangeReceiver
import id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.database.entity.LogApiCallEntity
import id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.fragment.AboutFragment
import id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.fragment.InformasiCuacaListFragment
import id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.fragment.OpenglFragment
import id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.service.ApiCallSchedulerService
import id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.viewmodel.LogApiCallViewModel


class MainActivity : AppCompatActivity() {
    private var permissionStatus : Boolean = false
    private var initFragementAvailable : Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //reciever
        val filter = IntentFilter()
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        registerReceiver(NetworkChangeReceiver(), filter)
        //end reciever
        //steto
        Stetho.initializeWithDefaults(this)
        //end steto

        val versionAndroid = Build.VERSION.SDK_INT
        permissionStatus = checkPermission()
        if (!permissionStatus) {
            if (versionAndroid >= Build.VERSION_CODES.M) {
                requestUngrentedPermission()
            }
        }
        if (permissionStatus && !initFragementAvailable){
            initFragment()
            initFragementAvailable = true
        }
        else{
            val textLogApiCallView: TextView = findViewById(R.id.textLogApiCall)
            textLogApiCallView.text = resources.getText(R.string.permission_message)
        }
        val tentangButton : Button = findViewById(R.id.tentang)
        tentangButton.setOnClickListener(View.OnClickListener {
            supportFragmentManager.beginTransaction()
                .addToBackStack(null).replace(R.id.fragmentContainer, OpenglFragment()).commit()
        })
        val segarkanButton : Button = findViewById(R.id.segarkan)
        segarkanButton.setOnClickListener(View.OnClickListener {
            refreshButtonEvent()
        })
    }

    override fun onResume() {
        super.onResume()
        permissionStatus = checkPermission()
        if(permissionStatus && !initFragementAvailable){
            val intentStartService = Intent(this, ApiCallSchedulerService::class.java)
            startService(intentStartService)
            initFragment()
            initFragementAvailable = true
        }
        else if(!permissionStatus && !initFragementAvailable) {
            try{
                val intentStartService = Intent(this, ApiCallSchedulerService::class.java)
                stopService(intentStartService)
                val fragment : Fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)!!
                supportFragmentManager.beginTransaction().remove(fragment).commit()
                supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            }catch (exception : Exception){
                //do nothing
            }

        }
    }

    private fun initFragment(){
        val informasiCuacaListFragment = InformasiCuacaListFragment()
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction().add(R.id.fragmentContainer, informasiCuacaListFragment).commit()
        val logApiCallViewModel = ViewModelProviders.of(this).get(LogApiCallViewModel::class.java)
        logApiCallViewModel.getLatestLogApiCall()?.observe(this, Observer<List<LogApiCallEntity>> {
            val textLogApiCallView: TextView = findViewById(R.id.textLogApiCall)
            try {
                textLogApiCallView.text = this.resources.getString(R.string.pembaruan, it[0].timestamp.toString())
            } catch (e: Exception) {
                textLogApiCallView.text = null
            }
        })
    }

    private fun showTentangDialog(){
        val imageView = ImageView(this)
        imageView.setImageResource(R.drawable.logo)
        val alertBuilder = AlertDialog.Builder(this)
        alertBuilder.setTitle(resources.getText(R.string.tentang))
        alertBuilder.setMessage(resources.getText(R.string.isi_tentang))
        alertBuilder.setNeutralButton("Ok") { dialog, which ->
            dialog.cancel()
        }
        alertBuilder.setView(imageView)
        val showAlert = alertBuilder.create()
        showAlert.show()
    }

    private fun checkPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun requestUngrentedPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), 5054)
    }

    private fun refreshButtonEvent(){
        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val wifi = connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI)

        val mobile = connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE)

        if (wifi?.isConnected!! || mobile?.isConnected!!) {
            AsyncTaskApiCall(application).execute()
        }
        else{
            Toast.makeText(this, resources.getText(R.string.status_koneksi), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBackPressed() {
        val fragmentManager : FragmentManager = supportFragmentManager
        if (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val intentStartService = Intent(this, ApiCallSchedulerService::class.java)
        stopService(intentStartService)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            5054 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permissionStatus = true
                }
            }
        }
    }
}