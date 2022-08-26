package com.kao.opencvcpp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraActivity;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivityCPP extends CameraActivity {

    private static String TAG = "OPENCV-LOG";
    private CameraBridgeViewBase mOpencvCameraView;

    private File cascadeFile;

    static {
        System.loadLibrary("opencvcpp");
    }

    public native void FindFeatures(long addrGray, long addrRGB);
    public native void InitFaceDelector(String filePath);
    public native void DetecFaces(long addrGray, long addrRGB);

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            Log.i("BENNN","Log  38");
            switch (status){
                case LoaderCallbackInterface.SUCCESS:{
                    mOpencvCameraView.enableView();
                }break;
                default:{
                    super.onManagerConnected(status);
                }break;
            }
        }
    };

    @Override
    protected List<? extends CameraBridgeViewBase> getCameraViewList() {
        return Collections.singletonList(mOpencvCameraView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main_cpp);


        readMXL();
        mOpencvCameraView = (CameraBridgeViewBase) findViewById(R.id.opencv_view);
        mOpencvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpencvCameraView.setCvCameraViewListener(setCvCameraViewListener);
    }

    private void readMXL(){
        try{
            cascadeFile = new File(getCacheDir(), "haarcascade_eye.xml");
            if(!cascadeFile.exists()){
                InputStream inputSteam = getAssets().open("haarcascade_eye.xml");
                FileOutputStream outputStream = new FileOutputStream(cascadeFile);
                byte[] buffer = new byte[2048];
                int bytesRead;
                while((bytesRead = inputSteam.read(buffer)) != -1){
                    outputStream.write(buffer,0, bytesRead);
                }
                inputSteam.close();
                outputStream.close();
            }

            InitFaceDelector(cascadeFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private CameraBridgeViewBase.CvCameraViewListener2 setCvCameraViewListener = new CameraBridgeViewBase.CvCameraViewListener2() {

//        private Mat mGrayMat;

        @Override
        public void onCameraViewStarted(int width, int height) {

//            mGrayMat = new Mat(height, width, CvType.CV_8UC1);

        }

        @Override
        public void onCameraViewStopped() {

//            mGrayMat.release();
        }

        @Override
        public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
            Mat input_rgba = inputFrame.rgba();
            Mat input_gray = inputFrame.gray();
//            MatOfPoint corners = new MatOfPoint();
//            Imgproc.goodFeaturesToTrack(input_gray, corners, 20, 0.01, 10, new Mat(), 3, 2);
//            Point[] cornersArr = corners.toArray();
//
//            for(int i=0; i < corners.rows(); i++){
//                Imgproc.circle(input_rgba, cornersArr[i],10, new Scalar(0,255,0), 2);
//            }
//            FindFeatures(input_gray.getNativeObjAddr(), input_rgba.getNativeObjAddr());




            DetecFaces(input_gray.getNativeObjAddr(), input_rgba.getNativeObjAddr());
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

                Core.rotate(input_rgba, input_rgba, Core.ROTATE_90_CLOCKWISE);

            }
            return input_rgba;
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        if(mOpencvCameraView != null){
            mOpencvCameraView.disableView();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!OpenCVLoader.initDebug()){
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, this, mLoaderCallback);
        }else{
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mOpencvCameraView != null){
            mOpencvCameraView.disableView();
        }
    }
}