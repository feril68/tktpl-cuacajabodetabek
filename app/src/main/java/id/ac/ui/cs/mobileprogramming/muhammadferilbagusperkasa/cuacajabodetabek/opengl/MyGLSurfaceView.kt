package id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.opengl

import android.content.Context
import android.opengl.GLSurfaceView
import androidx.fragment.app.FragmentManager

class MyGLSurfaceView(context: Context?, fragmentManager: FragmentManager) : GLSurfaceView(context) {
    private val mRenderer: MyGLRenderer

    init {
        setEGLContextClientVersion(2)
        mRenderer = MyGLRenderer()
        mRenderer.setFragmentManager(fragmentManager)
        setRenderer(mRenderer)
    }
}