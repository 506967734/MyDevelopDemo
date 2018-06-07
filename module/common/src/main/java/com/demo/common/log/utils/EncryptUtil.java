package com.demo.common.log.utils;


import android.text.TextUtils;

import com.demo.common.log.des.DES;
import com.demo.common.log.rsa.RSA;

import java.security.PublicKey;


/**
 * Created by lenovo on 2016/7/6.
 */
public class EncryptUtil {
    private static String mKey="";
    private static String mEncodeKey="";
    private static PublicKey mRSAPublicKey;
    public static void init(String yhdPubKey){
        try {
            mRSAPublicKey = RSA.loadPublicKey(yhdPubKey);
            mKey = KeyWordsUtil.getRandomString(8);
            if(mRSAPublicKey!=null){
                byte[] encryptByte = RSA.encryptData(mKey.getBytes(),mRSAPublicKey);
                mEncodeKey  = Base64Utils.encode(encryptByte);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static String encode(String msg, String pubKey){
        try {
            if(mRSAPublicKey==null||mKey==null){
                init(pubKey);
            }
            StringBuilder sb = new StringBuilder();
            String encodeMsg = msg;
            if(!TextUtils.isEmpty(mKey)&&!TextUtils.isEmpty(mEncodeKey)){
                encodeMsg = DES.encryptDES(msg,mKey);
            }
            sb.append("@#Skd@").append(mEncodeKey).append("@#Skd@").append(encodeMsg);
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msg;
    }
    public static String decode(String msg){
        return msg;
    }


}
