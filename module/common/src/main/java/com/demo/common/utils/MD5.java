package com.demo.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密
 * 
 */
public class MD5 {
	private static final char[] DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public static String getMD5(String strSource) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
			md5.update(strSource.getBytes());
			byte[] m = md5.digest();
			return ByteAction.toHex(m).toLowerCase();
		} catch (NoSuchAlgorithmException e) {
		}
		return "";
	}

	public static String md5(String text, String charsetName) {
//		if(TextUtils.isEmpty(text)){
//			return "";
//		}
		MessageDigest msgDigest = null;
		try {
			msgDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("System doesn't support MD5 algorithm.");
		}
		try {
			if (charsetName == null)
				msgDigest.update(text.getBytes());
			else
				msgDigest.update(text.getBytes(charsetName));
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("System doesn't support your  EncodingException.");
		}

		byte[] bytes = msgDigest.digest();
		String md5Str = new String(encodeHex(bytes));
		return md5Str;
	}

	public static String md5(String text) {
		return md5(text, null);
	}

	private static char[] encodeHex(byte[] data) {
		int l = data.length;
		char[] out = new char[l << 1];
		int i = 0;
		for (int j = 0; i < l; i++) {
			out[(j++)] = DIGITS[((0xF0 & data[i]) >>> 4)];
			out[(j++)] = DIGITS[(0xF & data[i])];
		}
		return out;
	}

}
