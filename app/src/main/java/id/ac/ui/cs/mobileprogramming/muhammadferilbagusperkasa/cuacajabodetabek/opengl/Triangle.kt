package id.ac.ui.cs.mobileprogramming.muhammadferilbagusperkasa.cuacajabodetabek.opengl

import android.opengl.GLES20
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import kotlin.random.Random
import kotlin.random.asJavaRandom


class Triangle() {
    private val vertexShaderCode =
        "uniform mat4 uMVPMatrix;" +
                "attribute vec4 vPosition;" +
                "void main() {" +
                "  gl_Position = uMVPMatrix * vPosition;" +
                "}"
    private val fragmentShaderCode = ("precision mediump float;" +
            "uniform vec4 vColor;" +
            "void main() {" +
            "  gl_FragColor = vColor;" +
            "}")
    val COORDS_PER_VERTEX = 3
    var triangleCoords = floatArrayOf(
            0.0f, 0.5f, 0.0f,
            -0.05f, -0.1f, 0.0f,
            0.05f, -0.1f, 0.0f
    )
    private val vertexBuffer: FloatBuffer
    private val mProgram: Int
    private var mPositionHandle = 0
    private var mColorHandle = 0
    private var mMVPMatrixHandle = 0
    private val vertexCount = triangleCoords.size / COORDS_PER_VERTEX
    private val vertexStride = COORDS_PER_VERTEX * 4
    var color = floatArrayOf(0f, 0f, 0f, 1.0f)

    fun draw(mvpMatrix: FloatArray?) {
        GLES20.glUseProgram(mProgram)

        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition")

        GLES20.glEnableVertexAttribArray(mPositionHandle)

        GLES20.glVertexAttribPointer(
                mPositionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer
        )

        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor")

        GLES20.glUniform4fv(mColorHandle, 1, color, 0)

        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix")

        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0)

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount)

        GLES20.glDisableVertexAttribArray(mPositionHandle)
    }



    init {
        vertexBuffer =
                ByteBuffer.allocateDirect(triangleCoords.size * 4).run {
                    order(ByteOrder.nativeOrder())
                    asFloatBuffer().apply {
                        put(triangleCoords)
                        position(0)
                    }
                }
        val vertexShader : Int = MyGLRenderer().loadShader(
                GLES20.GL_VERTEX_SHADER, vertexShaderCode
        )
        val fragmentShader : Int = MyGLRenderer().loadShader(
                GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode
        )
        mProgram = GLES20.glCreateProgram()
        GLES20.glAttachShader(mProgram, vertexShader)
        GLES20.glAttachShader(mProgram, fragmentShader)
        GLES20.glLinkProgram(mProgram)
    }
}