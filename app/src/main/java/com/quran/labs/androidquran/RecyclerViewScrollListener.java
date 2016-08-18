package com.quran.labs.androidquran;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP_Spectre on 8/13/2016.
 */
public class RecyclerViewScrollListener extends RecyclerView.OnScrollListener {
    private final FastScroller scroller;
    List<ScrollerListener> listeners = new ArrayList<>();
    int oldScrollState = RecyclerView.SCROLL_STATE_IDLE;

    //===========Constructor==============
    public RecyclerViewScrollListener(FastScroller scroller) {
        this.scroller = scroller;
    }

    //=============Public Methods=====================
    public void addScrollListener(ScrollerListener listener) {
        listeners.add(listener);
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (newState== RecyclerView.SCROLL_STATE_IDLE
                && oldScrollState!= RecyclerView.SCROLL_STATE_IDLE)
            scroller.getViewProvider().onScrollFinished();
        else if (newState != RecyclerView.SCROLL_STATE_IDLE
                && oldScrollState == RecyclerView.SCROLL_STATE_IDLE) {
            scroller.getViewProvider().onScrollStarted();
        }
        oldScrollState = newState;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (scroller.shouldUpdateHandlePosition()) {
            updateHandlePosition(recyclerView);
        }
    }

    void updateHandlePosition(RecyclerView rv) {
        float relativePos;
        if(scroller.isVertical()) {
            int offset = rv.computeVerticalScrollOffset();
            int extent = rv.computeVerticalScrollExtent();
            int range = rv.computeVerticalScrollRange();
            relativePos = offset / (float)(range - extent);
        } else {
            int offset = rv.computeHorizontalScrollOffset();
            int extent = rv.computeHorizontalScrollExtent();
            int range = rv.computeHorizontalScrollRange();
            relativePos = offset / (float)(range - extent);
        }
        scroller.setScrollerPosition(relativePos);
        notifyListeners(relativePos);
    }

    public void notifyListeners(float relativePos){
        for(ScrollerListener listener : listeners) listener.onScroll(relativePos);
    }

    public void addScrollerListener(ScrollerListener listener) {
        listeners.add(listener);
    }

    public interface ScrollerListener {
        void onScroll(float relativePos);
    }
}
