package com.demo.common.log;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Environment;

import org.slf4j.LoggerFactory;

import java.io.File;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.android.LogcatAppender;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;

public class LogBackUtil {
    private static final String PUB_KEY_NAME = "encode_public_key";
    private static String LOG_DIR;
    private static boolean SAVE_ON_SDCARD;

    static String mPackageName;
    static String mVersionName;
    static int mVersionCode = -1;

    /**
     * @param cxt
     * @param doEncryption 是否加密存储
     * @param showLogCat   是否在控制台显示logcat
     */
    public static void configureLogbackDirectly(Context cxt, boolean doEncryption, boolean showLogCat) {
        //读取rsa秘钥
        String rsaPubKey = getRsaPubKey(cxt);
        //读取日志存储路径
        LOG_DIR = getLogsDir(cxt);
        // reset the default context (which may already have been initialized)
        // since we want to reconfigure it
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        lc.reset();
        // setup RollingFileAppender
        EncodePatternLayoutEncoder encoderLogger = new EncodePatternLayoutEncoder();
        encoderLogger.setContext(lc);
        encoderLogger.setDoEncryption(doEncryption);
        encoderLogger.setKey(rsaPubKey);
        //输出log的格式
        encoderLogger.setPattern("[%-5level] %d{YYYY-MM-dd HH:mm:ss.SSS} [%thread] [%logger{36}] - %msg%n");
        encoderLogger.start();

        //按天固定生成的日志
        RollingFileAppender<ILoggingEvent> rollAppender = new RollingFileAppender<ILoggingEvent>();
        rollAppender.setAppend(true);

        // OPTIONAL: Set an active log file (separate from the rollover files).
        // If rollingPolicy.fileNamePattern already set, you don't need this.
        //rollingFileAppender.setFile(LOG_DIR + "/log.txt");
        TimeBasedRollingPolicy rollingPolicy = new TimeBasedRollingPolicy<>();
        rollingPolicy.setFileNamePattern(LOG_DIR + "/%d{yyyy-MM-dd_HH}.logFile.txt");
        //sd卡上存7天日志，没有sd卡在data/data上存1天日志 日志文件保留天数
        if (!SAVE_ON_SDCARD) {
            rollingPolicy.setMaxHistory(24);
        } else {
            rollingPolicy.setMaxHistory(168);
        }
        rollingPolicy.setParent(rollAppender);
        rollingPolicy.setContext(lc);
        rollingPolicy.start();

        rollAppender.setContext(lc);
        //rollAppender.setFile(LOG_DIR + "/logFile.txt");
        rollAppender.setRollingPolicy(rollingPolicy);
        rollAppender.setEncoder(encoderLogger);
        rollAppender.start();

        // add the newly created appenders to the root logger;
        // qualify Logger to disambiguate from org.slf4j.Logger
        ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        root.addAppender(rollAppender);

        // setup LogcatAppender 表示在控制台输出
        if (showLogCat) {
            PatternLayoutEncoder encoder2 = new PatternLayoutEncoder();
            encoder2.setContext(lc);
            //输出log的格式
            encoder2.setPattern("[%-5level] %d{YYYY-MM-dd HH:mm:ss.SSS} [%thread] [%logger{36}] - %msg%n");
            encoder2.start();

            LogcatAppender logcatAppender = new LogcatAppender();
            logcatAppender.setContext(lc);
            logcatAppender.setEncoder(encoder2);
            logcatAppender.start();
            root.addAppender(logcatAppender);
        }
    }

    private static String getLogsDir(Context cxt) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            SAVE_ON_SDCARD = true;
            return Environment.getExternalStorageDirectory() + File.separator + cxt.getPackageName();
        } else {
            SAVE_ON_SDCARD = false;
            return cxt.getDir("logs", Context.MODE_PRIVATE).getAbsolutePath();
        }
    }

    private static String getRsaPubKey(Context cxt) {
        try {
            ApplicationInfo info = cxt.getPackageManager().getApplicationInfo(cxt.getPackageName(), PackageManager.GET_META_DATA);
            return info.metaData.getString(PUB_KEY_NAME);
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }
}
