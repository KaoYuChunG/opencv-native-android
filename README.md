# opencv-native-android
## Step 1: Create a project
Build a project based on Android Native C++. This option will build a C/C++ environment that can execute programs calling C/C++ language through JNI (Java Native Interface).After the creation is completed, we can change the project window to the folder mode of `Project` to facilitate subsequent operations.

## Step 2: download OpenCV SDK
Enter OpenCV [官網](https://opencv.org/releases/) and download Android version。After downloading, go to the SDK folder and find the java folder (this folder will be used later).

## Step 3: import OpenCV SDK
Click File > New > Import Module on the upper toolbar. Select the java folder in the second step just now, click Next after completion and click Finish to import (can be changed to opencv).

After the import is completed, you can see that `opencv` is in the main directory folder, but what we imported is to be used as a module, not. So we have to manually change it to library.

## Step 4: dependencies add OpenCV
Click File > Project Structure ，enter Project Structure ，Click the Dependencies option on the left, choose app for Modules, and click [+] to choose `3 Module Dependency`.

Click on the `opencv` module that was just imported earlier. After clicking ok, our project can successfully use the OpenCV library.

We can open build.gradle(app) to see if `opencv` has been successfully imported into the implementation.

## Step 5: Add Native Libraries
open app the build.gradle，and add:
```
android {
  ...
  defaultConfig{
        .....
        externalNativeBuild {
            cmake {
                arguments "-DOpenCV_DIR=XXX:/XXX/OpenCV-android-sdk/sdk/native/jni",
                        "-DANDROID_TOOLCHAIN=clang",
                        "-DANDROID_STL=c++_static"
                targets "opencvcpp"
            }
        }
  }
  ....
  externalNativeBuild {
        cmake {
            path file('src/main/cpp/CMakeLists.txt')
            version '3.10.2'
        }
  }
}
```
