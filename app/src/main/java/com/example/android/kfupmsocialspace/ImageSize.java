package com.example.android.kfupmsocialspace;

import android.content.res.Resources;

//https://stackoverflow.com/questions/4743116/get-screen-width-and-height-in-android
public class ImageSize {


    //Returns an integer 3/5 of the screen width
    public static int getImageWidth() {
        return 3 * getScreenWidth() / 5;
    }

    //Returns an integer 2/5 of the screen height
    public static int getImageHeight() {
        return 2 * getScreenHeight() / 5;
    }


    //Returns the device's screen width in pixels
    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    //Returns the device's screen height in pixels
    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public static int getNewsImageHeight() {
        return getScreenWidth() / 2;
    }
}
