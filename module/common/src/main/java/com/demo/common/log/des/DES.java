package com.demo.common.log.des;



import com.demo.common.log.utils.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/**
 * Created by lenovo on 2016/7/6.
 */
public class DES {

    // 初始化向量，随机填充
    private static byte[] iv = { 1, 2, 3, 4, 5, 6, 7, 8 };

    /**
     * @param encryptString
     *            需要加密的明文
     * @param encryptKey
     *            秘钥
     * @return 加密后的密文
     * @throws Exception
     */
    public static String encryptDES(String encryptString, String encryptKey)
            throws Exception {
        // 实例化IvParameterSpec对象，使用指定的初始化向量
        IvParameterSpec zeroIv = new IvParameterSpec(iv);
        // 实例化SecretKeySpec类，根据字节数组来构造SecretKey
        SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");
        // 创建密码器
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        // 用秘钥初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
        // 执行加密操作
        byte[] encryptedData = cipher.doFinal(encryptString.getBytes());
//        return Base64.encodeToString(encryptedData, Base64.NO_PADDING);
        return Base64Utils.encode(encryptedData);
    }

    /****
     *
     * @param decrypString
     *            密文
     * @param decryptKey
     *            解密密钥
     * @return
     * @throws Exception
     */
    public static String decryptDES(String decrypString, String decryptKey)
            throws Exception {

//        byte[] byteMi = Base64.decode(decrypString, Base64.NO_PADDING);
        // 实例化IvParameterSpec对象，使用指定的初始化向量
        byte[] byteMi = Base64Utils.decode(decrypString);
        IvParameterSpec zeroIv = new IvParameterSpec(iv);
        // 实例化SecretKeySpec类，根据字节数组来构造SecretKey
        SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes(), "DES");
        // 创建密码器
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        // 用秘钥初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
        // 执行解密操作
        byte[] decryptedData = cipher.doFinal(byteMi);

        return new String(decryptedData);
    }

}
