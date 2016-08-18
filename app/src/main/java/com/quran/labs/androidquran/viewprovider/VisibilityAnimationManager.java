package com.quran.labs.androidquran.viewprovider;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.support.annotation.AnimatorRes;
import android.view.View;

import com.quran.labs.androidquran.R;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

/**
 * Created by HP_Spectre on 8/12/2016.
 */
public class VisibilityAnimationManager {

    protected final View view;

    protected AnimatorSet hideAnimator;
    protected AnimatorSet showAnimator;

    private float pivotXRelative;
    private float pivotYRelative;

    ////==========Constructor=============================================
    protected VisibilityAnimationManager(final View view, @AnimatorRes int showAnimator, @AnimatorRes int hideAnimator
            , float pivotXRelative, float pivotYRelative, int hideDelay) {
        this.view = view;
        this.pivotXRelative = pivotXRelative;
        this.pivotYRelative = pivotYRelative;

        this.hideAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(view.getContext(), hideAnimator);
        this.hideAnimator.setStartDelay(hideDelay);
        this.hideAnimator.setTarget(view);
        this.hideAnimator.addListener(new AnimatorListenerAdapter() {
            boolean wasCanceled;

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                wasCanceled = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (!wasCanceled) view.setVisibility(INVISIBLE);
                wasCanceled = false;
            }
        });

        this.showAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(view.getContext(), showAnimator);
        this.showAnimator.setTarget(view);

        updatePivot();
    }

    protected void updatePivot() {
        view.setPivotX(pivotXRelative * view.getMeasuredWidth());
        view.setPivotY(pivotYRelative * view.getMeasuredHeight());
    }

    ////==========Public Methods===========================
    public void show() {
        hideAnimator.cancel();
        if (view.getVisibility() == INVISIBLE) {
            view.setVisibility(VISIBLE);
            updatePivot();
            showAnimator.start();
        }
    }

    public void hide() {
        updatePivot();
        hideAnimator.start();
    }

    ////==========Abs abstract Builder class========================
    public static abstract class AbsBuilder<T extends VisibilityAnimationManager> {
        protected final View view;
        protected int showAnimatorResource = R.animator.fastscroll_default_show;
        protected int hideAnimatorResource = R.animator.fastscroll_default_hide;
        protected int hideDelay = 1000;
        protected float pivotX = 0.5f;
        protected float pivotY = 0.5f;

        public AbsBuilder(View view) {
            this.view = view;
        }

        public AbsBuilder<T> withShowAnimator(@AnimatorRes int showAnimatorResource) {
            this.showAnimatorResource = showAnimatorResource;
            return this;
        }

        public AbsBuilder<T> withHideDelay(int hideDelay) {
            this.hideDelay = hideDelay;
            return this;
        }

        public AbsBuilder<T> withPivotX(float pivotX) {
            this.pivotX = pivotX;
            return this;
        }
          public AbsBuilder<T> withPivotY(float pivotY) {
            this.pivotY = pivotY;
            return this;
        }

        public abstract T build();
    }

    /////============Builder class========================
    public static class Builder extends AbsBuilder<VisibilityAnimationManager> {
        public Builder(View view) {
            super(view);
        }

        @Override
        public VisibilityAnimationManager build() {
            return new VisibilityAnimationManager(view,showAnimatorResource,hideAnimatorResource,pivotX,pivotY,hideDelay);
        }
    }


}


