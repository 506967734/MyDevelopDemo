package com.demo.common.log.upload;

import android.os.Environment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by lenovo on 2016/7/8.
 */
public class UpdateUtil {
    private static final String LOG_DIR = Environment.getExternalStorageDirectory()+ File.separator+"com.chuanhua.goodstaxi"+ File.separator;
    private static final String UN_ARCHIVED_FILE = LOG_DIR+"logFile.txt";
    public static void upload(long startTime,long endTime) {
        try {
            //设备时间异常
            if(startTime> System.currentTimeMillis()||startTime>endTime){
                return;
            }
            List<File> files = getUploadFiles(startTime,endTime);
            if(files==null||files.size()==0){
                return;
            }
           //上传文件
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static List<File> getUploadFiles(long startTime, long endTime) {
        //归档文件
        List<File> files = new ArrayList<>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH", Locale.CHINA);
        Calendar cal = Calendar.getInstance();
        String fileTime = null;

        for(long time = startTime;time<=endTime;time+=36000000){
            StringBuilder sb = new StringBuilder();
            cal.setTimeInMillis(time);
            fileTime = df.format(cal.getTime());
            String fileName = sb.append(LOG_DIR).append("logFile.").append(fileTime).append(".txt.gz").toString();
            File tempFile = new File(fileName);
            if(tempFile.exists()){
                files.add(tempFile);
            }
        }
        //未归档文件
        Calendar thisHour = Calendar.getInstance();
        thisHour.set(Calendar.MINUTE,0);
        thisHour.set(Calendar.SECOND,0);
        thisHour.set(Calendar.MILLISECOND,0);
        if(endTime>=thisHour.getTimeInMillis()){
            File file = new File(UN_ARCHIVED_FILE);
            if(file.exists()){
                files.add(file);
            }
        }
        return files;
    }
}
