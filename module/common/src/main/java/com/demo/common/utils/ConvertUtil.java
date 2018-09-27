package com.demo.common.utils;

import android.content.Context;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ConvertUtil {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
    public static int String2Int(String arg, int def) {
        try {
            return Integer.parseInt(arg);
        } catch (Exception e) {
            e.printStackTrace();
            return def;
        }
    }
    public static long getTimeMillis(String dateStr, String s) {
        if(TextUtils.isEmpty(dateStr)||TextUtils.isEmpty(s)){
            return System.currentTimeMillis();
        }else{
            SimpleDateFormat sdf = new SimpleDateFormat(s);
            try {
                long res = sdf.parse(dateStr).getTime();
                return res;
            } catch (ParseException e) {
                e.printStackTrace();
                return System.currentTimeMillis();
            }
        }
    }

 


    public static long String2Long(String strTime,long def) {
        if(TextUtils.isEmpty(strTime)){
            return def;
        }
        try {
            return Long.parseLong(strTime);
        } catch (Exception e) {
            return def;
        }
    }

    public static Float String2Float(String data, Float def) {
        try {
            return Float.parseFloat(data);
        }catch (Exception e){
            return def;
        }
    }

    public static double String2Double(String str, int def) {
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return def;
        }
    }

    /**
     * 如果是含有小数的double转化成String去掉小数
     * @param value
     * @return
     */
    public static String removeDecimal_0(double value) {
        int temp = (int)value;
        if(temp==value){
            return temp+"";
        }else{
            return value+"";
        }
    }
}
