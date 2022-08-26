#include "jni.h"
#include "opencv2/core.hpp"
#include "opencv2/imgproc.hpp"
#include "opencv2/features2d.hpp"
#include "opencv2/objdetect.hpp"
#include "vector"
#include "string"

using namespace cv;

extern "C"{
    JNIEXPORT void JNICALL Java_com_kao_opencvcpp_MainActivityCPP_FindFeatures(JNIEnv *env, jobject , jlong addr_gray, jlong addr_rgba) {
        Mat *mGray = (Mat *) addr_gray;
        Mat *mRGBA = (Mat *) addr_rgba;

        std::vector<Point2f> corners;

        goodFeaturesToTrack(*mGray, corners, 20, 0.01, 10, Mat(),3, false, 0.04);

        for (int i=0; i < corners.size(); i++){
            circle(*mRGBA, corners[i], 10, Scalar(0,255,0),2);
        }
    }

    CascadeClassifier face_cascade;

    JNIEXPORT void JNICALL Java_com_kao_opencvcpp_MainActivityCPP_InitFaceDelector(JNIEnv* env, jobject , jstring jFilePath) {
        const char * jnamestr = env ->GetStringUTFChars(jFilePath,NULL);
        std::string filePath(jnamestr);
        face_cascade.load(filePath);
    }

    JNIEXPORT void JNICALL Java_com_kao_opencvcpp_MainActivityCPP_DetecFaces(JNIEnv *env, jobject , jlong addr_gray, jlong addr_rgba) {
        Mat *mGray = (Mat *) addr_gray;
        Mat *mRGBA = (Mat *) addr_rgba;

        std::vector<Rect> faces;
        face_cascade.detectMultiScale(*mGray, faces);

        for(int i=0; i < faces.size(); i++){
            rectangle(*mRGBA, Point(faces[i].x,faces[i].y), Point(faces[i].x+faces[i].width, faces[i].y+faces[i].height), Scalar(0, 255, 0),2);
        }
    }
}


