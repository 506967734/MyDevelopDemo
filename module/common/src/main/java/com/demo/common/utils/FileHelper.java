package com.demo.common.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.AssetManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;


public class FileHelper {

    public static final String DEFAULT_CHARSET = "UTF-8";

    private static Logger logger = LoggerFactory.getLogger("FileHelper");

    public static void close(Closeable closeable) {
        if(closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {

            }
        }
    }

    public static long readFileLength(File localFile) throws IOException {
        if (localFile == null || !localFile.exists()) {
            return 0;
        }
        FileInputStream stream = null;
        long totleLength = 0;
        try {
            stream = new FileInputStream(localFile);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = stream.read(buffer)) != -1) {
                totleLength += len;
            }
        } catch (IOException ioe) {
            throw ioe;
        } finally {
            close(stream);
        }
        return totleLength;
    }

    public static long write(InputStream inputStream, OutputStream outputStream) throws IOException {
        return write(inputStream, outputStream, 1024);
    }

    public static long write(InputStream inputStream, OutputStream outputStream, int bufferLen) throws IOException {
        if(inputStream == null || outputStream == null) {
            return -1;
        }
        long totleLength = 0;
        try {
            byte[] buffer = new byte[bufferLen];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
                totleLength += len;
            }
            outputStream.flush();
        } catch (IOException ioe) {
            throw ioe;
        }
        return totleLength;
    }

    public static File getAppExternalDir(@NonNull Context context) {
        File externalDir = Environment.getExternalStorageDirectory();
        String packageName = "";
        try {
            ApplicationInfo info= context.getApplicationInfo();
            packageName = info.packageName;
        } catch (Exception e) {
        }
        StringBuilder builder = new StringBuilder();
        builder.append(File.separator).append(packageName).append(File.separator);
        return new File(externalDir, builder.toString());
    }

    public static void deleteFile(File file) {
        if(file == null || !file.exists()) {
            return ;
        }
        if(!file.delete()){
            try {
                Runtime.getRuntime().exec("rm -Rf "+file.getAbsolutePath());
            }catch (Exception e){

            }
        }
    }

    public static void copyFile(File srcFile, File targetFile)
            throws IOException {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(srcFile);
            outputStream = new FileOutputStream(targetFile);
            copyFile(inputStream, outputStream, new byte[1024 * 4]);
        } catch (IOException e) {
            throw e;
        } finally {
            close(inputStream);
            close(outputStream);
        }
    }

    private static long copyFile(InputStream input, OutputStream output, byte[] buffer)
            throws IOException {
        long count = 0;
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    public static boolean setFileContent(File file, String content) {
        if(file==null|| TextUtils.isEmpty(content)){
            return false;
        }
        OutputStreamWriter osw = null;
        try{
            if(!file.exists()){
                File dir = file.getParentFile();
                if(!dir.exists()){
                    dir.mkdirs();
                }
                file.createNewFile();
            }
            osw = new OutputStreamWriter(new FileOutputStream(file),"UTF-8");
            osw.write(content);
            return true;
        }catch (Exception e){
            return false;
        }finally {
            close(osw);
        }
    }

    public static String readContentFromFile(File file, String charsetName) {
        if(file == null || !file.exists()) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            write(fileInputStream, byteArrayOutputStream);
            return byteArrayOutputStream.toString(charsetName);
        } catch (FileNotFoundException e) {
            logger.warn("FileNotFoundException", e);
        } catch (IOException e) {
            logger.warn("IOException", e);
        } finally {
            close(fileInputStream);
            close(byteArrayOutputStream);
        }
        return "";
    }

    public static boolean writeContentToFile(File file, String content, String charsetName) {
        if(file == null) {
            return false;
        }
        if(TextUtils.isEmpty(content) || TextUtils.isEmpty(charsetName)) {
            return false;
        }
        if(!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        ByteArrayInputStream byteArrayInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            byteArrayInputStream = new ByteArrayInputStream(content.getBytes(charsetName));
            fileOutputStream = new FileOutputStream(file, false);
            write(byteArrayInputStream, fileOutputStream);
            return true;
        } catch (UnsupportedEncodingException e) {
            logger.warn("UnsupportedEncodingException", e);
        } catch (FileNotFoundException e) {
            logger.warn("FileNotFoundException", e);
        } catch (IOException e) {
            logger.warn("IOException", e);
        } finally {
            close(byteArrayInputStream);
            close(fileOutputStream);
        }
        return false;
    }
    public static String readAssertResource(Context context, String strAssertFileName) {
        AssetManager assetManager = context.getAssets();
        String strResponse = "";
        try {
            InputStream ims = assetManager.open(strAssertFileName);
            strResponse = getStringFromInputStream(ims);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strResponse;
    }

    private static String getStringFromInputStream(InputStream a_is) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            br = new BufferedReader(new InputStreamReader(a_is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }
        return sb.toString();
    }
}
