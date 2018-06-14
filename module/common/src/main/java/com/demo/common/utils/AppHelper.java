package com.demo.common.utils;

import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.os.Process;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;



public class AppHelper {

    private static Logger logger = LoggerFactory.getLogger("AppHelper");
    public static File RPC_TEST_FILE;

    /*1、测试环境，开发环境，自定义环境 return true  2、正式环境，预发布环境视为 return false*/
    public static boolean isDebugable(@NonNull Context context) {
        boolean debug = isApkDebugable(context);
        if (debug) {
            initRpcFile(context);
            String content = FileHelper.readContentFromFile(RPC_TEST_FILE, "utf-8");
            if ("online".equals(content)) {
                return false;
            } else {
                return true;
            }
        } else {
            //强制使用正式环境
            return false;
        }
    }

    @Deprecated
    public static boolean isDebugableOld(@NonNull Context context) {
        boolean debug = isApkDebugable(context);
        if (debug) {
            initRpcFile(context);
            String content = FileHelper.readContentFromFile(RPC_TEST_FILE, "utf-8");
            if ("online".equals(content) || "pre_online".equals(content)) {
                return false;
            } else {
                return true;
            }
        } else {
            //强制使用正式环境
            return false;
        }
    }

    private static void initRpcFile(Context context) {
        if (RPC_TEST_FILE == null) {
            RPC_TEST_FILE = new File(FileHelper.getAppExternalDir(context), "chuanhua_setting_dir" + File.separator + "RPC_TEST_FILE.txt");
        }
    }

    public static boolean isApkDebugable(@NonNull Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            boolean isApkDebugable = (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
            if (isApkDebugable) {
                initRpcFile(context);
            }
            return isApkDebugable;
        } catch (Exception e) {
            logger.error("isApkDebugable", e);
        }
        return false;
    }

    public static boolean isStorageAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static boolean isNetWorkAvailable(@NonNull Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netWorkInfo = cm.getActiveNetworkInfo();
            return (netWorkInfo != null && netWorkInfo.isAvailable());
        } catch (Exception e) {
            logger.error("isNetWorkAvailable", e);
        }
        return false;
    }

    public static String getPackageName(@NonNull Context context) {
        try {
            return context.getPackageName();
        } catch (Exception e) {
            logger.error("get package name fail", e);
        }
        return "";
    }


    public static String getProcess(@NonNull Context context) {
        try {
            int pid = Process.myPid();
            ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> list = mActivityManager.getRunningAppProcesses();
            if (list != null && !list.isEmpty()) {
                for (ActivityManager.RunningAppProcessInfo appProcess : list) {
                    if (appProcess.pid == pid) {
                        return appProcess.processName;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean isMainProcess(@NonNull Context context) {
        String processName = getProcess(context);
        String packageName = getPackageName(context);
        return !TextUtils.isEmpty(processName) && TextUtils.equals(processName, packageName);
    }

    /**
     * 获取设备唯一UUID,生成的规则是
     * MD5 & HEX( WLAN MAC + Bluetooth MAC + Secure.ANDROID_ID + IMEI + android.os.Build.SERIAL)
     *
     * @return
     */
    public static String getUUID(@NonNull Context context) {
        logger.debug("getUUID...");
        String uuid = null;
        if (!TextUtils.isEmpty(CacheUtil.getString(context, CacheUtil.uuid))) {
            uuid = CacheUtil.getString(context, CacheUtil.uuid);
        } else {
            String fileName = "chuanhuauuid";
            //从/data/data/${package}/chxuuid/chuanhuauuid读取
            String internalUUID = null;
            File internalDir = context.getDir("chxuuid", Context.MODE_PRIVATE);
            if (!internalDir.exists()) {
                internalDir.mkdirs();
            }
            File internalFile = new File(internalDir, fileName);
            if (internalFile.exists()) {
                internalUUID = FileHelper.readContentFromFile(internalFile, FileHelper.DEFAULT_CHARSET);
            }
            logger.info("internalUUID:{}, internalFile:{}", internalUUID, internalFile.getAbsolutePath());
            //从/mnt/sdcard/${package}/chxuuid/chuanhuauuid读取
            boolean sdcardCanUse = false;
            try {
                sdcardCanUse = Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
            } catch (Exception e) {
                logger.error("getExternalStorageState++", e);
            }
            File externalDir = null;
            File externalFile = null;
            String externalUUID = null;
            if (sdcardCanUse) {
                File appExternalDir = FileHelper.getAppExternalDir(context);
                externalDir = new File(appExternalDir, "chxuuid");
                if (!externalDir.exists()) {
                    externalDir.mkdirs();
                }
                externalFile = new File(externalDir, fileName);
                if (externalFile.exists()) {
                    externalUUID = FileHelper.readContentFromFile(externalFile, FileHelper.DEFAULT_CHARSET);
                }
                logger.info("externalUUID:{}, externalFile:{}", externalUUID, externalFile.getAbsolutePath());
            }
            //data下面的数据是否可用
            if (!TextUtils.isEmpty(internalUUID) && internalUUID.length() == 32) {//data下面数据
                uuid = internalUUID;
            } else if (!TextUtils.isEmpty(externalUUID) && externalUUID.length() == 32) {//sdcard下面数据
                uuid = externalUUID;
            }

            //两个地方都没有,那么就直接生成一个
            if (TextUtils.isEmpty(uuid)) {
                uuid = generateUUID(context);
            }

            if (!uuid.equalsIgnoreCase(internalUUID)) {
                boolean result = FileHelper.writeContentToFile(internalFile, uuid, FileHelper.DEFAULT_CHARSET);
                logger.info("writeContentToFile, result:{}, uuid : {} => {}", result, uuid, internalFile.getAbsolutePath());
            }
            if (sdcardCanUse && externalFile != null && !uuid.equalsIgnoreCase(externalUUID)) {
                boolean result = FileHelper.writeContentToFile(externalFile, uuid, FileHelper.DEFAULT_CHARSET);
                logger.info("writeContentToFile, result:{}, uuid : {} => {}", result, uuid, externalFile.getAbsolutePath());
            }
            CacheUtil.putString(context, CacheUtil.uuid, uuid);
        }
        return uuid;
    }

    /**
     * 通过执行shell脚本来获取Mac地址<br/>
     *
     * @return
     */
    public static String getWlanMACByShell() {
        //先执行cat /sys/class/net/wlan0/address
        // 命令试一下,看看能不能获取到
        String macSerial = null;
        try {
            String str = "";
            java.lang.Process pp = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address");
            InputStreamReader reader = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(reader);

            //避免进入死循环,这个文件一般情况下只有一行
            for (int i = 0; null != str && i < 100; i++) {
                str = input.readLine();
                if (str != null) {
                    macSerial = str.trim();
                    break;
                }
            }
            logger.info("get mac by cat /sys/class/net/wlan0/address : {}", macSerial);
            //8c:34:fd:3b:7c:f3
            //正确的情况是这样的,如果是失败的话,那就各种各样了,还是校验一下比较靠谱
            if (!TextUtils.isEmpty(macSerial)) {
                String[] split = macSerial.split(":");
                if (split == null || split.length != 6) {
                    macSerial = "";
                    logger.warn("macSerial is not good, clean it");
                }
            }
        } catch (Exception e) {
            logger.warn("exec(cat /sys/class/net/wlan0/address) fail", e);
        }
        return macSerial;
    }

    /**
     * 获取WlanMAC
     *
     * @param context
     * @param isOpenWifi 是否强制去把WI-FI打开
     * @return
     */
    public static String getWlanMACByWifiInfo(@NonNull Context context, boolean isOpenWifi) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        String macSerial = null;
        if (wifiManager == null) {
            return macSerial;
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo != null) {
            macSerial = wifiInfo.getMacAddress();
        }
        if (TextUtils.isEmpty(macSerial) && isOpenWifi && !wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);

            // 开启wifi后并不能马上获取到MAC
            //  一般来讲主线程的ARN时间:
            // KEY_DISPATCHING_TIMEOUT = 5*1000;
            // BROADCAST_TIMEOUT = 10*1000;
            // 这里设置成最长4s,运气好的话就不会出现异常
            for (int i = 0; i < 20; i++) {
                wifiInfo = wifiManager.getConnectionInfo();
                macSerial = wifiInfo != null ? wifiInfo.getMacAddress() : null;
                if (!TextUtils.isEmpty(macSerial)) {
                    break;
                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    logger.warn("sleep 200", e);
                }
            }
        }
        logger.info("get mac by WifiInfo, isOpenWifi:{}, macSerial : {}", isOpenWifi, macSerial);
        return macSerial;
    }

    /**
     * 获取网卡的MAC
     *
     * @param context
     * @return
     */
    public static String getWlanMAC(@NonNull Context context) {
        String macSerial = getWlanMACByShell();
        if (TextUtils.isEmpty(macSerial)) {
            macSerial = getWlanMACByWifiInfo(context, false);
        }
        return macSerial;
    }


    /**
     * 获取versionName
     *
     * @param context
     * @return
     */
    public static String getVersionName(@NonNull Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            logger.error("get package name fail", e);
            return "";
        }
    }


    /**
     * 获取版本号(内部识别号)
     *
     * @param context
     * @return
     */
    public static int getVersionCode(@NonNull Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            logger.error("get package code fail", e);
            return 0;
        }
    }

    /**
     * 获取蓝牙MAC
     *
     * @return
     */
    public static String getBluetoothMAC() {
        String bluetoothMAC = "";
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter != null) {
            bluetoothMAC = bluetoothAdapter.getAddress();
        }
        return bluetoothMAC;
    }

    /**
     * 生成UUID的规则
     *
     * @param context
     * @return
     */
    public static String generateUUID(@NonNull Context context) {
        String wlanMAC = "";
        try {
            wlanMAC = getWlanMAC(context);
        } catch (Exception e) {

        }

        String bluetoothMAC = getBluetoothMAC();

        String secureId = "";
        try {
            Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception e) {

        }

        String imei = "";
        try {
            TelephonyManager TelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            imei = TelephonyMgr.getDeviceId();
        } catch (Exception e) {

        }

        String serialNumber = android.os.Build.SERIAL;

        logger.info("wlanMAC:{}, bluetoothMAC:{}, secureId:{}, imei:{}, serialNumber:{}", wlanMAC, bluetoothMAC, secureId, imei, serialNumber);

        StringBuilder builder = new StringBuilder();
        builder.append(wlanMAC).append("|")
                .append(bluetoothMAC).append("|")
                .append(secureId).append("|")
                .append(imei).append("|")
                .append(serialNumber);

        MessageDigest messageDigest = null;
        String uuid = "";
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            uuid = StringHelper.byteArrayToHex(messageDigest.digest(builder.toString().getBytes("UTF-8")));
        } catch (Exception e) {
            logger.error("get MD5 fail", e);
        }
        logger.info("generateUUID={}", uuid);
        return uuid;
    }


    public static String getChannel(@NonNull Context context) {
        String channel = "guanwang";
        String sourceDir = context.getApplicationInfo().sourceDir;
        String key = "APPHELPER_CHANNEL";
        try {
            long installedTime = new File(sourceDir).lastModified();
            key = key + "_" + installedTime;

            String cachechannel = "";
            if (!TextUtils.isEmpty(cachechannel)) {
                logger.info("get cache channel, key={},value={}", key, cachechannel);
                return cachechannel;
            }
        } catch (Exception e) {
        }
        try {
         /*   ZipFile zipFile = new ZipFile(sourceDir);
            String comment = zipFile.getComment("UTF-8");
            if(!TextUtils.isEmpty(comment)) {
                String[] splits = comment.split(",", 2);
                if(splits != null && splits.length == 2) {
                    channel = splits[1];
                }
            }*/
        } catch (Exception e) {
            logger.error("getChannel failed", e);
        }
        if (!TextUtils.isEmpty(channel)) {
//            GlobalVariable.aCache.put(key, channel);
        }
        logger.info("channel={}", channel);
        return channel;

    }

    public static String languageEnvironment(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        return locale.getLanguage();
    }

    public static String getNetworkType(Context context) {
        String strNetworkType = "unknow";
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                strNetworkType = "WIFI";
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                String _strSubTypeName = networkInfo.getSubtypeName();
                int networkType = networkInfo.getSubtype();
                switch (networkType) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN: //api<8 : replace by 11
                        strNetworkType = "2G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    case TelephonyManager.NETWORK_TYPE_EHRPD:
                    case TelephonyManager.NETWORK_TYPE_HSPAP:
                        strNetworkType = "3G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_LTE:
                        strNetworkType = "4G";
                        break;
                    default:
                        if (_strSubTypeName.equalsIgnoreCase("TD-SCDMA") || _strSubTypeName.equalsIgnoreCase("WCDMA") || _strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                            strNetworkType = "3G";
                        } else {
                            strNetworkType = _strSubTypeName;
                        }

                        break;

                }
            }
        }
        return strNetworkType;
    }


    private final static int kSystemRootStateUnknow = -1;
    private final static int kSystemRootStateDisable = 0;
    private final static int kSystemRootStateEnable = 1;
    private static int systemRootState = kSystemRootStateUnknow;

    public static int isRootSystem() {
        if (systemRootState == kSystemRootStateEnable) {
            return kSystemRootStateEnable;
        } else if (systemRootState == kSystemRootStateDisable) {
            return kSystemRootStateDisable;
        }
        File f = null;
        final String kSuSearchPaths[] = {"/system/bin/", "/system/xbin/", "/system/sbin/", "/sbin/", "/vendor/bin/"};
        try {
            for (int i = 0; i < kSuSearchPaths.length; i++) {
                f = new File(kSuSearchPaths[i] + "su");
                if (f != null && f.exists()) {
                    systemRootState = kSystemRootStateEnable;
                    return kSystemRootStateEnable;
                }
            }
        } catch (Exception e) {
        }
        systemRootState = kSystemRootStateDisable;
        return kSystemRootStateDisable;
    }

    public static String getMetaDataByKey(@NonNull Context context, String key) {
        Bundle bundle = null;
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(getPackageName(context), PackageManager.GET_META_DATA);
            bundle = info.metaData;
        } catch (Exception e) {
            logger.warn("", e);
        }
        String value = "";
        if (bundle != null) {
            value = bundle.getString(key, "");
        }
        return value;
    }

    public static String getMD5(InputStream inputStream) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                messageDigest.update(buffer, 0, len);
            }
            return StringHelper.byteArrayToHex(messageDigest.digest());
        } catch (Exception e) {
        }
        return "";
    }

    public static Map<String, String> getSystemProperties() {
        //先执行getprop
        // 命令试一下,看看能不能获取到
        StringBuilder getprop = new StringBuilder();
        Map<String, String> getprops = new HashMap<String, String>();
        try {
            java.lang.Process pp = Runtime.getRuntime().exec("getprop");
            InputStreamReader reader = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(reader);

            String str = "";
            //避免进入死循环,这个文件一般情况下不会超过5000行
            for (int i = 0; null != str && i < 5000; i++) {
                str = input.readLine();
                getprop.append(str).append("\n");

                if (TextUtils.isEmpty(str)) {
                    break;
                }

                str = str.trim();
                String[] splits = str.split(":", 2);
                if (splits != null && splits.length == 2) {
                    int start = splits[0].indexOf("[") + 1;
                    int end = splits[0].lastIndexOf("]");
                    if (end < 0) {
                        end = splits[0].length();
                    }
                    String key = splits[0].substring(start, end);

                    //如果没有就是-1,加一就正好是0,如果是0那么也需要加一才能去掉这个[
                    start = splits[1].indexOf("[") + 1;
                    end = splits[1].lastIndexOf("]");
                    if (end < 0) {
                        end = splits[1].length();
                    }
                    String value = splits[1].substring(start, end);

                    getprops.put(key, value);
                }
            }
            logger.info("exe getprop shell : {}", getprop.toString());

        } catch (Exception e) {
            logger.warn("exec(getprop) fail", e);
        }
        return getprops;
    }

    public static String getApkSignatures(@NonNull Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int flags = PackageManager.GET_SIGNATURES;
            String packageName = context.getPackageName();
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, flags);
            Signature[] signatures = packageInfo.signatures;
            return StringHelper.byteArrayToHex(signatures[0].toByteArray());
        } catch (Exception e) {
            logger.error("getApkSignatures fail", e);
        }
        return "";
    }

    public static String getIMEI(@NonNull Context context) {
        String imei = "";
        try {
            TelephonyManager mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (mTelephonyMgr != null) {
                imei = mTelephonyMgr.getDeviceId();
            }
        } catch (SecurityException e) {
            logger.warn("", e);
        }

        return imei;
    }

    public static boolean getGpsState(Context cxt) {
        if (cxt == null) {
            return false;
        }
        LocationManager locationManager = (LocationManager) cxt
                .getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public static boolean getNetLocationState(Context cxt) {
        if (cxt == null) {
            return false;
        }
        LocationManager locationManager = (LocationManager) cxt
                .getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    // 判断是否缺少权限
    public static boolean lacksPermission(Context context, String permission) {
        try {
            return ContextCompat.checkSelfPermission(context, permission) ==
                    PackageManager.PERMISSION_DENIED;
        } catch (Exception e) {
            return true;
        }
    }

    public static String getSimOperatorName(@NonNull Context context) {
        try {
            TelephonyManager mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (mTelephonyMgr != null) {
                return mTelephonyMgr.getSimOperatorName();
            }
        } catch (SecurityException e) {
            logger.warn("", e);
        }
        return "";
    }

    public static boolean isMainThread() {
        return Looper.getMainLooper().getThread().getId() == Thread.currentThread().getId();
    }
}
