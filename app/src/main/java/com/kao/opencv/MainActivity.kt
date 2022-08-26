package com.kao.opencv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import org.opencv.android.OpenCVLoader

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
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

    }
}