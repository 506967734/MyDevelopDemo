package com.demo.titlebar;

import android.widget.ImageView;
import android.widget.TextView;

public interface TitleBarInterface {
    /**
     * 设置标题
     * @param title
     */
    void setTitle(CharSequence title);

    /**
     * 设置背景
     * @param backgroundRes
     */
    void setBackground(int backgroundRes);

    /**
     * 设置左边图片
     * @param leftImgRes
     */
    void setLeftImg(int leftImgRes);

    /**
     * 设置左边text
     * @param leftText
     */
    void setLeftText(CharSequence leftText);

    /**
     * 设置右边图片
     * @param rightImgRes
     */
    void setRightImg(int rightImgRes);

    /**
     * 设置右边text
     * @param rightText
     */
    void setRightText(CharSequence rightText);

    /**
     * 只有标题
     */
    void setOnlyTitle();

    /**
     * 只有标题和返回键
     * @param leftImgRes
     */
    void setOnlyBackImg(int leftImgRes);

    /**
     * 设置标题大小
     * @param size
     */
    void setTitleSize(float size);

    /**
     * 设置标题颜色
     * @param color
     */
    void setTitleColor(int color);

    /**
     * 设置左边text字体大小
     * @param size
     */
    void setLeftTextSize(float size);

    /**
     * 设置左边字体颜色
     * @param color
     */
    void setLeftTextColor(int color,int type);
    /**
     * 设置右边字体大小
     * @param size
     */
    void setRightTextSize(float size);

    /**
     * 设置右边字体颜色
     * @param color
     */
    void setRightTextColor(int color,int type);

    /**
     * 设置右边背景色
     * @param backRes
     */
    void setRightTextBackground(int backRes);

    /**
     * 获取右边TextView
     * @return
     */
    TextView getRightTextView();

    /**
     * 获取右边ImageView
     * @return
     */
    ImageView getRightImageView();

    /**
     * 获取左边ImageView
     * @return
     */
    ImageView getLeftImageView();
}
