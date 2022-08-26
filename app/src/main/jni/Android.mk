LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := MyLib
LOCAL_SRC_FILES := main.c

include $(BUILD_SHARED_LIBRARY)

APP_ABI := armeabi-v7a
