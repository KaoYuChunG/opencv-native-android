package com.kao.opencv

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.SurfaceView
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import org.opencv.android.*
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2
import org.opencv.core.Core
import org.opencv.core.Mat
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class MainActivity : CameraActivity() {

    private val TAG = "OPENCV-LOG"
    private lateinit var mOpencvCameraView: CameraBridgeViewBase

    private val mLoaderCallback: BaseLoaderCallback = object : BaseLoaderCallback(this) {
        override fun onManagerConnected(status: Int) {
            Log.i("BENNN", "Log  38")
            when (status) {
                SUCCESS -> {
                    mOpencvCameraView.enableView()
                }
                else -> {
                    super.onManagerConnected(status)
                }
            }
        }
    }

    override fun getCameraViewList(): List<CameraBridgeViewBase> {
        return Collections.singletonList(mOpencvCameraView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val toast = Toast.makeText(this,"check your open CV",Toast.LENGTH_SHORT)
        if(OpenCVLoader.initDebug()){
            toast.setGravity(Gravity.CENTER,0,0)
            toast.show()
        }else{
            toast.setGravity(Gravity.CENTER,0,0)
            toast.show()
        }

        mOpencvCameraView = findViewById<View>(R.id.opencv_view) as CameraBridgeViewBase
        mOpencvCameraView.visibility = SurfaceView.VISIBLE
        mOpencvCameraView.setCvCameraViewListener(setCvCameraViewListener)

    }

    private val setCvCameraViewListener: CvCameraViewListener2 = object : CvCameraViewListener2 {
        //        private Mat mGrayMat;
        override fun onCameraViewStarted(width: Int, height: Int) {

//            mGrayMat = new Mat(height, width, CvType.CV_8UC1);
        }

        override fun onCameraViewStopped() {

//            mGrayMat.release();
        }

        override fun onCameraFrame(inputFrame: CvCameraViewFrame): Mat {
            val input_rgba = inputFrame.rgba()
            val input_gray = inputFrame.gray()

            if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                Core.rotate(input_rgba, input_rgba, Core.ROTATE_90_CLOCKWISE)
            }
            return input_rgba
        }
    }

    override fun onPause() {
        super.onPause()
        if (mOpencvCameraView != null) {
            mOpencvCameraView.disableView()
        }
    }

    override fun onResume() {
        super.onResume()
        if (!OpenCVLoader.initDebug()) {
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, this, mLoaderCallback)
        } else {
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mOpencvCameraView != null) {
            mOpencvCameraView.disableView()
        }
    }
}