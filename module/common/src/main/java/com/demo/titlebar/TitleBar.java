package com.demo.titlebar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.demo.common.R;


public class TitleBar extends FrameLayout implements TitleBarInterface{

    Context mContext;
    private TitleBarLeftClickedListener titleBarLeftClickedListener;
    private TitleBarRightClickedListener titleBarRightClickedListener;
    public TextView tileView;
    public TextView rightTextV;
    private TextView titleBarBackTv;
    private ImageView leftImg;
    private ImageView rightImg;

    public static int TITLE_BAR_TEXT_COLOR = 1;
    public static int TITLE_BAR_TEXT_COLOR_LIST = 2;

    public TitleBar(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init() {
        this.setId(R.id.titlebar_view);
        this.removeAllViewsInLayout();
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.title_bar_view,null);
        addView(view, new RelativeLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        initView(view);
        setListener();
    }

    private void initView(View v) {
        titleBarBackTv = (TextView) v.findViewById(R.id.titlebar_back_tv);
        rightTextV = (TextView) v.findViewById(R.id.titlebar_right_tv);
        leftImg = (ImageView) v.findViewById(R.id.titlebar_back_img);
        rightImg = (ImageView) v.findViewById(R.id.titlebar_right_img);
    }

    private void setListener() {
        titleBarBackTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(titleBarLeftClickedListener!=null){
                    titleBarLeftClickedListener.onLeftClicked(v);
                }
            }
        });
        leftImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(titleBarLeftClickedListener!=null){
                    titleBarLeftClickedListener.onLeftClicked(v);
                }
            }
        });
        rightTextV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(titleBarRightClickedListener!=null){
                    titleBarRightClickedListener.onRightClicked(v);
                }
            }
        });
        rightImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(titleBarRightClickedListener!=null){
                    titleBarRightClickedListener.onRightClicked(v);
                }
            }
        });
    }


    @Override
    public void setTitle(CharSequence title) {
        tileView = ((TextView)findViewById(R.id.titlebar_title_tv));
        tileView.setText(title);
    }

    @Override
    public void setBackground(int backgroundRes) {
        this.setBackgroundResource(backgroundRes);
    }

    @Override
    public void setLeftImg(int leftImgRes) {
        titleBarBackTv.setVisibility(View.GONE);
        leftImg.setVisibility(View.VISIBLE);
        leftImg.setImageResource(leftImgRes);
    }

    @Override
    public void setLeftText(CharSequence leftText) {

        titleBarBackTv.setVisibility(View.VISIBLE);
        leftImg.setVisibility(View.GONE);
        titleBarBackTv.setText(leftText);
    }

    @Override
    public void setRightImg(int rightImgRes) {
        rightTextV.setVisibility(View.GONE);
        rightImg.setVisibility(View.VISIBLE);
        rightImg.setImageResource(rightImgRes);
    }

    @Override
    public void setRightText(CharSequence rightText) {
        rightImg.setVisibility(View.GONE);
        rightTextV.setVisibility(View.VISIBLE);
        rightTextV.setText(rightText);
    }

    @Override
    public void setOnlyTitle() {
        rightImg.setVisibility(View.GONE);
        rightTextV.setVisibility(View.GONE);
        leftImg.setVisibility(View.GONE);
        titleBarBackTv.setVisibility(View.GONE);
    }

    @Override
    public void setOnlyBackImg(int leftImgRes) {
        rightImg.setVisibility(View.GONE);
        rightTextV.setVisibility(View.GONE);
        leftImg.setVisibility(View.VISIBLE);
        titleBarBackTv.setVisibility(View.GONE);
        leftImg.setImageResource(leftImgRes);
    }

    @Override
    public void setTitleSize(float size) {
        ((TextView)findViewById(R.id.titlebar_title_tv)).setTextSize(size);
    }

    @Override
    public void setTitleColor(int color) {
        ((TextView)findViewById(R.id.titlebar_title_tv)).setTextColor(color);
    }

    @Override
    public void setLeftTextSize(float size) {
        titleBarBackTv.setTextSize(size);
    }

    @Override
    public void setLeftTextColor(int color,int type) {

        if(type==TITLE_BAR_TEXT_COLOR){
            int colorId;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                colorId = mContext.getResources().getColor(color, mContext.getTheme());
            } else {
                colorId =  mContext.getResources().getColor(color);
            }
            titleBarBackTv.setTextColor(colorId);
        }else if(type==TITLE_BAR_TEXT_COLOR_LIST){
            ColorStateList colorList;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                colorList = mContext.getResources().getColorStateList(color, mContext.getTheme());
            } else {
                colorList =  mContext.getResources().getColorStateList(color);
            }
            titleBarBackTv.setTextColor(colorList);
        }


    }

    @Override
    public void setRightTextSize(float size) {
        rightTextV.setTextSize(size);
    }

    @Override
    public void setRightTextColor(int color,int type) {

        if(type==TITLE_BAR_TEXT_COLOR){
            int colorId;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                colorId = mContext.getResources().getColor(color, mContext.getTheme());
            } else {
                colorId =  mContext.getResources().getColor(color);
            }
            rightTextV.setTextColor(colorId);
        }else if(type==TITLE_BAR_TEXT_COLOR_LIST){
            ColorStateList colorList;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                colorList = mContext.getResources().getColorStateList(color, mContext.getTheme());
            } else {
                colorList =  mContext.getResources().getColorStateList(color);
            }
            rightTextV.setTextColor(colorList);
        }

    }

    @Override
    public void setRightTextBackground(int backRes) {
        rightTextV.setBackgroundResource(backRes);
    }

    @Override
    public TextView getRightTextView() {
        return rightTextV;
    }

    @Override
    public ImageView getRightImageView() {
        return rightImg;
    }

    @Override
    public ImageView getLeftImageView() {
        return leftImg;
    }

    public void setOnTitleBarLeftClickedListener(TitleBarLeftClickedListener titleBarLeftClickedListener){
        this.titleBarLeftClickedListener = titleBarLeftClickedListener;
    }
    public void setOnTitleBarRightClickedListener(TitleBarRightClickedListener titleBarRightClickedListener){
        this.titleBarRightClickedListener = titleBarRightClickedListener;
    }

    public interface TitleBarRightClickedListener{
        void onRightClicked(View v);
    }
    public interface TitleBarLeftClickedListener{
        void onLeftClicked(View v);
    }
}
