package id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.opengl

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import android.os.SystemClock
import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.R
import id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.fragment.AboutFragment
import id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.fragment.OpenglFragment
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import kotlin.random.Random
import kotlin.random.asJavaRandom

class MyGLRenderer : GLSurfaceView.Renderer {
    private var mTriangle: Triangle? = null

    private val mMVPMatrix = FloatArray(16)
    private val mProjectionMatrix = FloatArray(16)
    private val mViewMatrix = FloatArray(16)
    private val mRotationMatrix = FloatArray(16)
    private var counter : Int = -25
    private var rotateFlag = true
    private var showAboutFlag = false
    var color1 = floatArrayOf(randFloat(0f,1f), randFloat(0f,1f), randFloat(0f,1f), randFloat(0f,1f))
    var color2 = floatArrayOf(randFloat(0f,1f), randFloat(0f,1f), randFloat(0f,1f), randFloat(0f,1f))
    private lateinit var fragmentManager : FragmentManager

    fun setFragmentManager(fragmentManager: FragmentManager){
        this.fragmentManager = fragmentManager
    }

    override fun onSurfaceCreated(unused: GL10, config: EGLConfig) {
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f)
        mTriangle = Triangle()
    }

    override fun onDrawFrame(unused: GL10) {
        val scratch = FloatArray(16)
        val time = counter
        val angle = 4f * time.toInt()
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT or GLES20.GL_DEPTH_BUFFER_BIT)
        Matrix.setLookAtM(mViewMatrix, 0, 0f, 0f, -3f, 0f, 0f, 0f, 0f, 1.0f, 0.0f)

        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0)


        if(rotateFlag){
            Matrix.setRotateM(mRotationMatrix, 0, angle, 0f, 0f, 0.1f)
            GLES20.glClearColor(color1[0],color1[1],color1[2],color1[3])
            mTriangle!!.color = color2
            counter++

        }else if (!rotateFlag){
            Matrix.setRotateM(mRotationMatrix, 0, angle, 0f, 0f, 0.1f)
            GLES20.glClearColor(color2[0],color2[1],color2[2],color2[3])
            mTriangle!!.color = color1
            counter--
        }
        if (counter == 25 || counter == -25){
            showAboutFlag = !showAboutFlag && !rotateFlag
            rotateFlag = !rotateFlag
            color1 = floatArrayOf(randFloat(0f,1f), randFloat(0f,1f), randFloat(0f,1f), randFloat(0f,1f))
            color2 = floatArrayOf(randFloat(0f,1f), randFloat(0f,1f), randFloat(0f,1f), randFloat(0f,1f))
        }
        if(showAboutFlag){
            val fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentContainer, AboutFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
        Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0)
        mTriangle!!.draw(scratch)
    }

    override fun onSurfaceChanged(unused: GL10, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
        val ratio = width.toFloat() / height
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1f, 1f, 3f, 7f)
    }

    fun loadShader(type: Int, shaderCode: String?): Int {
        val shader = GLES20.glCreateShader(type)
        GLES20.glShaderSource(shader, shaderCode)
        GLES20.glCompileShader(shader)
        return shader
    }
    private fun randFloat(min: Float, max: Float): Float {
        val rand = Random.asJavaRandom()
        return rand.nextFloat() * (max - min) + min
    }
}