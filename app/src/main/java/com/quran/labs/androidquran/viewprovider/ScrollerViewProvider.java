package com.quran.labs.androidquran.viewprovider;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quran.labs.androidquran.FastScroller;

/**
 * Created by HP_Spectre on 8/13/2016.
 */
public abstract class ScrollerViewProvider {
    private FastScroller scroller;
    private ViewBehavior handleBehavior;
    private ViewBehavior bubbleBehavior;

    public void setFastScroller(FastScroller scroller) {
        this.scroller = scroller;
    }

    protected Context getContext() {
        return scroller.getContext();
    }

    protected FastScroller getScroller() {
        return scroller;
    }

    public abstract View provideBubbleView(ViewGroup container);

    public abstract View provideHandleView(ViewGroup container);

    public abstract TextView provideBubbleTextView();

    public abstract int getBubbleOffset();

    protected abstract ViewBehavior provideHandleBehavior();

    protected abstract ViewBehavior provideBubbleBehavior();

    protected ViewBehavior getHandleBehavior() {
        if (handleBehavior==null) handleBehavior = provideHandleBehavior();
        return handleBehavior;
    }

    protected ViewBehavior getBubbleBehavior() {
        if (bubbleBehavior==null) bubbleBehavior = provideBubbleBehavior();
        return bubbleBehavior;
    }

    public void onScrollStarted() {
        if (getHandleBehavior()!=null)getHandleBehavior().onScrollStarted();
        if (getBubbleBehavior()!=null) getBubbleBehavior().onScrollStarted();
    }

    public void onScrollFinished(){
        if(getHandleBehavior()!=null) getHandleBehavior().onScrollFinished();
        if(getBubbleBehavior()!=null) getBubbleBehavior().onScrollFinished();
    }

    public void onHandleGrabbed(){
        if(getHandleBehavior()!=null) getHandleBehavior().onHandleGrabbed();
        if(getBubbleBehavior()!=null) getBubbleBehavior().onHandleGrabbed();
    }

    public void onHandleReleased(){
        if(getHandleBehavior()!=null) getHandleBehavior().onHandleReleased();
        if(getBubbleBehavior()!=null) getBubbleBehavior().onHandleReleased();
    }

}
