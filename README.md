# opencv-native-android
## Step 1: 建立專案
開啟 Android Studio 建立一個以 Android Native C++ 為基底的專案。這一個選項他會建構一個 C/C++ 環境能夠透過 JNI (Java Native Interface) ，執行呼叫 C/C++ 語言的程式。
建立完成後我們可以將專案視窗改成 `Project` 的資料夾模式，以利後續操作。

## Step 2: 下載 OpenCV SDK
進入 OpenCV [官網](https://opencv.org/releases/) 並下載 Android 版本。下載後進入 SDK 資料夾，找到 java 資料夾 (此資料夾之後會用到)。

## Step 3: 匯入 OpenCV SDK
點選上方工具列 File > New > Import Module。選擇剛剛第二步驟的java資料夾，完成後點選下一步並按完成匯入(可以改成opencv)。

匯入完成後可以看到 `opencv` 在主目錄資料夾中，但是我們匯入的是要當作 module 使用並非。因此我們要手動將它改成 library。

## Step 4: dependencies加入OpenCV
點選File > Project Structure ，進入 Project Structure 畫面，點選左邊 Dependencies 選項，Modules 選 app ，點 [+] 選 `3 Module Dependency`。

點選剛剛稍早匯入的 `opencv` module。點選 ok 後我們的專案就能成功使用 OpenCV 的函式庫囉。

我們可以開啟 build.gradle(app) 來查看 `opencv` 是否已經成功被匯入implementation。

## Step 5: 新增Native Libraries
開啟 app 的 build.gradle，增加:
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
