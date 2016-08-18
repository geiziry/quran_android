package com.quran.labs.androidquran.viewprovider;

/**
 * Created by HP_Spectre on 8/12/2016.
 */
public interface ViewBehavior {
    void onHandleGrabbed();
    void onHandleReleased();
    void onScrollStarted();
    void onScrollFinished();
}
