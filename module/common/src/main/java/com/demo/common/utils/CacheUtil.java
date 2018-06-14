package com.demo.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

import com.demo.common.constant.SPlConstant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

/**
 * 该类用于全局变量存放 要求变量都继承至Serializable
 */
public class CacheUtil {

    private static Logger logger = LoggerFactory.getLogger("CommonVariable");

    public final static String FIRST_PERMISSION_REQUEST_REFUSE = "first_permission_request";//第一次启动权限请求失败
    public static final String uuid = "uuid";

    /**
     * desc:保存对象
     *
     * @param context
     * @param key
     * @param obj     要保存的对象，只能保存实现了serializable的对象 modified:
     */
    public static void saveObject(Context context, String key, Object obj) {
        try {
            // 保存对象
            Editor sharedata = context.getSharedPreferences(SPlConstant.LOGIN_INFO, 0).edit();
            // 先将序列化结果写到byte缓存中，其实就分配一个内存空间
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(bos);
            // 将对象序列化写入byte缓存
            os.writeObject(obj);
            // 将序列化的数据转为16进制保存
            String bytesToHexString = bytesToHexString(bos.toByteArray());
            // 保存该16进制数组
            sharedata.putString(key, bytesToHexString);
            sharedata.commit();
        } catch (IOException e) {
            logger.warn("保存obj失败", e);
        }
    }

    public static void cleaData(Context context, String key) {
        Editor editor = context.getSharedPreferences(SPlConstant.LOGIN_INFO, 0).edit();
        editor.remove(key);
        editor.commit();
    }

    /**
     * desc:将数组转为16进制
     *
     * @param bArray
     * @return modified:
     */
    public static String bytesToHexString(byte[] bArray) {
        if (bArray == null) {
            return null;
        }
        if (bArray.length == 0) {
            return "";
        }
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 保存对象
     *
     * @param context
     * @param key
     */
    public static void putString(Context context, String key, String value) {
        if (context == null) {
            return;
        }
        SharedPreferences sharedata = context.getSharedPreferences(SPlConstant.realNameAuth, 0);
        if (sharedata != null) {
            Editor editor = sharedata.edit();
            editor.putString(key, value);
            editor.commit();
        }
    }

    /**
     * 获取对象
     *
     * @param context
     * @param key
     */
    public static String getString(Context context, String key) {
        if (context == null) {
            return "";
        }
        SharedPreferences sharedata = context.getSharedPreferences(SPlConstant.realNameAuth, 0);
        if (sharedata != null) {
            return sharedata.getString(key, "");
        }
        return "";

    }

    /**
     * 获取对象
     *
     * @param context
     * @param key
     */
    public static String getString(Context context, String key, String defvalue) {
        if (context == null) {
            return "";
        }
        SharedPreferences sharedata = context.getSharedPreferences(SPlConstant.realNameAuth, 0);
        if (sharedata != null) {

            return sharedata.getString(key, defvalue);

        }
        return "";

    }


    public static void putBoolean(Context context, String key, boolean value) {
        if (context == null) {
            return;
        }
        SharedPreferences sharedata = context.getSharedPreferences(SPlConstant.realNameAuth, 0);
        if (sharedata != null) {
            Editor editor = sharedata.edit();
            editor.putBoolean(key, value);
            editor.commit();
        }
    }

    public static boolean getBoolean(Context context, String key) {
        if (context == null) {
            return false;
        }
        SharedPreferences sharedata = context.getSharedPreferences(SPlConstant.realNameAuth, 0);
        if (sharedata != null) {
            return sharedata.getBoolean(key, false);

        }
        return false;

    }

    /**
     * desc:获取保存的Object对象
     *
     * @param context
     * @param key
     * @return modified:
     */
    public static Object readObject(Context context, String key) {
        try {
            SharedPreferences sharedata = context.getSharedPreferences(SPlConstant.LOGIN_INFO, 0);
            if (sharedata.contains(key)) {
                String string = sharedata.getString(key, "");
                if (TextUtils.isEmpty(string)) {
                    return null;
                } else {
                    // 将16进制的数据转为数组，准备反序列化
                    byte[] stringToBytes = StringToBytes(string);
                    ByteArrayInputStream bis = new ByteArrayInputStream(stringToBytes);
                    ObjectInputStream is = new ObjectInputStream(bis);
                    // 返回反序列化得到的对象
                    Object readObject = is.readObject();
                    return readObject;
                }
            }
        } catch (StreamCorruptedException e) {
            logger.error("", e);
        } catch (IOException e) {
            logger.error("", e);
        } catch (ClassNotFoundException e) {
            logger.error("", e);
        }
        // 所有异常返回null
        return null;

    }

    /**
     * desc:将16进制的数据转为数组
     *
     * @param data
     * @return modified:
     */
    public static byte[] StringToBytes(String data) {
        String hexString = data.toUpperCase().trim();
        if (hexString.length() % 2 != 0) {
            return null;
        }
        byte[] retData = new byte[hexString.length() / 2];
        for (int i = 0; i < hexString.length(); i++) {
            int int_ch; // 两位16进制数转化后的10进制数
            char hex_char1 = hexString.charAt(i); // //两位16进制数中的第一位(高位*16)
            int int_ch1;
            if (hex_char1 >= '0' && hex_char1 <= '9')
                int_ch1 = (hex_char1 - 48) * 16; // // 0 的Ascll - 48
            else if (hex_char1 >= 'A' && hex_char1 <= 'F')
                int_ch1 = (hex_char1 - 55) * 16; // // A 的Ascll - 65
            else
                return null;
            i++;
            char hex_char2 = hexString.charAt(i); // /两位16进制数中的第二位(低位)
            int int_ch2;
            if (hex_char2 >= '0' && hex_char2 <= '9')
                int_ch2 = (hex_char2 - 48); // // 0 的Ascll - 48
            else if (hex_char2 >= 'A' && hex_char2 <= 'F')
                int_ch2 = hex_char2 - 55; // // A 的Ascll - 65
            else
                return null;
            int_ch = int_ch1 + int_ch2;
            retData[i / 2] = (byte) int_ch;// 将转化后的数放入Byte里
        }
        return retData;
    }

}
