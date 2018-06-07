package com.demo.titlebar;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.demo.common.R;


/**
 * 根布局
 */
public class RootLinearLayout extends LinearLayout{
    public RootLinearLayout(Context context) {
        super(context);
        init();
    }

    public RootLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RootLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 添加titlebar到根布局中
     */
    private void init(){
        addView(new TitleBar(getContext()),0,new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                getResources().getDimensionPixelSize(R.dimen.titlebar_height)));
    }
}
