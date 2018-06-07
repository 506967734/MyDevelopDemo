package com.demo.common.utils;

/**
 * 字节处理
 * 
 */
public class ByteAction {
	// BIG-ENDIAN
	// LITTLE-ENDIAN
	private static char HEX_DIGEST[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	public static void set(byte[] b, byte v) {
		for (int i = 0; i < b.length; i++) {
			b[i] = v;
		}
	}

	public static String toHex(byte[] b) {
		String v = "";
		if (b.length > 0) {
			v += HEX_DIGEST[((b[0] >> 4) & 0x0f)];
			v += HEX_DIGEST[(b[0] & 0x0f)];

			for (int i = 1; i < b.length; i++) {
				v += HEX_DIGEST[((b[i] >> 4) & 0x0f)];
				v += HEX_DIGEST[(b[i] & 0x0f)];
			}
		}
		return v;
	}

	public static String toHexSpace(byte[] b) {
		String v = "";
		if (b.length > 0) {
			v += HEX_DIGEST[((b[0] >> 4) & 0x0f)];
			v += HEX_DIGEST[(b[0] & 0x0f)];

			for (int i = 1; i < b.length; i++) {
				v += " ";
				v += HEX_DIGEST[((b[i] >> 4) & 0x0f)];
				v += HEX_DIGEST[(b[i] & 0x0f)];
			}
		}
		return v;
	}

	private static int byteToInt(byte[] bBuf, int iOffset, int iCount, boolean bBig) {
		int iData = 0;
		if (bBuf != null && iOffset >= 0 && iCount > 0) {
			if (iCount + iOffset > bBuf.length) {
				iCount = bBuf.length - iOffset;
			}
			if (bBig) {
				for (int i = 0; i < iCount; i++) {
					iData <<= 8;
					iData += ((int) (bBuf[iCount + iOffset - i - 1]) & 0xff);
				}
			} else {
				for (int i = 0; i < iCount; i++) {
					iData <<= 8;
					iData += ((int) (bBuf[i + iOffset]) & 0xff);
				}
			}
		}
		return iData;
	}

	private static void intToByte(int iData, byte[] bBuf, int iOffset, int iCount, boolean bBigEdian) {
		if (bBuf != null && iOffset >= 0 && iCount > 0) {
			if (iCount + iOffset > bBuf.length) {
				iCount = bBuf.length - iOffset;
			}
			if (bBigEdian) {
				for (int i = 0; i < iCount; i++) {
					bBuf[i + iOffset] = (byte) (iData & 0xff);
					iData >>= 8;
				}
			} else {
				for (int i = 0; i < iCount; i++) {
					bBuf[iCount + iOffset - i - 1] = (byte) (iData & 0xff);
					iData >>= 8;
				}
			}
		}
	}

	public static byte[] intToByteB(int n) {
		byte[] b = new byte[4];
		intToByte(n, b, 0, 4, true);
		return b;
	}

	public static void intToByteB(int n, byte[] b, int i, int c) {
		intToByte(n, b, i, c, true);
	}

	public static void intToByteB(int n, byte[] b, int i) {
		intToByte(n, b, i, 4, true);
	}

	public static void intToByteB(int n, byte[] b) {
		intToByte(n, b, 0, 4, true);
	}

	public static int byteToIntB(byte[] bBuf, int iOffset, int iCount) {
		return byteToInt(bBuf, iOffset, iCount, true);
	}

	public static int byteToIntB(byte[] b) {
		return byteToInt(b, 0, 4, true);
	}

	public static int byteToIntB(byte[] b, int i) {
		return byteToInt(b, i, 4, true);
	}

	public static byte[] intToByteL(int n) {
		byte[] b = new byte[4];
		intToByte(n, b, 0, 4, false);
		return b;
	}

	public static void intToByteL(int n, byte[] b) {
		intToByte(n, b, 0, 4, false);
	}

	public static void intToByteL(int n, byte[] b, int i) {
		intToByte(n, b, i, 4, false);
	}

	public static int byteToIntL(byte[] b) {
		return byteToInt(b, 0, 4, false);
	}

	public static int byteToIntL(byte[] b, int i) {
		return byteToInt(b, i, 4, false);
	}

	public static int byteToIntL(byte[] b, int i, int c) {
		return byteToInt(b, i, c, false);
	}
}
