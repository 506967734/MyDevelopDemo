/*
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */
package com.demo.common.dialog;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;


public class LoadingProgressDrawable extends ImageView {

    AnimationDrawable animationDrawable;

    public LoadingProgressDrawable(Context context) {
        super(context);
    }

    public LoadingProgressDrawable(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadingProgressDrawable(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        animationDrawable = (AnimationDrawable) getDrawable();
    }

    public void start() {
        animationDrawable.start();
    }

    public void stop(boolean animate) {
        if (animate) {
            animationDrawable.stop();
        }
    }

    public void show() {
        start();
        setVisibility(VISIBLE);
    }

    public void dismiss() {
        stop(false);
        setVisibility(GONE);
    }

    public void onDestroy() {
        stop(false);
        setVisibility(GONE);
    }

}

