package com.demo.common.utils;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.TextView;

import com.demo.common.base.BaseApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    private static Logger logger = LoggerFactory.getLogger("Utils");

    private static SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static String getString(int resId) {
        return BaseApplication.getInstance().getString(resId);

    }

    public static String getJsonFromAssets(Context context, String fileName) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * convert drawable to bitmap
     *
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;

    }


    /**
     * 功能：判断字符串是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (isNum.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 功能：如果为空则返回一个空字符串
     *
     * @param str
     * @return
     */
    public static String getEmptyString(String str) {
        String es = "";
        if (validateString(str)) {
            es = str;
        }
        return es;
    }

    /**
     * 功能：判断字符串是否为汉字
     *
     * @param str
     * @return
     */
    public static boolean isChineseChar(String str) {
        boolean temp = false;
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            temp = true;
        }
        return temp;
    }

    /**
     * 功能：判断字符串是否为日期格式
     *
     * @param strDate
     * @return
     */
    public static boolean isDate(String strDate) {
        Pattern pattern = Pattern
                .compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
        Matcher m = pattern.matcher(strDate);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Validate if the input string is null or empty.
     *
     * @param src
     * @return true if the string is legal, false otherwise.
     */
    public static boolean validateString(String src) {
        if (src == null || src.trim().length() == 0) {
            return false;
        }

        return true;
    }

    /**
     * Validate if the input is number
     *
     * @param src
     * @return
     */
    public static boolean validateNumber(String src) {
        Pattern p = Pattern.compile("[0-9]*");
        Matcher m = p.matcher(src);
        if (m.matches()) {
            return true;
        }
        return false;
    }

    /**
     * Compare to the current time
     *
     * @param s1
     * @return
     */
    public static boolean datecompare(String s1) {
        java.text.DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            c1.setTime(df.parse(s1));
        } catch (java.text.ParseException e) {
            System.err.println("格式不正确");
        }
        int result = c1.compareTo(c2);
        if (result < 0) {
            return false;
        }
        return true;

    }

    /**
     * Test if the external storage is mounted
     *
     * @return true : the external storage is mounted
     */
    public static boolean isExternalStorageMounted() {
        if (!Environment.getExternalStorageDirectory().canRead()
                || Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED_READ_ONLY)
                || Environment.getExternalStorageState().equals(Environment.MEDIA_UNMOUNTED)) {
            return false;
        }

        return true;
    }

    /**
     * 描述: 隐藏键盘
     *
     * @param activity
     */
    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputmanger = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputmanger.hideSoftInputFromWindow(activity.getWindow().peekDecorView().getWindowToken(), 0);
    }

    /**
     * 描述: 隐藏键盘
     *
     * @param c
     */
    public static void hideKeyboard(Context c) {
        InputMethodManager inputmanger = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputmanger.hideSoftInputFromWindow(BaseApplication.currentActivity.getWindow().peekDecorView().getWindowToken(), 0);
    }

    /**
     * 有键盘就将其隐藏
     *
     * @param c
     */
    public static void hideKeyboardAll(Context c) {
        InputMethodManager imm = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 判断输入框弹出
     *
     * @param window
     * @param content
     */
    public static void getInput(Window window, Context content) {
        // 判断隐藏软键盘是否弹出
        if (window.getAttributes().softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED) {
            // 隐藏软键盘
            // InputType.TYPE_CLASS_NUMBER
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        } else {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        }
    }

    // 自动调用软键盘
    public static void showInput(final TextView textView) {
        textView.setFocusable(true);
        textView.setFocusableInTouchMode(true);
        textView.requestFocus();
        // mSearchEdit.setInputType(InputType.TYPE_CLASS_NUMBER); //弹出数字键盘
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                InputMethodManager inputManager = (InputMethodManager) textView.getContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                if (inputManager != null) {
                    inputManager.showSoftInput(textView, 0);
                }
            }
        }, 100);
    }

    public static String getStringFromPref(String key) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(BaseApplication.getInstance());
        if (pref.contains(key)) {
            return pref.getString(key, null);
        }

        return null;
    }


    @Deprecated
    public static void tag(String msg) {
        StackTraceElement stacks = new Exception().getStackTrace()[1];
        String information = "Exception Found!:" + stacks.getClassName() + "---------->OnLine:" + stacks.getLineNumber() + " "
                + stacks.getMethodName() + ":" + msg;
        logger.error(information);
    }

    @Deprecated
    public static void Log(Exception e) {
        logger.warn("", e);
    }

    /**
     * 获取渠道号
     *
     * @return
     */
    public static String getAppMetaData(String key) {
        if (Utils.validateString(key)) {
            return null;
        }
        ApplicationInfo appInfo = null;
        String resultData = null;
        try {
            appInfo = BaseApplication.getInstance().getPackageManager()
                    .getApplicationInfo(BaseApplication.getInstance().getPackageName(), PackageManager.GET_META_DATA);
            resultData = appInfo.metaData.getString(key);
            return resultData;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return resultData;
    }

    /**
     * 支付传参-时间戳
     *
     * @return
     */
    public static String stampDate() {
        String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        return date;
    }

    //把String转化为float
    public static float convertToFloat(String number, float defaultValue) {
        if (TextUtils.isEmpty(number)) {
            return defaultValue;
        }
        try {
            return Float.parseFloat(number);
        } catch (Exception e) {
            return defaultValue;
        }

    }

    //把String转化为double
    public static double convertToDouble(String number, double defaultValue) {
        if (TextUtils.isEmpty(number)) {
            return defaultValue;
        }
        try {
            return Double.parseDouble(number);
        } catch (Exception e) {
            return defaultValue;
        }

    }

    public static class FileUtils {
        public static void cleanInternalCache() {
            Context c = BaseApplication.getInstance();
            if (c != null) {
                File dir = c.getCacheDir();
                File[] files = dir.listFiles();
                for (File file : files) {
                    if (file.getName().endsWith("doe")) {
                        file.delete();
                    }
                }
            }
        }

        public static File readable(String path) {
            if (StringUtils.validate(path)) {
                File file = new File(path);
                if (file.exists() && file.canRead())
                    return file;
            }

            return null;
        }

        public static boolean delete(String path) {
            if (!StringUtils.validate(path)) {
                return false;
            }
            File file = new File(path);
            if (file != null && file.canWrite()) {
                return file.delete();
            }

            return false;
        }

        public static File copy(String fromPath, String toPath) throws IOException {
            if (fromPath != null && fromPath.equals(toPath)) {
                throw new IOException("复制和剪切操作的目标路径不可与原路径相同");
            }

            if (toPath == null || toPath.trim().length() == 0) {
                return null;
            }

            File file = readable(fromPath);
            if (file != null) {
                File toFile = new File(toPath);
                if (toFile.exists()) {
                    toFile.delete();
                }

                File parent = toFile.getParentFile();
                parent.mkdirs();

                if (toFile.createNewFile()) {
                    FileInputStream fins = new FileInputStream(file);
                    FileOutputStream fopt = new FileOutputStream(toFile);

                    byte[] buffer = new byte[8 * 1024];
                    int count = 0;
                    try {
                        while ((count = fins.read(buffer)) != -1) {
                            fopt.write(buffer, 0, count);
                        }

                        fopt.flush();
                    } catch (IOException e) {
                        throw e;
                    } finally {
                        fins.close();
                        fopt.close();
                    }

                }

                return toFile;
            }

            return null;
        }

        public static File creatNewFileInSdcard(String path) throws IOException {
            if (!isExternalStorageMounted()) {
                System.out.println("sdcard unavailiable");
                return null;
            }

            File file = new File(path);
            if (file.exists()) {
                return file;
            }
            file = null;

            String sdPath = null;
            String[] paths = path.split("/");
            String newPath = null;
            int j = 0;

            if (paths.length == 1) {

                throw new IOException("permission denied");
            } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(Environment.getExternalStorageState())
                    || Environment.MEDIA_UNMOUNTED.equals(Environment.getExternalStorageState())) {
                throw new IOException("SdCard unmounted or read-only");
            } else {
                sdPath = Environment.getExternalStorageDirectory().getPath();
                // paths = path.replace(sdPath, "").split("/");
                newPath = sdPath;
                j = sdPath.split("/").length;
            }

            for (int i = j; i < paths.length - 1; i++) {
                newPath += "/" + paths[i];
            }

            File newFileDir = new File(newPath);

            if (!newFileDir.exists()) {
                boolean dirsCreated = newFileDir.mkdirs();

                if (!dirsCreated)
                    return null;
            }

            File newFile = new File(path);
            if (!newFile.exists()) {

                boolean isCreated = newFile.createNewFile();
                if (!isCreated)
                    return null;

            }

            return newFile;
        }
    }

    /**
     * 处理金额取两位小数
     *
     * @param value
     * @return
     */
    public static String parseMoneyValue(String value) {
        if (value == null)
            return "";
        String ret = "";
        try {
            ret = String.format("%.2f", Double.parseDouble(value));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 处理时间
     *
     * @param date
     * @return
     */
    public static String getTimeString(Date date) {
        Calendar calendarNow = Calendar.getInstance(), calendar1 = Calendar.getInstance();
        calendar1.setTime(date);
        Calendar today = Calendar.getInstance();
        today.set(calendarNow.get(Calendar.YEAR), calendarNow.get(Calendar.MONTH), calendarNow.get(Calendar.DAY_OF_MONTH), 0, 0,
                0);
        Calendar yeaterday = Calendar.getInstance();
        yeaterday.setTime(new Date(today.getTimeInMillis() - 1000 * 3600 * 24));
        Calendar firstDayOfMonth = Calendar.getInstance();
        firstDayOfMonth.set(calendarNow.get(Calendar.YEAR), calendarNow.get(Calendar.MONTH), 1, 0, 0, 0);
        Calendar firstDatOfYear = Calendar.getInstance();
        firstDatOfYear.set(calendarNow.get(Calendar.YEAR), 1, 1, 0, 0, 0);
        if (calendar1.after(today)) {
            return "今天\t" + new SimpleDateFormat("HH:mm").format(date);
        } else if (calendar1.after(yeaterday)) {
            return "昨天\t" + new SimpleDateFormat("HH:mm").format(date);
        } else if (calendar1.after(firstDayOfMonth) || calendar1.after(firstDatOfYear)) {
            return new SimpleDateFormat("MM-dd HH:mm").format(date);
        } else {
            return new SimpleDateFormat("yyyy-MM-dd").format(date);
        }
    }

    public static class StringUtils {
        public static boolean validate(String str) {
            return str != null && str.trim().length() != 0;
        }

        public static String getValidString(String string, String defaultValue) {
            return string != null ? string : getValidString(defaultValue, "");
        }

        public static boolean validate(Object str) {

            if (str == null)
                return false;
            return str.toString().trim().length() != 0;
        }

    }

    public static class ResUtils {

        public static Resources getResources() {
            return BaseApplication.currentActivity == null ? BaseApplication.getInstance().getResources()
                    : BaseApplication.currentActivity.getResources();
        }

        public static String getString(int resId) {
            return getResources().getString(resId);
        }

        public static int getColor(int colorResID) {
            return getResources().getColor(colorResID);
        }

        public static float getFloat(int resId) {
            String configStr = getString(resId);
            if (configStr.contains("%")) {
                configStr = configStr.replace("%", "");
                return Integer.valueOf(configStr) / 100f;
            }

            return Float.valueOf(configStr);
        }

        public static String[] getArray(int resId) {
            return getResources().getStringArray(resId);
        }

        public static Bitmap getBitmapDrawable(int bitmapdrawResId) {
            return ((BitmapDrawable) getResources().getDrawable(bitmapdrawResId)).getBitmap();
        }

        public static float getPixelFromDP(float dp) {
            WindowManager windowManager = (WindowManager) BaseApplication.getInstance().getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics m = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(m);

            return dp * m.density;
        }

        public static SharedPreferences getDefaultSharedPreferences() {
            return PreferenceManager.getDefaultSharedPreferences(BaseApplication.getInstance());
        }

        /**
         * 通过输入目标的高或宽与原高宽的比例返回对应的高或宽，输入的目标width或height必须有个为-1。如果输入的width是-1
         * 则表示按比例获取相应的height。
         *
         * @param drawable
         * @param width
         * @param height
         * @return
         */
        public static int getReMeasuredLength(Drawable drawable, int width, int height) {
            int oHeight = drawable.getIntrinsicHeight();
            int oWidth = drawable.getIntrinsicWidth();

            float scale = 0;
            if (width == -1) {
                scale = height / (float) oHeight;
                return (int) (oWidth * scale);
            } else if (height == -1) {
                scale = width / (float) oWidth;
                return (int) (oHeight * scale);
            } else {
                throw new IllegalArgumentException("One of the width or height must be -1");
            }

        }
    }

    public static Context getAppContext() {
        return ApplicationManager.getApplicationContext();
    }

    public static boolean validList(List<?> list) {
        return list != null && list.size() != 0;
    }

    public static boolean validMap(Map<?, ?> map) {
        return map != null && map.size() != 0;
    }

    /**
     * 给剪贴板设置字符
     *
     * @param context
     * @param txt
     */
    public static void writeToClipboard(Context context, String txt) {
        ClipboardManager mClipboard = null;
        mClipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("cliptext", txt);
        mClipboard.setPrimaryClip(clip);
        ToastCompat.show(context,"复制成功", 0);
    }

    /**
     * 获取剪贴板的字符
     *
     * @return
     */
    public static String readToClipboard(Context context) {
        ClipboardManager mClipboard = null;
        if (null == mClipboard) {
            mClipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        }
        String rs = "";
        if (!mClipboard.hasPrimaryClip()) {
            ToastCompat.show(context,"Clipboard is empty", 0);
        } else {
            ClipData clipData = mClipboard.getPrimaryClip();
            int count = clipData.getItemCount();
            for (int i = 0; i < count; ++i) {
                ClipData.Item item = clipData.getItemAt(i);
                CharSequence str = item.coerceToText(context);
                rs += str;
            }
        }
        return rs;
    }

    /*
     * 获取版本的信息 VersionName
     */
    @Deprecated
    public static String getversionName(Context context) {
        return AppHelper.getVersionName(context);
    }

    /*
     * 获取版本的信息 VersionCode
     */
    public static int getversionCode(Context context) {
        int versionCode = -1;
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            versionCode = info.versionCode;
            if (versionCode <= 0) {
                return 20160101;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 递归删除文件和文件夹
     *
     * @param file 要删除的根目录
     */
    public static void deleteFile(File file) {
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
                return;
            }
            if (file.isDirectory()) {
                File[] childFile = file.listFiles();
                if (childFile == null || childFile.length == 0) {
                    file.delete();
                    return;
                }
                for (File f : childFile) {
                    deleteFile(f);
                }
                file.delete();
            }
        }
    }

    /**
     * 以最省内存的方式读取图片
     */
    public static Bitmap readBitmap(final String path) {
        try {
            FileInputStream stream = new FileInputStream(new File(path));
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inSampleSize = 8;
            opts.inPurgeable = true;
            opts.inInputShareable = true;
            Bitmap bitmap = BitmapFactory.decodeStream(stream, null, opts);
            return bitmap;
        } catch (OutOfMemoryError e) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 根据图片的url路径获得Bitmap对象
     *
     * @param url
     * @return
     */
    public static Bitmap returnBitmap(String url) {
        URL fileUrl = null;
        Bitmap bitmap = null;

        try {
            fileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            HttpURLConnection conn = (HttpURLConnection) fileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;

    }

    /**
     * 去掉字符串中的所有空格以及“-”等符号
     *
     * @param str 需要去除空格的字符串
     * @return 处理过后的字符串
     */
    public static String removeStrSpace(String str) {
        if (str == null)
            str = "";
        // String afterStr=str.replaceAll(" ", "");//去处所有英文空格
        // afterStr=afterStr.replaceAll(" ", "");//去处所有中文空格
        // afterStr.replaceAll("-", "");//去除所有英文“-”
        // afterStr.replaceAll("-", "");//去除所有中文“-”

        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);

        return m.replaceAll("").trim();
    }

    private static final String TAG = "BitmapCommonUtils";
    private static final long POLY64REV = 0x95AC9329AC4BC9B5L;
    private static final long INITIALCRC = 0xFFFFFFFFFFFFFFFFL;

    private static long[] sCrcTable = new long[256];

    /**
     * 获取bitmap的字节大小
     *
     * @param bitmap
     * @return
     */
    public static int getBitmapSize(Bitmap bitmap) {
        return bitmap.getRowBytes() * bitmap.getHeight();
    }

    /**
     * 获取程序外部的缓存目录
     *
     * @param context
     * @return
     */
    public static File getExternalCacheDir(Context context) {
        final String cacheDir = "/Android/data/" + context.getPackageName() + "/cache/";
        return new File(Environment.getExternalStorageDirectory().getPath() + cacheDir);
    }


    public static byte[] getBytes(String in) {
        byte[] result = new byte[in.length() * 2];
        int output = 0;
        for (char ch : in.toCharArray()) {
            result[output++] = (byte) (ch & 0xFF);
            result[output++] = (byte) (ch >> 8);
        }
        return result;
    }

    public static boolean isSameKey(byte[] key, byte[] buffer) {
        int n = key.length;
        if (buffer.length < n) {
            return false;
        }
        for (int i = 0; i < n; ++i) {
            if (key[i] != buffer[i]) {
                return false;
            }
        }
        return true;
    }

    public static byte[] copyOfRange(byte[] original, int from, int to) {
        int newLength = to - from;
        if (newLength < 0)
            throw new IllegalArgumentException(from + " > " + to);
        byte[] copy = new byte[newLength];
        System.arraycopy(original, from, copy, 0, Math.min(original.length - from, newLength));
        return copy;
    }

    static {
        // 参考 http://bioinf.cs.ucl.ac.uk/downloads/crc64/crc64.c
        long part;
        for (int i = 0; i < 256; i++) {
            part = i;
            for (int j = 0; j < 8; j++) {
                long x = ((int) part & 1) != 0 ? POLY64REV : 0;
                part = (part >> 1) ^ x;
            }
            sCrcTable[i] = part;
        }
    }

    public static byte[] makeKey(String httpUrl) {
        return getBytes(httpUrl);
    }

    /**
     * A function thats returns a 64-bit crc for string
     *
     * @param in input string
     * @return a 64-bit crc value
     */
    public static final long crc64Long(String in) {
        if (in == null || in.length() == 0) {
            return 0;
        }
        return crc64Long(getBytes(in));
    }

    public static final long crc64Long(byte[] buffer) {
        long crc = INITIALCRC;
        for (int k = 0, n = buffer.length; k < n; ++k) {
            crc = sCrcTable[(((int) crc) ^ buffer[k]) & 0xff] ^ (crc >> 8);
        }
        return crc;
    }

    private static final int INVALID = -1;

    public static boolean listIsAtTop(AbsListView listView) {
        return listView.getChildCount() == 0 || listView.getChildAt(0).getTop() == listView.getPaddingTop();
    }

    /**
     * This will be called in order to create view, if the given view is not
     * null, it will be used directly, otherwise it will check the resourceId
     *
     * @return null if both resourceId and view is not set
     */
    public static View getView(Context context, int resourceId, View view) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if (view != null) {
            return view;
        }
        if (resourceId != INVALID) {
            view = inflater.inflate(resourceId, null);
        }
        return view;
    }

    /*
     * 计算过了多长时间。
     */
    public static long howdaysago(String oldDate) {
        if (!validateString(oldDate)) {
            return 0;
        }
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = null;
        Date agoDate = null;
        try {
            newDate = myFormatter.parse(Utils.getDateTime(new Date()));
            agoDate = myFormatter.parse(oldDate);
        } catch (Exception e) {
        }
        if (newDate.getTime() < agoDate.getTime()) {
            return 0;
        }
        long day = (newDate.getTime() - agoDate.getTime()) / (24 * 60 * 60 * 1000);
        return day;

    }

    /*
    * 计算过了多长时间。
    */
    public static long validateDays(String oldDate) {
        if (!validateString(oldDate)) {
            return 0;
        }
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = null;
        Date agoDate = null;
        long day = 0;
        try {
            newDate = myFormatter.parse(Utils.getDateTime(new Date()));
            agoDate = myFormatter.parse(Utils.getDateTime(new Date(Long.parseLong(oldDate))));
            day = (newDate.getTime() - agoDate.getTime()) / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            return day;
        }
        if (newDate.getTime() < agoDate.getTime()) {
            return day;
        }

        return day;
    }

    /*
     * 将时间转成String 格式
     */
    public static String getDateTime(Date date) {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        return myFormatter.format(date);
    }

    /*
 * 将时间转成String 格式
 */
    public static String getDateTimeMill(Date date) {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return myFormatter.format(date);
    }

    /*
     * 将字符串全转成全角字符。
     */
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }


    /**
     * 时间格式化
     * @param date
     * @return
     */
    public static String formatTime(Date date) {
        if (date == null) {
            return "";
        }
        return mFormat.format(date);
    }
    /**
     * 首页金牌司机数量动画效果的数据格式化处理
     */
    private static DecimalFormat dfs = null;

    public static DecimalFormat format(String pattern) {
        if (dfs == null) {
            dfs = new DecimalFormat();
        }
        dfs.setRoundingMode(RoundingMode.FLOOR);
        dfs.applyPattern(pattern);
        return dfs;
    }

    private static long lastClickTime;



    /**
     * 连续多次点击控制
     */
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 1000) {       //500毫秒内按钮无效，这样可以控制快速点击，自己调整频率
            return true;
        }
        lastClickTime = time;
        return false;
    }
}